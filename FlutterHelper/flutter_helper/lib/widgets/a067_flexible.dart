import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';

class FlexibleApp extends StatelessWidget with HighMixin{
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

  @override
  High getHigh() => High(toStringShort(), """
import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';

class FlexibleApp extends StatelessWidget with HighMixin{
  @override
  Widget build(BuildContext context) {
    print('{context.toString()}');
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

  @override
  High getHigh() => High(toStringShort(), """

      """);
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

  buildBody() => Column(
        children: [
          Row(
            children: [
              Container(
                width: 100,
                height: 50,
                color: Colors.green,
              ),
              Spacer(
                flex: 2,
              ),
              Container(
                width: 100,
                height: 50,
                color: Colors.blue,
              ),
              Spacer(),
              Container(
                width: 100,
                height: 50,
                color: Colors.green,
              ),
            ],
          ),
          Row(
            children: [
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
              Expanded(
                child: OutlineButton(
                  child: Text('OutlineButton'),
                ),
              ),
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
            ],
          ),
          Row(
            children: [
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
              Flexible(
                fit: FlexFit.loose,
                child: OutlineButton(
                  child: Text('OutlineButton'),
                ),
              ),
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
            ],
          ),
          Row(
            children: [
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
              Flexible(
                fit: FlexFit.loose,
                child: Container(
                  color: Colors.red,
                  height: 50,
                  alignment: Alignment.center,
                  child: Text(
                    'Container',
                    style: TextStyle(color: Colors.white),
                  ),
                ),
              ),
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
            ],
          ),
          SizedBox(
            height: 4,
          ),
          Flexible(
            flex: 1,
            child: Container(
              color: Colors.blue,
              alignment: Alignment.center,
              child: Text(
                '1 Flex / 6 Total',
                style: TextStyle(color: Colors.white),
              ),
            ),
          ),
          Flexible(
            flex: 2,
            child: Container(
              color: Colors.red,
              alignment: Alignment.center,
              child: Text(
                '1 Flex / 6 Total',
                style: TextStyle(color: Colors.white),
              ),
            ),
          ),
          Flexible(
            flex: 3,
            child: Container(
              color: Colors.green,
              alignment: Alignment.center,
              child: Text(
                '1 Flex / 6 Total',
                style: TextStyle(color: Colors.white),
              ),
            ),
          ),
        ],
      );
}

  """);
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

  buildBody() => Column(
        children: [
          Row(
            children: [
              Container(
                width: 100,
                height: 50,
                color: Colors.green,
              ),
              Spacer(
                flex: 2,
              ),
              Container(
                width: 100,
                height: 50,
                color: Colors.blue,
              ),
              Spacer(),
              Container(
                width: 100,
                height: 50,
                color: Colors.green,
              ),
            ],
          ),
          Row(
            children: [
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
              Expanded(
                child: OutlineButton(
                  child: Text('OutlineButton'),
                ),
              ),
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
            ],
          ),
          Row(
            children: [
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
              Flexible(
                fit: FlexFit.loose,
                child: OutlineButton(
                  child: Text('OutlineButton'),
                ),
              ),
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
            ],
          ),
          Row(
            children: [
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
              Flexible(
                fit: FlexFit.loose,
                child: Container(
                  color: Colors.red,
                  height: 50,
                  alignment: Alignment.center,
                  child: Text(
                    'Container',
                    style: TextStyle(color: Colors.white),
                  ),
                ),
              ),
              Container(
                color: Colors.blue,
                height: 50,
                width: 50,
              ),
            ],
          ),
          SizedBox(
            height: 4,
          ),
          Flexible(
            flex: 1,
            child: Container(
              color: Colors.blue,
              alignment: Alignment.center,
              child: Text(
                '1 Flex / 6 Total',
                style: TextStyle(color: Colors.white),
              ),
            ),
          ),
          Flexible(
            flex: 2,
            child: Container(
              color: Colors.red,
              alignment: Alignment.center,
              child: Text(
                '1 Flex / 6 Total',
                style: TextStyle(color: Colors.white),
              ),
            ),
          ),
          Flexible(
            flex: 3,
            child: Container(
              color: Colors.green,
              alignment: Alignment.center,
              child: Text(
                '1 Flex / 6 Total',
                style: TextStyle(color: Colors.white),
              ),
            ),
          ),
        ],
      );
}
