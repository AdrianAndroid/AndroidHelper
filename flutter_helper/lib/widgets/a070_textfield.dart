import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class TextField222App extends StatelessWidget {
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
      body: Builder(builder: (context) {
        return buildBody();
      }),
      // body: buildDataTable(),
      floatingActionButton: RaisedButton(
        child: CircleAvatar(
          child: Text('点'),
        ),
        onPressed: () {
          showBottomSheet(
            context: context,
            backgroundColor: Colors.lightGreenAccent,
            elevation: 20,
            shape: CircleBorder(),
            builder: (context) {
              return Container(
                height: 200,
                color: Colors.lightBlue,
              );
            },
          );
        },
      ),
    );
  }

  List<bool> dataList = List.generate(20, (index) => false).toList();

  buildBody() => SingleChildScrollView(
        child: Column(
          children: [
            TextFormField(
              onSaved: (value) {
                print('$value');
              },
              autovalidate: false,
              validator: (String value) {
                return value.length >= 6 ? null : '账号最少6个字符';
              },
            ),
          ],
        ),
      );
}
