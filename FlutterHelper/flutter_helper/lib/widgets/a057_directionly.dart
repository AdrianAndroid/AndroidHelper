import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DirectionlyApp extends StatelessWidget {
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
  Animation _animation;

  @override
  void initState() {
    _animationController = AnimationController(
      duration: Duration(seconds: 2),
      vsync: this,
    )..addListener(() {
        if (_animationController.status == AnimationStatus.completed) {
          _animationController.reverse();
        } else if (_animationController.status == AnimationStatus.dismissed) {
          _animationController.forward();
        }
      });

    _animation = TextStyleTween(
            begin: TextStyle(color: Colors.blue, fontSize: 14),
            end: TextStyle(color: Colors.red, fontSize: 24))
        .animate(_animationController);

    //开始动画
    _animationController.forward();
    super.initState();
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('ZhaoJian'),
      ),
      body: Builder(builder: (context) {
        return Container(
          height: 100,
          width: 100,
          color: Colors.red,
          child: Directionality(
            textDirection: TextDirection.rtl,
            child: Text('老孟'),
          ),
        );
      }),
      // body: buildDataTable(),
    );
  }

  buildBody() => Column(
        children: [
          Builder(builder: (context) {
            return DefaultTextStyleTransition(
              style: _animation,
              child: Text(
                  '11111111111111111111111111111111111111111111111111111111'),
            );
          }),
          Container(
            width: 150,
            color: Colors.red,
            child: DefaultTextStyle(
              style: TextStyle(fontSize: 18),
              overflow: TextOverflow.clip,
              child: Text(
                  '11111111111111111111111111111111111111111111111111111111'),
            ),
          ),
          SizedBox(
            height: 10,
          ),
          Container(
            width: 150,
            color: Colors.red,
            child: DefaultTextStyle(
              style: TextStyle(fontSize: 18),
              overflow: TextOverflow.fade,
              child: Text(
                  '11111111111111111111111111111111111111111111111111111111'),
            ),
          ),
          SizedBox(
            height: 10,
          ),
          Container(
            width: 150,
            color: Colors.red,
            child: DefaultTextStyle(
              style: TextStyle(fontSize: 18),
              overflow: TextOverflow.visible,
              child: Text(
                  '11111111111111111111111111111111111111111111111111111111'),
            ),
          ),
          SizedBox(
            height: 10,
          ),
          Container(
            width: 150,
            color: Colors.red,
            child: DefaultTextStyle(
              style: TextStyle(fontSize: 18),
              overflow: TextOverflow.ellipsis,
              child: Text(
                  '11111111111111111111111111111111111111111111111111111111'),
            ),
          ),
          DefaultTextStyle(
            style: TextStyle(color: Colors.red),
            child: Text(
              '老孟',
              style: TextStyle(color: Colors.blue),
            ),
          ),
          DefaultTextStyle(
            style: TextStyle(color: Colors.red),
            child: Text('老孟'),
          ),
        ],
      );
}
