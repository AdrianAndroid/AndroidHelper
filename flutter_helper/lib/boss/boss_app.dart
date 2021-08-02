import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/base_app.dart';
import 'package:flutter_helper/boss/splash.dart';

class BossApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
          primaryIconTheme: IconThemeData(color: Colors.white),
          brightness: Brightness.light,
          primaryColor: Color.fromARGB(255, 0, 215, 198),
          accentColor: Colors.cyan[300]),
      home: Scaffold(
        appBar: AppBar(
          title: Text("BossApp"),
        ),
        body: SplashPage(),
      ),
    );
  }
}
