import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/boss_app.dart';
import 'package:flutter_helper/language/languagea_app.dart';
import 'package:flutter_helper/other/other_app.dart';
import 'package:flutter_helper/templates/main.dart';
import 'package:flutter_helper/trip/trip_app.dart';
import 'package:flutter_helper/utils/util_log.dart';
import 'package:flutter_helper/widgets/widgets_page.dart';

import 'samples/main.dart';

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
    AppData(app: SamplesApp(), imageUrl: 'assets/flutter.png'),
    AppData(app: TemplatesApp(), imageUrl: 'images/poster.png'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
    AppData(app: LanguageApp(), imageUrl: 'images/flutter_cover.png'),
  ];

  _buildGridViewBuilder() {
    return GridView.builder(
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 3,
        crossAxisSpacing: 10,
        mainAxisSpacing: 10,
        childAspectRatio: 1,
      ),
      itemCount: listApp.length,
      itemBuilder: (BuildContext context, int index) {
        var e = listApp[index];
        return GestureDetector(
          onTap: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (_) {
                return listApp[index].app;
              }),
            );
          },
          child: Card(
            child: Column(
              children: [
                Image.asset(
                  e.imageUrl,
                  width: 60,
                  height: 80,
                ),
                Text(e.getDisplay())
              ],
            ),
          ),
        );
      },
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
        body: _buildGridViewBuilder(),
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

  String getDisplay() => app.toStringShort();
}
