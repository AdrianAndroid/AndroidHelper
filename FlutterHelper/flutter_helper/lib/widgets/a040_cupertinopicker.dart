import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class CupertinoPickerApp extends StatelessWidget {
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
    return CupertinoPageScaffold(
        navigationBar: CupertinoNavigationBar(
          leading: Icon(Icons.arrow_back),
          middle: Text('老孟'),
        ),
        child: Center(
          child: Column(
            children: [
              SizedBox(
                height: 500,
                child:  CupertinoPicker(
                  backgroundColor: Colors.red,
                  itemExtent: 45,
                  onSelectedItemChanged: (index) {
                    print('$index');
                  },
                  children: [
                    Container(
                      color: Colors.primaries[1],
                    ),
                    Container(
                      color: Colors.primaries[2],
                    ),
                    Container(
                      color: Colors.primaries[3],
                    ),
                    Container(
                      color: Colors.primaries[4],
                    ),
                    Container(
                      color: Colors.primaries[5],
                    ),
                    Container(
                      color: Colors.primaries[6],
                    ),
                  ],
                ),
              ),

            ],
          ),
        ));
  }
}
