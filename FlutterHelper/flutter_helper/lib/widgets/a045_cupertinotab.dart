import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class CupertinoTabBarApp extends StatelessWidget {
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

  var _switchValue = false;

  @override
  Widget build(BuildContext context) {
    return CupertinoTabScaffold(
      tabBar: CupertinoTabBar(
        items: [
          BottomNavigationBarItem(icon: Icon(Icons.home), title: Text('tab1')),
          BottomNavigationBarItem(icon: Icon(Icons.home), title: Text('tab2')),
        ],
        onTap: (index) {
          print('$index');
        },
        currentIndex: 1,
        backgroundColor: Colors.blue,
        activeColor: Colors.red,
      ),
      tabBuilder: (BuildContext context, int index) {
        return CupertinoTabView(
          defaultTitle: '老孟',
          builder: (context) {
            return Center(
              child: Text('$index'),
            );
          },
        );
      },
    );
  }
}
