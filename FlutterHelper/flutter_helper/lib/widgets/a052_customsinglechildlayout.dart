import 'dart:math';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class CustomSingleChildLayoutApp extends StatelessWidget {
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

class MySingleChildLayoutDelegate extends SingleChildLayoutDelegate {
  final Offset position;

  MySingleChildLayoutDelegate(this.position);

  @override
  Offset getPositionForChild(Size size, Size childSize) {
    return Offset(position.dx, position.dy);
  }

  @override
  bool shouldRelayout(covariant MySingleChildLayoutDelegate oldDelegate) {
    return oldDelegate.position != position;
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

  buildBody() => Container(
        width: 200,
        height: 200,
        color: Colors.blue,
        child: CustomSingleChildLayout(
          delegate: MySingleChildLayoutDelegate(Offset(10, 10)),
          child: Container(
            color: Colors.red,
          ),
        ),
      );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('ZhaoJian'),
      ),
      body: buildBody(),
    );
  }
}
