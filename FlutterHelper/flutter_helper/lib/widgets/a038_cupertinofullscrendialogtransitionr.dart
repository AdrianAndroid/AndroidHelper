import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class CupertinoFullscreenDialogTransitionApp extends StatelessWidget {
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
    return Scaffold(
      appBar: AppBar(
        title: Text('zhaojan'),
      ),
      body: Column(
        children: [
          Expanded(child: Container()),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              RaisedButton(
                onPressed: () => _animationController.forward(),
                child: Text('Forward'),
              ),
              RaisedButton(
                onPressed: () => _animationController.reverse(),
                child: Text('Reverse'),
              ),
            ],
          ),
          CupertinoFullscreenDialogTransition(
              primaryRouteAnimation: _animationController,
              secondaryRouteAnimation: _animationController,
              child: Container(
                color: Colors.blueGrey,
                height: 300,
              ),
              linearTransition: true)
        ],
      ),

    );
  }
}
