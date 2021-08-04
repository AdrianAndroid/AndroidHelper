import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/boss_app.dart';
import 'package:flutter_helper/boss/utils.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';
import 'package:flutter_helper/other/other_app.dart';
import 'package:flutter_helper/samples/radial_menu/demo.dart';
import 'package:flutter_helper/samples/samples_1.dart';
import 'package:flutter_helper/samples/staggerd/main.dart';
import 'package:flutter_helper/trip/trip_app.dart';
import 'package:flutter_helper/utils/util_log.dart';
import 'package:flutter_helper/widgets/a003_absorbpointer.dart';
import 'package:flutter_helper/widgets/widgets_page.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

void main() {
  LogUtil.init(isDebug: true);

  runApp(_HomeApp());
}

class _HomeApp extends StatefulWidget {
  @override
  __HomeAppState createState() => __HomeAppState();
}

class __HomeAppState extends State<_HomeApp> {
  var listApp = [
    AppData(app: BossApp()),
    AppData(app: TripApp(), imageUrl: 'images/ic_trip.png'),
    AppData(app: WidgetPage(), imageUrl: 'images/bird.png'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
  ];

  Widget buildHorizontal() {
    return Container(
      height: 100,
      child: ListView.builder(
        scrollDirection: Axis.horizontal,
        itemBuilder: (BuildContext context, int index) {
          var name = listApp[index].app.toStringShort();
          return GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (_) {
                  return listApp[index].app;
                }),
              );
            },
            child: Padding(
              padding: EdgeInsets.only(right: 10),
              child: Column(
                children: [
                  Image.asset(
                    listApp[index].imageUrl,
                    width: 60,
                    height: 80,
                  ),
                  Text(name)
                ],
              ),
            ),
          );
        },
        itemCount: listApp.length,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter DEMO APP",
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text("Flutter"),
        ),
        body: Column(
          children: [
            buildHorizontal(),
            Expanded(
              child: Padding(
                padding: EdgeInsets.only(right: 30),
                child: _buildGrid(),
              ),
            ),
          ],
        ),
      ),
    );
  }

  List<Widget> _mwidgets = [
    GridViewCount(),
    ListViewHorizontal(),
    StaggerApp(),
    RadialMenuApp(),
  ];

  ScrollController _scrollController = ScrollController();

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
    High high = High("title", "text");
    if (w is HighMixin) {
      high = (w as HighMixin).getHigh();
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
              Expanded(child: w),
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
