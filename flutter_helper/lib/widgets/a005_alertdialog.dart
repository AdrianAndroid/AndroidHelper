import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AlertDialogApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: AlertDialogPage(title: 'Flutter Demo Home Page'),
    );
  }
}

class AlertDialogPage extends StatefulWidget {
  AlertDialogPage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _DialogHomePageState createState() => _DialogHomePageState();
}

class _DialogHomePageState extends State<AlertDialogPage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      showSimpleDialog();
      _counter++;
    });
  }

  void showAlertDialog1() {
    showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('显示'),
            content: Text('确认删除吗?'),
            actions: [
              FlatButton(onPressed: () {}, child: Text('取消')),
              FlatButton(onPressed: () {}, child: Text('确认')),
            ],
          );
        });
  }

  void showAlertDialog2() {
    showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('显示'),
            content: Text('确认删除吗?'),
            backgroundColor: Colors.lightBlueAccent,
            elevation: 24,
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(50)),
            actions: [
              FlatButton(onPressed: () {}, child: Text('取消')),
              FlatButton(onPressed: () {}, child: Text('确认')),
            ],
          );
        });
  }

  void showAlertDialog3() async {
    var result = await showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('显示'),
            content: Text('确认删除吗?'),
            backgroundColor: Colors.lightBlueAccent,
            elevation: 24,
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(50)),
            actions: [
              FlatButton(
                  onPressed: () {
                    Navigator.of(context).pop('cancel');
                  },
                  child: Text('取消')),
              FlatButton(
                  onPressed: () {
                    Navigator.of(context).pop('ok');
                  },
                  child: Text('确认')),
            ],
          );
        });
    print("the result is $result");
  }

  void showCupertinoAlertDialog() {
    showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('显示'),
            content: Text('确认删除吗？'),
            actions: [
              CupertinoDialogAction(
                child: Text('取消'),
                onPressed: () {},
              ),
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {},
              ),
            ],
          );
        });
  }

  // http://laomengit.com/flutter/widgets/Dialog.html
  void showSimpleDialog___() {
    SimpleDialog(
      title: Text('提示'),
      children: <Widget>[
        Container(
          height: 80,
          alignment: Alignment.center,

          child: Text('确认删除吗？'),
        ),
        Divider(height: 1,),
        FlatButton(
          child: Text('取消'),
          onPressed: () {
            Navigator.of(context).pop('cancel');
          },
        ),
        Divider(height: 1,),
        FlatButton(
          child: Text('确认'),
          onPressed: () {
            Navigator.of(context).pop('ok');
          },
        ),
      ],
    );

    // SimpleDialog(
    //   title: Text('提示'),
    //   children: [
    //     Container(
    //       height: 80,
    //       alignment: Alignment.center,
    //       child: Text('确认删除吗？'),
    //     ),
    //     Divider(
    //       height: 1,
    //     ),
    //     FlatButton(
    //         onPressed: () {
    //           Navigator.of(context).pop('cancel');
    //         },
    //         child: Text('取消')),
    //     Divider(
    //       height: 1,
    //     ),
    //     FlatButton(onPressed: () {}, child: Text('确认')),
    //   ],
    // );
  }

  // SimpleDialog
  void showSimpleDialog() {
    showDialog(
        context: context,
        builder: (BuildContext context) {
          return SimpleDialog(
            title: Text('选择'),
            children: [
              SimpleDialogOption(
                child: Text('showAlertDialog1'),
                onPressed: () {
                  Navigator.of(context).pop();
                  showAlertDialog1();
                },
              ),
              SimpleDialogOption(
                child: Text('showAlertDialog2'),
                onPressed: () {
                  Navigator.of(context).pop();
                  showAlertDialog2();
                },
              ),
              SimpleDialogOption(
                child: Text('showAlertDialog3'),
                onPressed: () async {
                  // 等待结果
                  //Navigator.of(context).pop();
                  showAlertDialog3();
                },
              ),
              SimpleDialogOption(
                child: Text('showCupertinoAlertDialog'),
                onPressed: () {
                  // 等待结果
                  //Navigator.of(context).pop();
                  showCupertinoAlertDialog();
                },
              ),
              SimpleDialogOption(
                child: Text('showSimpleDialog___'),
                onPressed: () {
                  // 等待结果
                  //Navigator.of(context).pop();
                  showSimpleDialog___();
                },
              ),
            ],
          );
        });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
