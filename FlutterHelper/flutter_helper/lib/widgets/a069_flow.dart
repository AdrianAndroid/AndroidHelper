import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FlowApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print('${context.toString()}');
    return MaterialApp(
      title: "Flutter DEMO APP",
      locale: Locale('zh'),
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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('ZhaoJian'),
      ),
      body: Builder(builder: (context) {
        return buildBody();
      }),
      // body: buildDataTable(),
      floatingActionButton: RaisedButton(
        child: CircleAvatar(
          child: Text('点'),
        ),
        onPressed: () {
          showBottomSheet(
            context: context,
            backgroundColor: Colors.lightGreenAccent,
            elevation: 20,
            shape: CircleBorder(),
            builder: (context) {
              return Container(
                height: 200,
                color: Colors.lightBlue,
              );
            },
          );
        },
      ),
    );
  }

  List<bool> dataList = List.generate(20, (index) => false).toList();

  buildBody() => CustomScrollView(
        slivers: [
          SliverAppBar(
            pinned: true,
            expandedHeight: 200.0,
            stretch: true,
            flexibleSpace: FlexibleSpaceBar(
              stretchModes: [
                StretchMode.zoomBackground,
                StretchMode.blurBackground
              ],
              title: Text('复仇者联盟'),
              background: Image.network(
                'http://img.haote.com/upload/20180918/2018091815372344164.jpg',
                fit: BoxFit.fitHeight,
              ),
            ),
          ),
          SliverList(
            delegate: SliverChildBuilderDelegate((context, index) {
              return Container(
                height: 65,
                color: Colors.primaries[index % Colors.primaries.length],
              );
            }, childCount: 50),
          ),
        ],
      );
}

class DemoFlowPopMenuApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: DemoFlowPopMenu(),
    );
  }
}

class DemoFlowPopMenu extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _DemoFlowPopMenuState();
}

class _DemoFlowPopMenuState extends State<DemoFlowPopMenu>
    with SingleTickerProviderStateMixin {
  // 动画必须要with这个类
  AnimationController _ctrlAnimationPopMenu; //自定义动画的变量
  IconData lastTapped = Icons.notifications;
  final List<IconData> menuItems = [
    //菜单的icon
    Icons.home,
    Icons.new_releases,
    Icons.notifications,
    Icons.settings,
    Icons.menu,
  ];

  void _updateMenu(IconData icon) {
    if (icon != Icons.menu) {
      setState(() {
        lastTapped = icon;
      });
    } else {
      if (_ctrlAnimationPopMenu.status == AnimationStatus.completed) {
        _ctrlAnimationPopMenu.reverse();
      } else {
        _ctrlAnimationPopMenu.forward();
      }
    }
  }

  @override
  void initState() {
    _ctrlAnimationPopMenu = AnimationController(
      //必须初始化动画变量
      duration: const Duration(milliseconds: 250),
      vsync: this,
    );
    super.initState();
  }

  // 生成Popmenu数据
  Widget flowMenuItem(IconData icon) {
    final double buttonDiameter =
        MediaQuery.of(context).size.width * 2 / (menuItems.length * 3);
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: RawMaterialButton(
        fillColor: lastTapped == icon ? Colors.amber[700] : Colors.blue,
        splashColor: Colors.amber[100],
        shape: CircleBorder(),
        constraints: BoxConstraints.tight(Size(buttonDiameter, buttonDiameter)),
        onPressed: () {
          _updateMenu(icon);
        },
        child: Icon(
          icon,
          color: Colors.white,
          size: 30.0,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Flow(
        delegate: FlowMenuDelegate(animation: _ctrlAnimationPopMenu),
        children: menuItems.map((e) => flowMenuItem(e)).toList(),
      ),
    );
  }
}

class FlowMenuDelegate extends FlowDelegate {
  final Animation<double> animation;

  FlowMenuDelegate({this.animation}) : super(repaint: animation);

  @override
  void paintChildren(FlowPaintingContext context) {
    double x = 50.0;
    double y = 50.0;
    for (int i = 0; i < context.childCount; i++) {
      x = context.getChildSize(i).width * i * animation.value;
      context.paintChild(i, transform: Matrix4.translationValues(x, y, 0));
    }
  }

  @override
  bool shouldRepaint(covariant FlowMenuDelegate oldDelegate) {
    return animation != oldDelegate.animation;
  }
}

class DemoFlowCircelApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: DemoFlowCircle(),
    );
  }
}

class DemoFlowCircle extends StatefulWidget {
  @override
  _DemoFlowCircleState createState() => _DemoFlowCircleState();
}

class _DemoFlowCircleState extends State<DemoFlowCircle>
    with TickerProviderStateMixin {
  //动画需要这个类来混合
  //动画变量,以及初始化和销毁
  AnimationController _ctrlAnimationCircle;

  @override
  void initState() {
    super.initState();
    _ctrlAnimationCircle = AnimationController(
        //初始化动画变量
        lowerBound: 0,
        upperBound: 80,
        duration: Duration(seconds: 3),
        vsync: this);
    _ctrlAnimationCircle.addListener(() => setState(() {}));
  }

  @override
  void dispose() {
    _ctrlAnimationCircle.dispose(); //销毁变量,释放资源
    super.dispose();
  }

  //生成Flow的数据
  List<Widget> _buildFlowChildren() {
    return List.generate(
        15,
        (index) => Container(
              child: Icon(
                index.isEven ? Icons.timer : Icons.ac_unit,
                color: Colors.primaries[index % Colors.primaries.length],
              ),
            ));
  }

//系统生成页面
  @override
  Widget build(BuildContext context) {
    return Center(
      child: GestureDetector(
        onTap: () {
          setState(() {
            //点击后让动画可前行或回退
            _ctrlAnimationCircle.status == AnimationStatus.completed
                ? _ctrlAnimationCircle.reverse()
                : _ctrlAnimationCircle.forward();
          });
        },
        child: Container(
          color: Colors.blueAccent.withOpacity(0.4),
          width: 200,
          height: 200,
          child: Flow(
            delegate: FlowAnimatedCircle(_ctrlAnimationCircle.value),
            children: _buildFlowChildren(),
          ),
        ),
      ),
    );
  }
}

class FlowAnimatedCircle extends FlowDelegate {
  final double radius; //绑定半径,让圆动起来
  FlowAnimatedCircle(this.radius);

  @override
  void paintChildren(FlowPaintingContext context) {
    double x = 0; //开始(0,0)在父组件的中心
    double y = 0;
    for (int i = 0; i < context.childCount; i++) {
      x = radius * cos(i * 2 * pi / (context.childCount - 1)); //根据数学得出坐标
      y = radius * sin(i * 2 * pi / (context.childCount - 1)); //根据数学得出坐标
      context.paintChild(i, transform: Matrix4.translationValues(x, y, 0));
    } //使用Matrix定位每个子组件
  }

  @override
  bool shouldRepaint(FlowDelegate oldDelegate) => true;
}

class DemoFlowMenuApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    MaterialApp(
      home: Scaffold(
        body: DemoFlowMenu(),
      ),
    );
  }
}

class DemoFlowMenu extends StatefulWidget {
  @override
  _DemoFlowMenuState createState() => _DemoFlowMenuState();
}

class _DemoFlowMenuState extends State<DemoFlowMenu>
    with TickerProviderStateMixin {
  //动画需要这个类来混合
  //动画变量,以及初始化和销毁
  AnimationController _ctrlAnimationCircle;

  @override
  void initState() {
    super.initState();
    _ctrlAnimationCircle = AnimationController(
        //初始化动画变量
        lowerBound: 0,
        upperBound: 80,
        duration: Duration(milliseconds: 300),
        vsync: this);
    _ctrlAnimationCircle.addListener(() => setState(() {}));
  }

  @override
  void dispose() {
    _ctrlAnimationCircle.dispose(); //销毁变量,释放资源
    super.dispose();
  }

  //生成Flow的数据
  List<Widget> _buildFlowChildren() {
    return List.generate(
        5,
        (index) => Container(
              child: Icon(
                index.isEven ? Icons.timer : Icons.ac_unit,
                color: Colors.primaries[index % Colors.primaries.length],
              ),
            ));
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Positioned.fill(
          child: Flow(
            delegate: FlowAnimatedCircle2(_ctrlAnimationCircle.value),
            children: _buildFlowChildren(),
          ),
        ),
        Positioned.fill(
          child: IconButton(
            icon: Icon(Icons.menu),
            onPressed: () {
              setState(() {
                //点击后让动画可前行或回退
                _ctrlAnimationCircle.status == AnimationStatus.completed
                    ? _ctrlAnimationCircle.reverse()
                    : _ctrlAnimationCircle.forward();
              });
            },
          ),
        ),
      ],
    );
  }
}

class FlowAnimatedCircle2 extends FlowDelegate {
  final double radius; //绑定半径,让圆动起来
  FlowAnimatedCircle2(this.radius);

  @override
  void paintChildren(FlowPaintingContext context) {
    if (radius == 0) {
      return;
    }
    double x = 0; //开始(0,0)在父组件的中心
    double y = 0;
    for (int i = 0; i < context.childCount; i++) {
      x = radius * cos(i * pi / (context.childCount - 1)); //根据数学得出坐标
      y = radius * sin(i * pi / (context.childCount - 1)); //根据数学得出坐标
      context.paintChild(i, transform: Matrix4.translationValues(x, -y, 0));
    } //使用Matrix定位每个子组件
  }

  @override
  bool shouldRepaint(FlowDelegate oldDelegate) => true;
}
