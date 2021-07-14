import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/AnimatedModalBarrier.html#animatedmodalbarrier
class AnimatedSwitcherApp extends StatelessWidget {
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
  var _firstChild = Container(
    key: ValueKey("1"),
    height: 300,
    width: 300,
    color: Colors.red,
  );

  var _secondChild = Container(
    key: ValueKey("2"),
    height: 100,
    width: 100,
    color: Colors.green,
  );

  var _displayChild;

  @override
  void initState() {
    _displayChild = _firstChild;
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
        title: Text('zhaojan'),
      ),
      body: Center(
        child: AnimatedSwitcher(
          duration: Duration(seconds: 1),
          child: _displayChild,
          switchInCurve: Curves.bounceInOut,
          transitionBuilder: (Widget child, Animation<double> value) {
            return ScaleTransition(scale: value, child: child,);
          },
        ),
      ),
      floatingActionButton: RaisedButton(
        onPressed: () {
          setState(() {
            if (_displayChild == _firstChild) {
              _displayChild = _secondChild;
            } else {
              _displayChild = _firstChild;
            }
          });
        },
      ),
    );
  }
}
