import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/AnimatedModalBarrier.html#animatedmodalbarrier
class AnimatedPhysicalModelApp extends StatelessWidget {
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

  var _padding = 0.0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('zhaojan'),
      ),
      body: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            RaisedButton(
              onPressed: () {
                setState(() {
                  _animated = !_animated;
                });
              },
              child: Text('dong hua'),
            ),
            _buildAnimatedPhysicalModel(),
          ],
        ),
      ),
      floatingActionButton: RaisedButton(
        onPressed: () {
          setState(() {
            if (_padding == 0.0) {
              _padding = 50.0;
            } else {
              _padding = 0.0;
            }
          });
        },
      ),
    );
  }

  bool _animated = false;

  _buildAnimatedPhysicalModel() {
    return AnimatedPhysicalModel(
      child: Container(
        height: 100,
        width: 100,
      ),
      borderRadius: BorderRadius.circular(_animated ? 20 : 10),
      shape: BoxShape.rectangle,
      elevation: _animated ? 18 : 8,
      color: _animated ? Colors.blue : Colors.red,
      shadowColor: !_animated ? Colors.blue : Colors.red,
      duration: Duration(seconds: 1),
    );
  }
}
