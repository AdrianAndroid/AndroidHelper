import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FutureBuilderApp extends StatelessWidget {
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

  buildBody2() => FutureBuilder(
    future: _future,
    builder: (context, snapshot) {
      var widget;
      if (snapshot.connectionState == ConnectionState.done) {
        if (snapshot.hasError) {
          widget = Icon(
            Icons.error,
            color: Colors.red,
            size: 48,
          );
        } else {
          widget = Icon(
            Icons.check_circle,
            color: Colors.green,
            size: 36,
          );
        }
      } else {
        widget = Padding(
          padding: EdgeInsets.all(20),
          child: CircularProgressIndicator(),
        );
      }

      return Center(
        child: Container(
          height: 100,
          width: 100,
          decoration: BoxDecoration(
              border: Border.all(color: Colors.grey),
              borderRadius: BorderRadius.all(Radius.circular(10))),
          child: widget,
        ),
      );
    },
  );
  // var _future = Future.delayed(Duration(seconds: 3), () {
  //   return "老孟，一个有态度的程序员";
  // });

  // var _future = Future.delayed(Duration(seconds: 3), () {
  //   return Future.error('');
  // });

  buildBody() => FutureBuilder(
    future: _future,
    builder: (context, snapshot) {
      var widget;
      if (snapshot.connectionState == ConnectionState.done) {
        if (snapshot.hasError) {
          widget = _loadingErrorWidget();
        } else {
          widget = _dataWidget(snapshot.data);
        }
      } else {
        widget = _loadingWidget();
      }
      return widget;
    },
  );

  _loadingWidget() {
    return Center(
      child: Padding(
        padding: EdgeInsets.all(20),
        child: CircularProgressIndicator(),
      ),
    );
  }


  _loadingErrorWidget() {
    return Center(
      child: Text('数据加载失败，请重试。'),
    );
  }

  _dataWidget(data) {
    return ListView.separated(
      itemBuilder: (context, index) {
        return Container(
          height: 60,
          alignment: Alignment.center,
          child: Text(
            '$index',
            style: TextStyle(fontSize: 20),
          ),
        );
      },
      separatorBuilder: (context, index) {
        return Divider();
      },
      itemCount: 10,
    );
  }

  var _future = Future.delayed(Duration(seconds: 3), () {
    return Future.error('');
  });


// var _future = Future.delayed(Duration(seconds: 3), () {
  //   return 'json 字符串';
  // });

}
