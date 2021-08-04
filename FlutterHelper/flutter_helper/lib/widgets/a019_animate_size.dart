import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/AnimatedModalBarrier.html#animatedmodalbarrier
class AnimatedSizeApp extends StatelessWidget {
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

  var _height = 100.0;
  var _width = 100.0;
  var _color = Colors.red;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('zhaojan'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            RaisedButton(onPressed: () {
              setState(() {
                if(_height == 100.0) {
                  _height = 200.0;
                  _width = 200.0;
                  _color = Colors.blue;
                } else {
                  _height = 100.0;
                  _width = 100.0;
                  _color = Colors.green;
                }
              });
            }),
            AnimatedSize(
              duration: Duration(seconds: 1),
              vsync: this,
              child: Container(
                height: _height,
                width: _width,
                color: _color,
              ),
            )
          ],
        ),
      ),
      floatingActionButton: RaisedButton(
        onPressed: () {
          setState(() {});
        },
      ),
    );
  }
}
