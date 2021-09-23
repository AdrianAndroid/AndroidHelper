import 'package:flutter/material.dart';
import 'package:flutter_helper/trip/home/home_dao.dart';
import 'package:flutter_helper/trip/home/home_model.dart';
import 'package:flutter_helper/trip/home/nav_grid.dart';
import 'package:flutter_helper/trip/home/nav_local.dart';
import 'package:flutter_helper/trip/utils.dart';
import 'package:flutter_helper/trip/widget/LoadingContainer.dart';
import 'package:flutter_helper/trip/widget/search_bar.dart';
import 'package:flutter_helper/trip/widget/webview.dart';
import 'package:flutter_swiper/flutter_swiper.dart';

import 'nav_sale.dart';
import 'nav_sub.dart';

const APPBAR_SCROLL_OFFSET = 100;
const SEARCH_BAR_DEFAULT_TEXT = '网红打卡地 景点 酒店 美食';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  double _appBarAlpha = 0;
  List<CommonModel> _localNavList = [];
  List<CommonModel> _bannerList = [];
  List<CommonModel> _subNavList = [];
  GridNavModel _gridNavModel;
  SalesBoxModel _salesBoxModel;
  bool _loading = true;

  @override
  void initState() {
    super.initState();
    _handleRefresh();
  }

  _onScroll(offset) {
    double alpha = offset / APPBAR_SCROLL_OFFSET;
    if (alpha < 0) {
      alpha = 0;
    } else if (alpha > 1) {
      alpha = 1;
    }
    setState(() {
      _appBarAlpha = alpha;
    });
    print(_appBarAlpha);
  }

  Future<Null> _handleRefresh() async {
    try {
      HomeModel model = await HomeDao.fetch();
      setState(() {
        _localNavList = model.localNavList;
        _subNavList = model.subNavList;
        _gridNavModel = model.gridNav;
        _salesBoxModel = model.salesBox;
        _bannerList = model.bannerList;
        _loading = false;
      });
    } catch (e) {
      print(e);
      setState(() {
        _loading = false;
      });
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xfff2f2f2),
      body: LoadingContainer(
        isLoading: _loading,
        child: Stack(
          children: [
            MediaQuery.removePadding(
              removeTop: true,
              context: context,
              child: RefreshIndicator(
                onRefresh: _handleRefresh,
                child: NotificationListener(
                  onNotification: (notif) {
                    if (notif is ScrollUpdateNotification && notif.depth == 0) {
                      _onScroll(notif.metrics.pixels);
                    }
                    return true;
                  },
                  child: _listView,
                ),
              ),
            ),
            _appBar,
          ],
        ),
      ),
    );
  }

  Widget get _appBar {
    return Column(
      children: [
        Container(
          decoration: BoxDecoration(
            gradient: LinearGradient(
              // AppBar 渐变遮罩背景
              colors: [Color(0x66000000), Colors.transparent],
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
            ),
          ),
          child: Container(
            padding: EdgeInsets.fromLTRB(0, 20, 0, 0),
            height: 80.0,
            decoration: BoxDecoration(
              color:
                  Color.fromARGB((_appBarAlpha * 255).toInt(), 255, 255, 255),
            ),
            child: SearchBar(
              searchBarType: _appBarAlpha > 0.2
                  ? SearchBarType.homeLight
                  : SearchBarType.home,
              // inputBoxClick: _jumpToSearch,
              // speakClick: _jumpToSpeak,
              defaultText: SEARCH_BAR_DEFAULT_TEXT,
              leftButtonClick: () {},
            ),
          ),
        ),
        Container(
          height: _appBarAlpha > 0.2 ? 0.5 : 0,
          decoration: BoxDecoration(
            color: Colors.red,
            boxShadow: [
              BoxShadow(color: Colors.black12, blurRadius: 0.5),
            ],
          ),
        ),
      ],
    );
  }

  Widget get _banner {
    return Container(
      height: 180,
      child: Swiper(
        itemCount: _bannerList.length,
        autoplay: true,
        itemBuilder: (context, index) {
          return GestureDetector(
            onTap: () {
              CommonModel model = _bannerList[index];
              NavigatorUtil.push(
                  context,
                  WebView(
                    url: model.url,
                    title: model.title,
                    hideAppBar: model.hideAppBar,
                  ));
            },
            child: Image.network(_bannerList[index].icon, fit: BoxFit.fill),
          );
        },
        pagination: SwiperPagination(),
      ),
    );
  }

  Widget get _listView {
    return ListView(
      children: [
        _banner,
        Padding(
          padding: EdgeInsets.fromLTRB(7, 4, 7, 4),
          child: LocalNav(localNavList: _localNavList),
        ),
        Padding(
          padding: EdgeInsets.fromLTRB(7, 4, 7, 4),
          child: GridNav(gridNavModel: _gridNavModel),
        ),
        Padding(
          padding: EdgeInsets.fromLTRB(7, 4, 7, 4),
          child: SubNav(subNavList: _subNavList),
        ),
        Padding(
          padding: EdgeInsets.fromLTRB(7, 4, 7, 4),
          child: SalesNav(salesBoxModel: _salesBoxModel),
        ),
      ],
    );
  }
}
