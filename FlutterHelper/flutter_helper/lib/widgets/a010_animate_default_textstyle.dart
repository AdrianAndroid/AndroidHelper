import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';


///http://laomengit.com/flutter/widgets/AnimatedDefaultTextStyle.html#animateddefaulttextstyle
class AnimatedDefaultTextStyleApp extends StatelessWidget {
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
  TextStyle _style;

  @override
  void initState() {
    _style = TextStyle(color: Colors.blue, fontSize: 14);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("FFFFFF"),
      ),
      body: Column(
        children: [
          SizedBox(
            height: 200,
          ),
          AnimatedDefaultTextStyle(
            child: Text('老孟'),
            style: _style,
            duration: Duration(seconds: 1),
          ),
          SizedBox(
            height: 100,
          ),
          RaisedButton(onPressed: () {
            setState(() {
              if (_style.fontSize == 42) {
                _style = TextStyle(color: Colors.red, fontSize: 14);
              } else {
                _style = TextStyle(color: Colors.red, fontSize: 42);
              }
            });
          }),
        ],
      ),
    );
  }
}
