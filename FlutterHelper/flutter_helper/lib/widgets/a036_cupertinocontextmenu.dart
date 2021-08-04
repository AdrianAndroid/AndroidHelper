import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class CupertinoContextMenuApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter DEMO APP",
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: _HomePage(),
    );
  }
}

void test() {
  MaterialApp(
    routes: {
      'container': (context) => _HomePage(),
      'fitted': (context) => _HomePage(),
      'icon': (context) => _HomePage(),
    },
    initialRoute: '/',
    home: Scaffold(
      appBar: AppBar(
        title: Text('LLLL'),
      ),
    ),
    onGenerateRoute: (RouteSettings routeSettings) {
      print('onGenerateRouter:$routeSettings');
      if (routeSettings.name == 'icon') {
        return MaterialPageRoute(builder: (context) {
          return _HomePage();
        });
      }
      return null;
    },
    onUnknownRoute: (RouteSettings routeSettings) {
      print('onUnkonwRoute$routeSettings');
      return MaterialPageRoute(builder: (context) {
        return HomePage();
      });
    },
  );
}

class _HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('老慢'),
        ),
        body: Column(children: [
          CupertinoContextMenu(
              actions: [
                CupertinoContextMenuAction(
                  child: Text('Action one'),
                  onPressed: () {
                    Navigator.pop(context);
                  },
                ),
                CupertinoContextMenuAction(
                  child: Text('Action two'),
                  onPressed: () {
                    Navigator.pop(context);
                  },
                )
              ],
              child: Container(
                color: Colors.red,
                height: 60,
                width: 100,
              )),
          CupertinoContextMenu(
              previewBuilder: (context, animation, child) {
                return Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(10 * animation.value),
                    color: Colors.red,
                  ),
                  height: 60,
                  width: 100,
                );
              },
              actions: [
                CupertinoContextMenuAction(
                  isDefaultAction: true, //字体变粗
                  child: Text('Action one'),
                  onPressed: () {
                    Navigator.pop(context);
                  },
                ),
                CupertinoContextMenuAction(
                  isDestructiveAction: true, //变为红色字体了
                  child: Text('Action two'),
                  onPressed: () {
                    Navigator.pop(context);
                  },
                )
              ],
              child: Container(
                color: Colors.red,
                height: 60,
                width: 100,
              )),
        ]),
        floatingActionButton: RaisedButton(
          child: CircleAvatar(
            radius: 40,
            backgroundImage: AssetImage('images/beatiful_lady.jpeg'),
            child: Text('梦'),
          ),
          onPressed: () async {
            print("print");
            var result = await showCupertinoModalPopup(
                context: context,
                builder: (context) {
                  return CupertinoActionSheet(
                    title: Text('tips'),
                    message: Text('can u sure delete it?'),
                    cancelButton: CupertinoActionSheetAction(
                      child: Text('cancel'),
                      onPressed: () {
                        print("cancel button");
                      },
                    ),
                    actions: [
                      CupertinoActionSheetAction(
                        onPressed: () {
                          print("DEL button");
                          Navigator.of(context).pop('delete');
                        },
                        child: Text('DEL'),
                        isDefaultAction: true,
                      ),
                      CupertinoActionSheetAction(
                        onPressed: () {
                          print("OK button");
                        },
                        child: Text('OK'),
                        isDefaultAction: true,
                      ),
                    ],
                  );
                });
            print("result=$result");
          },
        ));
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

  var _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('zhaojan'),
      ),
      body: CustomScrollView(
        physics: NeverScrollableScrollPhysics(),
      ),
      bottomNavigationBar: BottomNavigationBar(
        // type: BottomNavigationBarType.fixed,
        type: BottomNavigationBarType.shifting,
        selectedItemColor: Theme.of(context).primaryColor,
        unselectedItemColor: Colors.black,
        currentIndex: _currentIndex,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            title: Text('首页'),
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.book),
            title: Text('书籍'),
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.perm_identity),
            title: Text('我的'),
          ),
        ],
      ),
    );
  }
}
