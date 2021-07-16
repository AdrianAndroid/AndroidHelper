import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DecoratedBoxTransitionApp extends StatelessWidget {
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
        title: Text('ZhaoJian'),
      ),
      body: _AnimationDemo(),
      // body: buildDataTable(),
    );
  }
}

class _AnimationDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _AnimationDemoState();
}

void test(BuildContext context) {
  DefaultAssetBundle.of(context).loadString("images/beatiful_lady.jpeg");
}

class _AnimationDemoState extends State<_AnimationDemo>
    with SingleTickerProviderStateMixin {
  AnimationController _animationController;
  Animation _animation;

  @override
  void initState() {
    _animationController =
        AnimationController(duration: Duration(seconds: 2), vsync: this);
    _animationController.addListener(() {
      if (_animationController.status == AnimationStatus.completed) {
        _animationController.reverse();
      } else if (_animationController.status == AnimationStatus.dismissed) {
        _animationController.forward();
      }
    });

    _animation = DecorationTween(
        begin: BoxDecoration(
          color: Colors.red,
          borderRadius: BorderRadius.circular(15),
        ),
        end: BoxDecoration(
          color: Colors.blue,
          borderRadius: BorderRadius.circular(55),
        )).animate(_animationController);

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
    return DecoratedBoxTransition(
      decoration: _animation,
      child: Container(
        height: 100,
        width: 100,
      ),
    );
  }
}
