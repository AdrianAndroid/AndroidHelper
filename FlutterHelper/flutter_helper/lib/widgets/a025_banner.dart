import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BannerApp extends StatelessWidget {
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
      body: Banner(
        message: 'zhaojian',
        location: BannerLocation.topStart,
        color: Colors.blue,
        textStyle: TextStyle(color: Colors.red),
      ),
      floatingActionButton: RaisedButton(
        onPressed: () {
          setState(() {});
        },
      ),
    );
  }
}
