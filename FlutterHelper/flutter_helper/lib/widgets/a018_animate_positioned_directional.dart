import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/AnimatedModalBarrier.html#animatedmodalbarrier
class AnimatedPositionedDirectionalApp extends StatelessWidget {
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

  var _start = 0.0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('zhaojan'),
      ),
      body: Center(
        child: Stack(
          alignment: Alignment.center,
          children: [
            AnimatedPositionedDirectional(
                start: _start,
                child: Container(
                  height: 50,
                  width: 50,
                  color: Colors.red,
                ),
                duration: Duration(seconds: 2)),
          ],
        ),
      ),
      floatingActionButton: RaisedButton(
        onPressed: () {
          setState(() {
            if (_start == 180.0) {
              _start = 0.0;
            } else {
              _start = 180.0;
            }
          });
        },
      ),
    );
  }
}
