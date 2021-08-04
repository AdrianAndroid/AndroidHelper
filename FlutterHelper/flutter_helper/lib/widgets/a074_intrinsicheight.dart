import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class IntrinsicHeightApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print('${context.toString()}');
    return MaterialApp(
      title: "Flutter DEMO APP",
      locale: Locale('zh'),
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: HeroDemo(),
    );
  }
}

class HeroDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _HeroDemo();
}

class _HeroDemo extends State<HeroDemo> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // body: buildBody(),
      // body: buildBody(),
      // body: buildColumn(),
      body: buildBody2(),
    );
  }

  buildRow() => Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          new Container(color: Colors.blue, width: 100.0),
          new Container(
            color: Colors.red,
            width: 50.0,
            height: 50.0,
          ),
          new Container(color: Colors.yellow, width: 150.0),
        ],
      );

  buildBody() => IntrinsicHeight(
        child: buildRow(),
      );

  buildColumn() => Column(
        children: <Widget>[
          new Container(color: Colors.blue, height: 100.0),
          new Container(color: Colors.red, width: 150.0, height: 100.0),
          new Container(
            color: Colors.yellow,
            height: 150.0,
          ),
        ],
      );

  buildBody2() => IntrinsicWidth(
        stepHeight: 450.0,
        stepWidth: 300.0,
        child: buildColumn(),
      );
}
