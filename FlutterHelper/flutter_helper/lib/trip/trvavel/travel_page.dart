import 'package:flutter/material.dart';
import 'package:flutter_helper/trip/trvavel/travel_tab_dao.dart';
import 'package:flutter_helper/trip/trvavel/travel_tab_model.dart';
import 'package:flutter_helper/trip/trvavel/travel_tab_page.dart';
import 'package:flutter_helper/utils/util_log.dart';
import 'package:underline_indicator/underline_indicator.dart';

class TravelPage extends StatefulWidget {
  @override
  _TravelPageState createState() => _TravelPageState();
}

class _TravelPageState extends State<TravelPage> with TickerProviderStateMixin {
  TabController _controller;
  List<TravelTab> _tabs = [];
  TravelTabModel _travelTabModel;

  @override
  void initState() {
    LogUtil.e("initState");
    _controller = TabController(length: 0, vsync: this);
    // 获取数据
    TravelTabDao.fetch().then((model) {
      _controller = TabController(length: model.tabs.length, vsync: this);
      setState(() {
        _tabs = model.tabs;
        _travelTabModel = model;
      });
      LogUtil.e("获取到数据 ${_tabs}");
      LogUtil.e("获取到数据 ${_travelTabModel}");
    }).catchError((e) {
      print(e);
    });
    super.initState();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Container(
            color: Colors.white,
            padding: EdgeInsets.only(top: 30),
            child: TabBar(
              controller: _controller,
              isScrollable: true,
              labelColor: Colors.black,
              labelPadding: EdgeInsets.fromLTRB(20, 0, 10, 5),
              indicator: UnderlineIndicator(
                strokeCap: StrokeCap.round,
                borderSide: BorderSide(
                  color: Color(0xff2fcfbb),
                  width: 3,
                ),
                insets: EdgeInsets.only(bottom: 10),
              ),
              tabs: _tabs.map((e) => Tab(text: e.labelName)).toList(),
            ),
          ),
          Flexible(
            child: TabBarView(
              controller: _controller,
              children: _tabs.map((tab) => TravelTabPage(
                travelUrl: _travelTabModel.url,
                params: _travelTabModel.params,
                groupChannelCode: tab.groupChannelCode,
              )).toList(),
            ),
          ),
        ],
      ),
    );
  }
}
