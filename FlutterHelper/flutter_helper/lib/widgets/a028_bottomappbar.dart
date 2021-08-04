import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BottomAppBarApp extends StatelessWidget {
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
      body: Container(),
      // floatingActionButton: RaisedButton(
      //   shape: CircleBorder(),
      //   color: Colors.red,
      //   onPressed: () {
      //     setState(() {});
      //   },
      // ),
      floatingActionButton: FloatingActionButton.extended(
        shape: BeveledRectangleBorder(borderRadius: BorderRadius.circular(100)),
        onPressed: () {},
        label: Text('label'),
        icon: Icon(Icons.add),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      bottomNavigationBar: BottomAppBar(
        // notchMargin: -10,
        elevation: 8.0,
        // shape: CircularNotchedRectangle(),
        shape: AutomaticNotchedShape(
          RoundedRectangleBorder(),
          // StadiumBorder(side: BorderSide()),
          BeveledRectangleBorder(borderRadius: BorderRadius.circular(100)),
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            IconButton(icon: Icon(Icons.home), onPressed: () {}),
            IconButton(icon: Icon(Icons.people), onPressed: () {}),
          ],
        ),
      ),
    );
  }
}
