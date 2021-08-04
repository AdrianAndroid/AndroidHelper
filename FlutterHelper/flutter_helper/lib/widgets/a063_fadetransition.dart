import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FadeTransitionApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print('${context.toString()}');
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
  Animation<double> animation;
  AnimationController controller;

  @override
  void initState() {
    controller =
        AnimationController(vsync: this, duration: Duration(seconds: 1))
          ..repeat();
    animation = Tween(begin: 0.0, end: 1.0).animate(controller);
    controller.forward();

    super.initState();
  }

  @override
  void dispose() {
    controller.dispose();
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
          if (controller.status == AnimationStatus.completed) {
            controller.reverse();
          } else if (controller.status == AnimationStatus.dismissed) {
            controller.forward();
          }
        },
      ),
    );
  }

  List<bool> dataList = List.generate(20, (index) => false).toList();

  buildBody() => Center(
        child: FadeTransition(
          opacity: animation,
          child: Container(
            color: Colors.red,
            width: 100,
            height: 100,
          ),
        ),
      );
}
