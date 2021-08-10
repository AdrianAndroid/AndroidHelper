import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/boss_app.dart';
import 'package:flutter_helper/language/languagea_app.dart';
import 'package:flutter_helper/other/other_app.dart';
import 'package:flutter_helper/templates/flutter_ui_kit/myapp.dart';
import 'package:flutter_helper/templates/main.dart';
import 'package:flutter_helper/trip/trip_app.dart';
import 'package:flutter_helper/utils/util_log.dart';
import 'package:flutter_helper/widgets/widgets_page.dart';

import 'samples/main.dart';
import 'templates/awesome_flutter/awesome.dart';
import 'templates/fonts/main.dart';
import 'templates/navigator_v2/main.dart';
import 'templates/todo/main.dart';

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
    AppData(app: FlutterUiKitApp(), imageUrl: 'assets/images/pk.jpg'),
    AppData(app: GoogleFontsApp(), imageUrl: 'assets/fonts.png'),
    AppData(app: LanguageApp(), imageUrl: 'images/beatiful_lady.png'),
    AppData(app: TodoApp(), imageUrl: 'assets/todo.png'),
    AppData(app: NavigatorVeggiesApp(), imageUrl: 'assets/navigation_route.png'),
    AppData(app: AwesomeFlutterApp(), imageUrl: 'assets/awsome.png'),
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

  AppData.from(): name="AppData", imageUrl="assets/awsome.png", app=null;

  String getDisplay() => app.toStringShort();
}


