import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BorderApp extends StatelessWidget {
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
      body: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          RaisedButton(
            shape: BeveledRectangleBorder(
              side: BorderSide(width: 1, color: Colors.red),
              borderRadius: BorderRadius.circular(10),
            ),
            onPressed: () {},
            child: Text('zhojian'),
          ),
          RaisedButton(
            shape: BeveledRectangleBorder(
              side: BorderSide(width: 1, color: Colors.red),
              borderRadius: BorderRadius.circular(100),
            ),
            onPressed: () {},
            child: Text('zhojian'),
          ),
          RaisedButton(
            shape: BeveledRectangleBorder(
              side: BorderSide(width: 1, color: Colors.red),
              borderRadius: BorderRadius.circular(0),
            ),
            onPressed: () {},
            child: Text('zhojian'),
          ),
          RaisedButton(
            shape: Border(
              top: BorderSide(color: Colors.red, width: 2),
            ),
            onPressed: () {},
            child: Text('zhojian'),
          ),
          RaisedButton(
            shape: Border(
              top: BorderSide(color: Colors.red, width: 10),
              right: BorderSide(color: Colors.green, width: 10),
              bottom: BorderSide(color: Colors.blue, width: 10),
              left: BorderSide(color: Colors.grey, width: 10),
            ),
            onPressed: () {},
            child: Text('zhojian'),
          ),
          RaisedButton(
            shape: BorderDirectional(
              start: BorderSide(color: Colors.red, width: 2),
              end: BorderSide(color: Colors.blue, width: 2),
            ),
            child: Text('ZHAOJIAN'),
            onPressed: () {},
          ),
          RaisedButton(
            shape: CircleBorder(side: BorderSide(color: Colors.red)),
            child: Text('ZHA'),
            onPressed: () {},
          ),
          RaisedButton(
            shape: ContinuousRectangleBorder(
              side: BorderSide(color: Colors.red),
              borderRadius: BorderRadius.circular(20),
            ),
            child: Text('ZHA'),
            onPressed: () {},
          ),
          RaisedButton(
            shape: RoundedRectangleBorder(
              side: BorderSide(color: Colors.red),
              borderRadius: BorderRadius.circular(10),
            ),
            child: Text('ZHA'),
            onPressed: () {},
          ),
          RaisedButton(
            shape: StadiumBorder(
              side: BorderSide(color: Colors.red),
            ),
            child: Text('ZHA'),
            onPressed: () {},
          ),
          RaisedButton(
            shape: OutlineInputBorder(
              borderSide: BorderSide(color: Colors.greenAccent),
              borderRadius: BorderRadius.circular(20),
            ),
            child: Text('ZHA'),
            onPressed: () {},
          ),
          RaisedButton(
            shape: UnderlineInputBorder(
              borderSide: BorderSide(color: Colors.red),
            ),
            child: Text('ZHAOJIAN'),
            onPressed: () {},
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
