import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DialogsApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print('${context.toString()}');
    return MaterialApp(
      title: "Flutter DEMO APP",
      // localizationsDelegates: [
      //   GlobalMaterialLocalizations.delegate,
      //   GlobalMaterialLocalizations.delegate,
      // ],
      // supportedLocales: [
      //   const Locale('zh', 'CH'),
      //   const Locale('en', 'US'),
      // ],
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
  Animation<double> animation;
  AnimationController controller;

  @override
  void initState() {
    controller =
        AnimationController(vsync: this, duration: Duration(seconds: 1))
          ..repeat();
    animation = Tween(begin: 0.0, end: 1.0).animate(controller);
    controller.forward();

    super.initState();
  }

  @override
  void dispose() {
    controller.dispose();
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

  buildBody() => SingleChildScrollView(
        child: Column(
          children: [
            RaisedButton(onPressed: () {
              showGeneralDialog(
                  context: context,
                  barrierDismissible: true,
                  barrierLabel: '',
                  transitionDuration: Duration(milliseconds: 200),
                  pageBuilder: (BuildContext context,
                      Animation<double> animation,
                      Animation<double> secondaryAnimation) {
                    return Center(
                      child: Container(
                        height: 300,
                        width: 250,
                        color: Colors.lightGreenAccent,
                      ),
                    );
                  },
                  transitionBuilder: (BuildContext context,
                      Animation<double> animation,
                      Animation<double> secodaryAnimation,
                      Widget child) {
                    return ScaleTransition(
                      scale: animation,
                      child: child,
                    );
                  });
            }),
            RaisedButton(onPressed: () {
              showAboutDialog(
                  context: context,
                  applicationIcon: Image.asset(
                    'images/bird.png',
                    width: 100,
                    height: 100,
                  ),
                  applicationName: '应用程序',
                  applicationVersion: '1.0.0',
                  applicationLegalese: 'copyright 老孟，一枚有态度的程序员',
                  children: [
                    Container(
                      height: 30,
                      color: Colors.red,
                    ),
                    Container(
                      height: 30,
                      color: Colors.blue,
                    ),
                    Container(
                      height: 30,
                      color: Colors.green,
                    )
                  ]);
            }),
            RaisedButton(onPressed: () {
              showMenu(
                  initialValue: PopupMenuItem(child: Text('语文')),
                  context: context,
                  position: RelativeRect.fill,
                  items: <PopupMenuEntry>[
                    PopupMenuItem(child: Text('语文')),
                    PopupMenuDivider(),
                    CheckedPopupMenuItem(
                      child: Text('数学'),
                      checked: true,
                    ),
                    PopupMenuDivider(),
                    PopupMenuItem(child: Text('英语')),
                  ]);
            }),
            RaisedButton(onPressed: () {
              showSearch(context: context, delegate: CustomSearchDelegate());
            }),
            RaisedButton(onPressed: () {}),
          ],
        ),
      );
}

class CustomSearchDelegate extends SearchDelegate<String> {
  @override
  List<Widget> buildActions(BuildContext context) {
    return null;
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      icon: Icon(Icons.arrow_back,color: Colors.blue,),
      onPressed: (){
        close(context, '');
      },
    );
  }
  @override
  Widget buildResults(BuildContext context) {
    return ListView.separated(
      itemBuilder: (context, index) {
        return Container(
          height: 60,
          alignment: Alignment.center,
          child: Text(
            '$index',
            style: TextStyle(fontSize: 20),
          ),
        );
      },
      separatorBuilder: (context, index) {
        return Divider();
      },
      itemCount: 10,
    );
  }


  @override
  Widget buildSuggestions(BuildContext context) {
    return ListView.separated(
      itemBuilder: (context, index) {
        return ListTile(
          title: Text('老孟 $index'),
          onTap: () {
            query = '老孟 $index';
          },
        );
      },
      separatorBuilder: (context, index) {
        return Divider();
      },
      itemCount: Random().nextInt(5),
    );
  }

}
