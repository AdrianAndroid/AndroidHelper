import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';


///http://laomengit.com/flutter/widgets/AnimatedIcon.html
class AnimatedIconApp extends StatelessWidget {
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

class _TestState extends State<_Test> with TickerProviderStateMixin {
  //TextStyle _style;
  AnimationController _animationController;

  @override
  void initState() {
    //_style = TextStyle(color: Colors.blue, fontSize: 14);
    _animationController = AnimationController(
      duration: Duration(seconds: 1),
      vsync: this,
    )
      ..addStatusListener((status) {
        if (status == AnimationStatus.completed) {
          _animationController.reverse();
        } else if (status == AnimationStatus.dismissed) {
          _animationController.forward();
        }
      });
    _animationController.forward();
    super.initState();
  }

  @override
  void dispose() {
    // double: implement dispose
    super.dispose();
    _animationController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("FFFFFF"),
      ),
      body: Container(
        height: 100,
        width: 100,
        alignment: Alignment.center,
        child: AnimatedIcon(
          icon: AnimatedIcons.view_list,
          progress: _animationController,
        ),
      ),
    );
  }
}
