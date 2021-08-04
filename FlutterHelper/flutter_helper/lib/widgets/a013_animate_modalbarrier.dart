import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/AnimatedModalBarrier.html#animatedmodalbarrier
class AnimatedModalBarrierApp extends StatelessWidget {
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
    _animationController =
        AnimationController(duration: Duration(seconds: 2), vsync: this)
          ..addStatusListener((status) {
            if(status == AnimationStatus.completed)
              _animationController.reverse();
            else if(status == AnimationStatus.dismissed)
              _animationController.forward();
          });

    _animation = ColorTween(
      begin: Colors.red,
      end: Colors.blue,
    ).animate(_animationController);

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
    return Center(
      child: Container(
        height: 100,
        width: 100,
        child: AnimatedModalBarrier(
          color: _animation,
        ),
      ),
    );
  }
}
