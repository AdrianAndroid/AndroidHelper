import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
/// http://laomengit.com/flutter/widgets/AnimatedContainer.html
class AnimatedContainerApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter APP",
      theme: ThemeData(
        primarySwatch: Colors.blue,
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

class _TestState extends State<_Test> {
  bool click = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("zhaojian"),),
      body: Center(
        child: GestureDetector(
          onTap: () {
            setState(() {
              click = !click;
            });
          },
          child: AnimatedContainer(
            height: click ? 200 : 100,
            width: click ? 200 : 100,
            color: Colors.green,
            duration: Duration(seconds: 1),
          ),
        ),
      ),
    );
  }
}
