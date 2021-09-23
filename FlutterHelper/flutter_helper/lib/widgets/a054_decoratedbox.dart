import 'dart:math';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class DecoratedBoxApp extends StatelessWidget {
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

  buildBody() => SingleChildScrollView(
        child: Column(
          children: [
            DecoratedBox(
              child: Container(
                width: 200,
                height: 200,
              ),
              decoration: BoxDecoration(
                gradient: RadialGradient(
                  center: const Alignment(-0.5, -0.6),
                  radius: 0.15,
                  colors: [
                    const Color(0xFFEEEEEE),
                    const Color(0xFF111133),
                  ],
                  stops: [0.9, 1.0]
                ),
              ),
            ),
            DecoratedBox(
              decoration: BoxDecoration(
                  image: DecorationImage(
                    image: NetworkImage(
                        'https://flutter.github.io/assets-for-api-docs/assets/widgets/owl-2.jpg'),
                    fit: BoxFit.cover,
                  ),
                  shape: BoxShape.circle,
                  color: Colors.blue,
                  // borderRadius: BorderRadius.circular(20),
                  border: Border.all(
                    color: Colors.red,
                    width: 2,
                  )),
              child: Container(
                height: 200,
                width: 200,
              ),
            ),
            DecoratedBox(
              decoration: BoxDecoration(
                  image: DecorationImage(
                    image: NetworkImage(
                        'https://flutter.github.io/assets-for-api-docs/assets/widgets/owl-2.jpg'),
                    fit: BoxFit.cover,
                  ),
                  shape: BoxShape.rectangle,
                  color: Colors.blue,
                  borderRadius: BorderRadius.circular(20),
                  border: Border.all(
                    color: Colors.red,
                    width: 2,
                  )),
              child: Container(
                height: 200,
                width: 200,
              ),
            ),
            DecoratedBox(
              decoration: BoxDecoration(
                  shape: BoxShape.rectangle,
                  color: Colors.blue,
                  borderRadius: BorderRadius.circular(20),
                  border: Border.all(
                    color: Colors.red,
                    width: 2,
                  )),
              child: Text('老孟，一个有态度的程序员'),
            ),
            SizedBox(
              height: 10,
            ),
            DecoratedBox(
              decoration: BoxDecoration(
                shape: BoxShape.rectangle,
                color: Colors.blue,
                borderRadius: BorderRadius.circular(20),
              ),
              child: Text('老孟，一个有态度的程序员'),
            ),
            SizedBox(
              height: 10,
            ),
            DecoratedBox(
              decoration:
                  BoxDecoration(shape: BoxShape.rectangle, color: Colors.blue),
              child: Text('老孟，一个有态度的程序员'),
            ),
          ],
        ),
      );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('ZhaoJian'),
      ),
      body: buildBody(),
      // body: buildDataTable(),
    );
  }
}
