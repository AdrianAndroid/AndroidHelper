import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class GestureDetectorApp extends StatelessWidget {
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
        title: Text('ZhaoJian'),
      ),
      body: Builder(builder: (context) {
        return buildBody();
      }),
      // body: buildDataTable(),
      floatingActionButton: RaisedButton(
        child: CircleAvatar(
          child: Text('点'),
        ),
        onPressed: () {
          showBottomSheet(
            context: context,
            backgroundColor: Colors.lightGreenAccent,
            elevation: 20,
            shape: CircleBorder(),
            builder: (context) {
              return Container(
                height: 200,
                color: Colors.lightBlue,
              );
            },
          );
        },
      ),
    );
  }

  List<bool> dataList = List.generate(20, (index) => false).toList();

  buildBody() => GestureDetector(
        // onTapDown: (tapDown) {
        //   print('onTapDown');
        // },
        // onTapUp: (tapUp) {
        //   print('onTapUp');
        // },
        // onTap: () {
        //   print('onTap');
        // },
        // onTapCancel: () {
        //   print('onTapCancel');
        // },
        // onDoubleTap: () {
        //   print('onDoubleTap');
        // },
        onLongPressStart: (v) => print('onLongPressStart'),
        onLongPressMoveUpdate: (v) => print('onLongPressMoveUpdate'),
        onLongPressUp: () => print('onLongPressUp'),
        onLongPressEnd: (v) => print('onLongPressEnd'),
        onLongPress: () => print('onLongPress'),
        onVerticalDragStart: (v) => print('onVerticalDragStart'),
        onVerticalDragDown: (v) => print('onVerticalDragDown'),
        onVerticalDragUpdate: (v) => print('onVerticalDragUpdate'),
        onVerticalDragCancel: () => print('onVerticalDragCancel'),
        onVerticalDragEnd: (v) => print('onVerticalDragEnd'),
        child: Center(
          child: Container(
            width: 200,
            height: 200,
            color: Colors.red,
          ),
        ),
      );
}
