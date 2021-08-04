import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BackdropFilterApp extends StatelessWidget {
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
      body: Stack(
        alignment: Alignment.center,
        children: [
          Container(
            width: 300,
            height: 400,
            child: Image.asset('images/beatiful_lady.jpeg'),
          ),
          BackdropFilter(
            filter: ImageFilter.blur(sigmaX: 5.0, sigmaY: 5.0),
            child: Center(
              child: Container(
                color: Colors.red.withOpacity(0),
                child: Text(
                  '一个有态度的程序员',
                  style: TextStyle(color: Colors.blue),
                ),
              ),
            ),
          ),
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
