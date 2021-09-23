import 'dart:math';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class CustomPaintApp extends StatelessWidget {
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
      body: SingleChildScrollView(
        child: Container(
          color: Colors.red,
          child: Column(
            children: [
              Container(
                width: 200,
                height: 200,
                color: Colors.blue,
                child: CustomPaint(
                  painter: MyCustomPainter(),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class MyCustomPainter extends CustomPainter {
  Paint _paint = Paint()
    ..color = Colors.red
    ..strokeWidth = 10;

  @override
  void paint(Canvas canvas, Size size) {
    var points = [
      Offset(20, 20),
      Offset(size.width / 2, size.height / 2),
      Offset(size.width - 20, size.height - 20),
    ];
    canvas.drawPoints(PointMode.points, points, _paint);
    _paint.strokeWidth = 5;
    canvas.drawLine(Offset(0,0), Offset(size.width, size.height), _paint);

    _paint.strokeWidth = 3;
    _paint.style = PaintingStyle.stroke;
    print('size:$size');
    var _path = Path()
      ..moveTo(20, 10)
      ..lineTo(size.width-10, 10)
      ..lineTo(size.width-10, size.height-20)
      ..close();
    canvas.drawPath(_path, _paint);


    _path = Path()
      ..moveTo(60, 20)
      ..lineTo(size.width-20, 20)
      ..lineTo(size.width-20, size.height-60)
      ..close();
    _paint.style = PaintingStyle.fill;
    canvas.drawPath(_path, _paint);

    canvas.drawCircle(Offset(size.width/2, size.height/2), 20, _paint);
    canvas.drawOval(Rect.fromLTRB(40, 0, size.width/2, size.height/2), _paint);

    _paint.style = PaintingStyle.stroke;
    canvas.drawArc(Rect.fromLTRB(70,70, size.width-20, size.height-20), 0,
        pi/2,
        true, _paint);

    _paint.color = Colors.yellow;
    _paint.strokeWidth = 10;
    canvas.drawRRect(RRect.fromLTRBR(0, 0, size.width, size.height, Radius
        .circular(10)), _paint);
  }

  ///
  /// 绘制花骨朵
  ///
  // _drawFlower(Canvas canvas, Size size) {
  //   //将花变为红色
  //   if (flowerPaths.length >= RoseData.flowerPoints.length) {
  //     var path = Path();
  //     for (int i = 0; i < flowerPaths.length; i++) {
  //       if (i == 0) {
  //         path.moveTo(flowerPaths[i].dx, flowerPaths[i].dy);
  //       } else {
  //         path.lineTo(flowerPaths[i].dx, flowerPaths[i].dy);
  //       }
  //     }
  //     _paint.style = PaintingStyle.fill;
  //     _paint.color = _flowerColor;
  //     canvas.drawPath(path, _paint);
  //   }
  //   //绘制线
  //   _paint.style = PaintingStyle.stroke;
  //   _paint.color = _strokeColor;
  //   //去掉最后2个点，最后2个点为了绘制红色
  //   var points = flowerPaths.sublist(0, max(0, flowerPaths.length - 2));
  //   canvas.drawPoints(PointMode.polygon, points, _paint);
  // }
  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return this != oldDelegate;
  }
}
