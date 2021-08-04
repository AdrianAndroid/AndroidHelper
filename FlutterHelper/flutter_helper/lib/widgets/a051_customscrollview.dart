import 'dart:math';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class CustomScrollViewApp extends StatelessWidget {
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

  var _scrollController = ScrollController();

  @override
  void initState() {
    // 监听滚动位置
    _scrollController.addListener(() {
      print('${_scrollController.position}');
    });
    //滚动到指定位置
    _scrollController.animateTo(20.0, duration: Duration(seconds: 2), curve:
    null);
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  buildBody1() =>
      CustomScrollView(
        slivers: <Widget>[
          SliverGrid.count(
            crossAxisCount: 4,
            children: List.generate(8, (index) {
              return Container(
                color: Colors.primaries[index % Colors.primaries.length],
                alignment: Alignment.center,
                child: Text(
                  '$index',
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ),
              );
            }).toList(),
          ),
          SliverList(
            delegate: SliverChildBuilderDelegate((content, index) {
              return Container(
                height: 85,
                alignment: Alignment.center,
                color: Colors.primaries[index % Colors.primaries.length],
                child: Text(
                  '$index',
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ),
              );
            }, childCount: 25),
          )
        ],
      );

  buildBody2() =>
      CustomScrollView(
        controller: _scrollController,
        scrollDirection: Axis.vertical,
        reverse: false,
        slivers: [
          SliverAppBar(
            pinned: true,
            expandedHeight: 230.0,
            flexibleSpace: FlexibleSpaceBar(
              title: Text('复仇者联盟'),
              background: Image.network(
                'http://img.haote.com/upload/20180918/2018091815372344164.jpg',
                fit: BoxFit.fitHeight,
              ),
            ),
          ),
          SliverGrid.count(
            crossAxisCount: 4,
            children: List.generate(8, (index) {
              return Container(
                color: Colors.primaries[index % Colors.primaries.length],
                alignment: Alignment.center,
                child: Text(
                  '$index',
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ),
              );
            }).toList(),
          ),
          SliverList(
              delegate: SliverChildBuilderDelegate((context, index) {
                return Container(
                  height: 85,
                  alignment: Alignment.center,
                  color: Colors.primaries[index % Colors.primaries.length],
                  child: Text(
                    '$index',
                    style: TextStyle(color: Colors.white, fontSize: 24),
                  ),
                );
              }, childCount: 25)),
        ],
      );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('ZhaoJian'),
      ),
      //body: buildBody1(),
      body: buildBody2(),
    );
  }
}
