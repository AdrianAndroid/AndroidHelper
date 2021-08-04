import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class ClipRectApp extends StatelessWidget {
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

class TextFieldPage extends StatefulWidget {
  String title;

  TextFieldPage({Key key, this.title}) : super(key: key);

  @override
  _TextFieldPageState createState() => _TextFieldPageState();
}

class _TextFieldPageState extends State<TextFieldPage> {
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  void test() {
    var _focusNode = FocusNode();
    _focusNode.unfocus();
    // FocusScope.of(context).requestFocus(_focusNode);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Builder(builder: (context) {
              return StartClip();
            }),
            Builder(builder: (context) {
              return Center(
                child: ClipPath(
                  clipper: StarPath(),
                  child: Container(
                    height: 150,
                    width: 250,
                    child: Image.asset(
                      'images/beatiful_lady.jpeg',
                      fit: BoxFit.cover,
                    ),
                  ),
                ),
              );
            }),
            Builder(builder: (context) {
              return Center(
                child: ClipPath(
                  clipper: TrianglePath(),
                  child: Container(
                    height: 150,
                    width: 250,
                    child: Image.asset(
                      'images/beatiful_lady.jpeg',
                      fit: BoxFit.cover,
                    ),
                  ),
                ),
              );
            }),
            ClipPath.shape(
              shape: BeveledRectangleBorder(),
              child: Container(
                height: 150,
                width: 250,
                child: Image.asset(
                  'images/beatiful_lady.jpeg',
                  fit: BoxFit.cover,
                ),
              ),
            ),
            ClipPath.shape(
              shape: ContinuousRectangleBorder(),
              child: Container(
                height: 150,
                width: 150,
                child: Image.asset(
                  'images/beatiful_lady.jpeg',
                  fit: BoxFit.cover,
                ),
              ),
            ),
            ClipPath.shape(
              shape: RoundedRectangleBorder(),
              child: Container(
                height: 150,
                width: 150,
                child: Image.asset(
                  'images/beatiful_lady.jpeg',
                  fit: BoxFit.cover,
                ),
              ),
            ),
            ClipPath.shape(
              shape: StadiumBorder(),
              child: Container(
                height: 150,
                width: 250,
                child: Image.asset(
                  'images/beatiful_lady.jpeg',
                  fit: BoxFit.cover,
                ),
              ),
            ),
            ClipOval(
              child: Container(
                height: 150,
                width: 250,
                child: Image.asset(
                  'images/beatiful_lady.jpeg',
                  fit: BoxFit.cover,
                ),
              ),
            ),
            ClipOval(
              child: Container(
                height: 150,
                width: 150,
                child: Image.asset(
                  'images/beatiful_lady.jpeg',
                  fit: BoxFit.cover,
                ),
              ),
            ),
            ClipRRect(
              borderRadius: BorderRadius.circular(20),
              child: Container(
                height: 150,
                width: 150,
                child:
                    Image.asset('images/beatiful_lady.jpeg', fit: BoxFit.cover),
              ),
            ),
            ClipRect(
              child: Align(
                alignment: Alignment.topCenter,
                heightFactor: 0.5,
                child: Container(
                  height: 150,
                  width: 150,
                  child: Image.asset(
                    'images/beatiful_lady.jpeg',
                    fit: BoxFit.cover,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class StartClip extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _StartClipState();
}

class _StartClipState extends State<StartClip>
    with SingleTickerProviderStateMixin {
  AnimationController _controller;
  Animation _animation;

  @override
  void initState() {
    _controller =
    AnimationController(duration: Duration(seconds: 2), vsync: this)
      ..addStatusListener((status) {
        if (status == AnimationStatus.completed) {
          _controller.reverse();
        } else if (status == AnimationStatus.dismissed) {
          _controller.forward();
        }
      });
    _animation = Tween(begin: 1.0, end: 4.0).animate(_controller);
    _controller.forward();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: AnimatedBuilder(
          animation: _animation,
          builder: (context, child) {
            return ClipPath(
              clipper: StarPath(scale: _animation.value),
              child: Container(
                height: 150,
                width: 150,
                color: Colors.red,
              ),
            );
          }),
    );
  }
}


class StarPath extends CustomClipper<Path> {
  StarPath({this.scale = 2.5});

  final double scale;

  double perDegree = 36;

  /// 角度转弧度公式
  double degree2Radian(double degree) {
    return (pi * degree / 180);
  }

  @override
  Path getClip(Size size) {
    var R = min(size.width / 2, size.height / 2);
    var r = R / scale;
    var x = size.width / 2;
    var y = size.height / 2;

    var path = Path();
    path.moveTo(x, y - R);
    path.lineTo(x - sin(degree2Radian(perDegree)) * r,
        y - cos(degree2Radian(perDegree)) * r);
    path.lineTo(x - sin(degree2Radian(perDegree * 2)) * R,
        y - cos(degree2Radian(perDegree * 2)) * R);
    path.lineTo(x - sin(degree2Radian(perDegree * 3)) * r,
        y - cos(degree2Radian(perDegree * 3)) * r);
    path.lineTo(x - sin(degree2Radian(perDegree * 4)) * R,
        y - cos(degree2Radian(perDegree * 4)) * R);
    path.lineTo(x - sin(degree2Radian(perDegree * 5)) * r,
        y - cos(degree2Radian(perDegree * 5)) * r);
    path.lineTo(x - sin(degree2Radian(perDegree * 6)) * R,
        y - cos(degree2Radian(perDegree * 6)) * R);
    path.lineTo(x - sin(degree2Radian(perDegree * 7)) * r,
        y - cos(degree2Radian(perDegree * 7)) * r);
    path.lineTo(x - sin(degree2Radian(perDegree * 8)) * R,
        y - cos(degree2Radian(perDegree * 8)) * R);
    path.lineTo(x - sin(degree2Radian(perDegree * 9)) * r,
        y - cos(degree2Radian(perDegree * 9)) * r);
    path.lineTo(x - sin(degree2Radian(perDegree * 10)) * R,
        y - cos(degree2Radian(perDegree * 10)) * R);
    return path;
  }

  @override
  bool shouldReclip(StarPath oldClipper) {
    return oldClipper.scale != this.scale;
  }
}

class TrianglePath extends CustomClipper<Path> {
  @override
  Path getClip(Size size) {
    var path = Path();
    path.moveTo(size.width / 2, 0);
    path.lineTo(0, size.height);
    path.lineTo(size.width, size.height);
    return path;
  }

  @override
  bool shouldReclip(CustomClipper<Path> oldClipper) {
    return true;
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

  var _switchValue = false;

  @override
  Widget build(BuildContext context) {
    return CupertinoTabScaffold(
      tabBar: CupertinoTabBar(
        items: [
          BottomNavigationBarItem(icon: Icon(Icons.home), title: Text('tab1')),
          BottomNavigationBarItem(icon: Icon(Icons.home), title: Text('tab2')),
        ],
        onTap: (index) {
          print('$index');
        },
        currentIndex: 0,
        backgroundColor: Colors.blue,
        activeColor: Colors.red,
      ),
      tabBuilder: (BuildContext context, int index) {
        return CupertinoTabView(
          defaultTitle: '老孟',
          builder: (context) {
            var page = TextFieldPage();
            page.title = '$index';
            return page;
          },
        );
      },
    );
  }
}
