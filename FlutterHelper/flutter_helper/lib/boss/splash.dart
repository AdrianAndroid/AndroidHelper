import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'mainpage.dart';

class SplashPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return SplashState();
  }
}

class SplashState extends State<SplashPage> {

  Timer _t; // 延迟

  @override
  void initState() {
    _t = Timer(Duration(milliseconds: 100), () {
      try {
        Navigator.of(context).pushAndRemoveUntil(
            PageRouteBuilder<Null>(
                pageBuilder: (context, animation, secondaryAnimation) {
                  return AnimatedBuilder(animation: animation, builder:
                      (context, child) {
                    return Opacity(opacity: animation.value
                      , child: MainPage(),);
                  }
                  );
                }
            )
            , (route) => route == null);
      } catch (e) {

      }
    });
    super.initState();
  }

  @override
  void dispose() {
    _t.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      color: Color.fromARGB(255, 0, 215, 198),
      child: Container(
        alignment: Alignment(0, -0.3),
        child: Text(
          "BOOS",
          style: TextStyle(
            color: Colors.white,
            fontSize: 50.0,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    );
  }
}