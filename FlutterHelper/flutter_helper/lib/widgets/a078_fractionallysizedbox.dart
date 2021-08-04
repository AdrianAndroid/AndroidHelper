import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/utils.dart';

///http://laomengit.com/flutter/widgets/NestedScrollView.html#%E6%BB%9A%E5%8A%A8%E9%9A%90%E8%97%8Fappbar
class FractionallySizedBoxApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print('${context.toString()}');
    return MaterialApp(
      title: "Flutter DEMO APP",
      locale: Locale('zh'),
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text(
            'FractionallySizedBox百分比布局示例',
            style: TextStyle(color: Colors.white),
          ),
          leading: GestureDetector(
            child: Icon(Icons.close),
            onTap: (){
              showToast("Close");
              Navigator.pop(context);
            },
          ),
          bottom: PreferredSize(
            child: Container(height: 48, color: Colors.red),
            preferredSize: Size.fromHeight(148),
          ),
        ),
        body: FractionallySizedBox(
          alignment: Alignment.center,
          widthFactor: 0.4,
          heightFactor: 0.5,
          child: Center(
            child: Container(
              color: Colors.green,
            ),
          ),
        ),
      ),
    );
  }
}
