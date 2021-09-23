import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

/// http://laomengit.com/flutter/widgets/AnimatedContainer.html
class AnimatedCrossFadeApp extends StatelessWidget {
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
  bool _showFirst = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("zhaojian"),
      ),
      body: Center(
        child: AnimatedCrossFade(
            firstChild: Container(
              width: 150,
              height: 150,
              alignment: Alignment.center,
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: Colors.blue,
              ),
              child: Text(
                'first child',
                style: TextStyle(color: Colors.white),
              ),
            ),
            secondChild: Container(
              width: 150,
              height: 150,
              alignment: Alignment.center,
              decoration: BoxDecoration(
                shape: BoxShape.rectangle,
                color: Colors.orange,
                borderRadius: BorderRadius.circular(20),
              ),
              child: Text(
                'second child',
                style: TextStyle(color: Colors.white),
              ),
            ),
            crossFadeState: _showFirst
                ? CrossFadeState.showFirst
                : CrossFadeState.showSecond,
            duration: Duration(seconds: 1)),
      ),
      floatingActionButton: FloatingActionButton(onPressed: () {
        setState(() {
          _showFirst = !_showFirst;
        });
      }),
    );
  }
}
