import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/AnimatedModalBarrier.html#animatedmodalbarrier
class AnimatedOpacityApp extends StatelessWidget {
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

  var _opacity = 1.0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('zhaojan'),
      ),
      body: Center(
        child: Container(
          child: AnimatedOpacity(
            opacity: _opacity,
            duration: Duration(seconds: 2),
            child: Container(
              height: 60,
              width: 150,
              color: Colors.blue,
            ),
          ),
        ),
      ),
      floatingActionButton: RaisedButton(
        onPressed: () {
          setState(() {
            if (_opacity == 0) {
              _opacity = 1.0;
            } else {
              _opacity = 0;
            }
          });
        },
      ),
    );
  }
}
