import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/Builder.html#%E4%BD%BF%E7%94%A8%E5%9C%BA%E6%99%AF%E4%BA%8C
class BuildApp extends StatelessWidget {
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

class _HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('老慢'),
      ),
      // body: Center(
      //   child: RaisedButton(
      //     color: Colors.pink,
      //     textColor: Colors.white,
      //     onPressed: _displaySnackBar(context),
      //     child: Text('show snakbar!'),
      //   ),
      // ),
      body: Builder(
        builder: (context) => Center(
          child: RaisedButton(
            onPressed: () {
              _displaySnackBar(context);
            },
            color: Colors.pink,
            textColor: Colors.white,
            child: Text('Hello'),
          ),
        ),
      ),
    );
  }

  _displaySnackBar(BuildContext context) {
    // final snackbar = SnackBar(content: Text("hello"));
    // Scaffold.of(context).showSnackBar(snackbar);


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
