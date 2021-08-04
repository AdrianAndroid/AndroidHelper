import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class RefreshIndicatorApp extends StatelessWidget {
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

  var _list = [1, 2, 3, 4, 5];

  @override
  Widget build2(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('APPBAR'),
      ),
      body: RefreshIndicator(
        color: Colors.red,
        backgroundColor: Colors.lightBlue,
        displacement: 10,
        onRefresh: () async {
          _list.add(_list.length + 1);
        },
        child: ListView.builder(
          itemBuilder: (context, index) {
            return ListTile(
              title: Text('老孟${_list[index]}'),
            );
          },
          itemExtent: 50,
          itemCount: _list.length,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('ZHAOJIAN'),
      ),
      body: CustomScrollView(
        slivers: [
          CupertinoSliverRefreshControl(
            onRefresh: () async {
              setState(() {
                _list.add(_list.length + 1);
              });
            },
          ),
          SliverList(
            delegate: SliverChildBuilderDelegate(
              (context, index) {
                return ListTile(
                  title: Text('老孟${_list[index]}'),
                );
              },
              childCount: _list.length,
            ),
          ),
        ],
      ),
    );
  }
}
