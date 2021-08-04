import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class CupertinoPageScaffoldApp extends StatelessWidget {
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
  AnimationController _animationController;

  @override
  void initState() {
    _animationController = AnimationController(
      vsync: this,
      duration: Duration(milliseconds: 500),
    );
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return CupertinoPageScaffold(
        navigationBar: CupertinoNavigationBar(
          leading: Icon(Icons.arrow_back),
          middle: Text('老孟'),
        ),
        child: Center(
          child: RaisedButton(
            child: Text('goto'),
            color: Colors.red,
            onPressed: () {
              Navigator.of(context).push(CupertinoPageRoute(builder: (context) {
                return SecondPage();
              }));
            },
          ),
        ));
  }
}

class SecondPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return CupertinoPageScaffold(
      navigationBar: CupertinoNavigationBar(
        previousPageTitle: '返回',
        middle: Text('老孟'),
      ),
      child: Center(
        child: RaisedButton(
          child: Text('to third'),
          onPressed: () {
            // Navigator.of(context).push(CupertinoPageRoute(builder: (context) {
            //   return ThirdPage();
            // }));
          },
        ),
      ),
    );
  }
}
