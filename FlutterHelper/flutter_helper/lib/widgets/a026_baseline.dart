import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BaselineApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter DEMO APP",
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: _Test(),
    );
  }
}

class _Test extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _TestState();
  }
}

class _TestState extends State<_Test> with SingleTickerProviderStateMixin {
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('zhaojan'),
      ),
      body: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Baseline(
            baseline: 50.0,
            baselineType: TextBaseline.alphabetic,
            child: Text(
              'TjTjTj',
              style: TextStyle(
                fontSize: 20.0,
                textBaseline: TextBaseline.alphabetic,
              ),
            ),
          ),
          Baseline(
            baseline: 50.0,
            baselineType: TextBaseline.alphabetic,
            child: Container(
              width: 30.0,
              height: 30.0,
              color: Colors.red,
            ),
          ),
          Baseline(
            baseline: 50.0,
            baselineType: TextBaseline.alphabetic,
            child: Text(
              'RyRyRy',
              style: TextStyle(
                  fontSize: 35.0, textBaseline: TextBaseline.alphabetic),
            ),
          )
        ],
      ),
      floatingActionButton: RaisedButton(
        onPressed: () {
          setState(() {});
        },
      ),
    );
  }
}
