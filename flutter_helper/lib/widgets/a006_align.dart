import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AlignApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: AlignPage(),
    );
  }
}

class AlignPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => AlignPageState();
}

class AlignPageState extends State<AlignPage>
    with SingleTickerProviderStateMixin {
  AnimationController _animationController;
  Animation<AlignmentGeometry> _animation;

  var _alignment = Alignment.topLeft;

  @override
  void initState() {
    _animationController =
        AnimationController(duration: Duration(seconds: 10), vsync: this);
    _animation = Tween<AlignmentGeometry>(
            begin: Alignment.topLeft, end: Alignment.bottomRight)
        .animate(_animationController);
    //开始动画
    _animationController.forward();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    print("$_animation");
    return Scaffold(
      appBar: AppBar(
        title: Text("Zhaojian"),
      ),
      body: Center(
          child: Scrollbar(
        child: SingleChildScrollView(
          //physics: ClampingScrollPhysics(),
          physics: AlwaysScrollableScrollPhysics(),
          child: Column(
            children: [
              Container(
                width: 200,
                height: 200,
                color: Colors.black12,
                // http://laomengit.com/flutter/widgets/AnimatedAlign.html
                child: AnimatedAlign(
                  onEnd: () {
                    print('onEnd');
                  },
                  curve: Curves.bounceInOut,
                  alignment: _alignment,
                  duration: Duration(seconds: 10),
                  child: IconButton(
                      icon: Icon(
                        Icons.print,
                        color: Colors.red,
                        size: 30,
                      ),
                      onPressed: () {
                        setState(() {
                          _alignment = Alignment.bottomRight;
                        });
                      }),
                ),
              ),
              Divider(
                height: 1,
              ),
              Container(
                height: 200,
                width: 200,
                color: Colors.blue,
                child: AlignTransition(
                  alignment: _animation,
                  child: Container(
                    height: 30,
                    width: 30,
                    color: Colors.red,
                  ),
                ),
              ),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  child: Align(
                    alignment: Alignment.topLeft,
                    widthFactor: 2,
                    heightFactor: 2,
                    child: Container(
                      height: 50,
                      width: 50,
                      color: Colors.red,
                    ),
                  )),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  width: 100,
                  height: 100,
                  child: Align(
                    alignment: Alignment.topLeft,
                    child: Text(
                      'AA',
                      style: TextStyle(color: Colors.white, fontSize: 20),
                    ),
                  )),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  width: 100,
                  height: 100,
                  child: Align(
                    alignment: Alignment.topCenter,
                    child: Text(
                      'AA',
                      style: TextStyle(color: Colors.white, fontSize: 20),
                    ),
                  )),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  width: 100,
                  height: 100,
                  child: Align(
                    alignment: Alignment.topRight,
                    child: Text(
                      'AA',
                      style: TextStyle(color: Colors.white, fontSize: 20),
                    ),
                  )),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  width: 100,
                  height: 100,
                  child: Align(
                    alignment: Alignment.centerLeft,
                    child: Text(
                      'AA',
                      style: TextStyle(color: Colors.white, fontSize: 20),
                    ),
                  )),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  width: 100,
                  height: 100,
                  child: Align(
                    alignment: Alignment.center,
                    child: Text(
                      'AA',
                      style: TextStyle(color: Colors.white, fontSize: 20),
                    ),
                  )),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  width: 100,
                  height: 100,
                  child: Align(
                    alignment: Alignment.centerRight,
                    child: Text(
                      'AA',
                      style: TextStyle(color: Colors.white, fontSize: 20),
                    ),
                  )),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  width: 100,
                  height: 100,
                  child: Align(
                    alignment: Alignment.bottomLeft,
                    child: Text(
                      'AA',
                      style: TextStyle(color: Colors.white, fontSize: 20),
                    ),
                  )),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  width: 100,
                  height: 100,
                  child: Align(
                    alignment: Alignment.bottomCenter,
                    child: Text(
                      'AA',
                      style: TextStyle(color: Colors.white, fontSize: 20),
                    ),
                  )),
              Divider(
                height: 1,
              ),
              Container(
                  color: Colors.lightBlue,
                  width: 100,
                  height: 100,
                  child: Align(
                    alignment: Alignment.bottomRight,
                    child: Text(
                      'AA',
                      style: TextStyle(color: Colors.white, fontSize: 20),
                    ),
                  ))
            ],
          ),
        ),
      )),
    );
  }
}
