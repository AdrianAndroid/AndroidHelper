import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/boss/utils.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';
import 'package:flutter_helper/utils/util_log.dart';
import 'package:flutter_helper/widgets2/search_appbar.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

import 'movie_details_ui/main.dart';
import 'profileapp/main.dart';

void main() {
  LogUtil.init(isDebug: true);

  runApp(TemplatesApp());
}

class TemplatesApp extends StatefulWidget {
  @override
  _TemplatesAppState createState() => _TemplatesAppState();
}

class _TemplatesAppState extends State<TemplatesApp> {

  var _simpleItem = false;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter DEMO APP",
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: _searchAppBarWidget,
        body: Column(
          children: [
            Expanded(
              child: Padding(
                padding: EdgeInsets.only(right: 30),
                child:
                _simpleItem ? _buildSImpleGridView(context) : _buildGrid(),
              ),
            ),
          ],
        ),
      ),
    );
  }

  List<Widget> _mwidgets = [
    ProfileApp(),
    MovieDetailsUiApp(),
  ];

  ScrollController _scrollController = ScrollController();

  var _listDisplay ;
  FocusNode _focusNode = new FocusNode();
  TextEditingController _controller = TextEditingController();

  PreferredSizeWidget get _searchAppBarWidget => SearchAppBarWidget(
    focusNode: _focusNode,
    controller: _controller,
    elevation: 2.0,
    leading: IconButton(
      onPressed: () {
        Navigator.pop(context);
      },
      icon: Icon(Icons.arrow_back),
    ),
    inputFormatters: [LengthLimitingTextInputFormatter(50)],
    onChangedCallback: (str) {
      if(str.isNotEmpty) {
        setState(() {
          // double _items.clear()
          LogUtil.e('onChangeCallBack -> $str');
          var tmp = _mwidgets
              .where((e) =>
              e.toStringShort().toLowerCase().contains(str.toLowerCase()))
              .toList();
          _listDisplay = tmp;
        });
      } else {
        setState(() {
          _listDisplay = null;
        });
      }
    },
    onEditingComplete: () {
      showToast('onEditingComplete!');
      setState(() {
        _listDisplay = null;
      });
    },
  );



  _buildSImpleGridView(BuildContext context) {
    if(_listDisplay == null) {
      _listDisplay = _mwidgets;
    }
    return GridView.builder(
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 3, //每行三列
          childAspectRatio: 1.0 //显示区域宽高相等
      ),
      itemCount: _listDisplay.length,
      itemBuilder: (context, index) {
        return GestureDetector(
          onTap: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (_) {
                return _listDisplay[index];
              }),
            );
          },
          child: Text("${_listDisplay[index].toStringShort()}"),
        );
      },
    );
  }

  _buildGrid() {
    return StaggeredGridView.countBuilder(
      controller: _scrollController,
      crossAxisCount: 4,
      itemCount: _mwidgets.length,
      itemBuilder: _builtItem,
      staggeredTileBuilder: (int index) => new StaggeredTile.fit(2),
    );
  }

  Widget _builtItem(BuildContext context, int index) {
    Widget w = _mwidgets[index];
    High high;
    if (w is HighMixin) {
      high = (w as HighMixin).getHigh();
    } else {
      high = High(w.toStringShort(), "没有实现minxin");
    }
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (_) {
            return w;
          }),
        );
      },
      child: Card(
        color: Colors.transparent,
        child: Container(
          color: Colors.white,
          width: 20,
          height: 280,
          child: Column(
            children: [
              Text(high.title),
              SizedBox(height: 5),
              ConstrainedBox(
                constraints: BoxConstraints(
                  // maxWidth: 150,
                  maxHeight: 250,
                ),
                child: w,
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class AppData {
  final String name;
  final String imageUrl;
  final Widget app;

  AppData({
    Key key,
    this.app,
    this.imageUrl = 'images/bossapp2x.png',
    this.name = "app",
  });
}
