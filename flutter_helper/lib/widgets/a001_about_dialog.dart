import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DialogHomeApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: DialogHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class DialogHomePage extends StatefulWidget {
  DialogHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _DialogHomePageState createState() => _DialogHomePageState();
}

class _DialogHomePageState extends State<DialogHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      showSimpleDialog();
      _counter++;
    });
  }

  // 显示第一个Dialog
  void showAlertDialog() {
    showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return new AlertDialog(
            title: Text("标题"),
            //可滑动
            content: SingleChildScrollView(
              child: ListBody(
                children: [
                  Text("内容1"),
                  Text("内容2"),
                  Text("内容1"),
                  Text("内容2"),
                  Text("内容2"),
                ],
              ),
            ),
            actions: [
              FlatButton(
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                  child: Text("确定")),
              FlatButton(
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                  child: Text("取消"))
            ],
          );
        });
  }

  void showCupertinoAlertDialog() {
    showDialog<void>(
      context: context,
      // false = user must tap button, true = tap outside dialog
      builder: (BuildContext dialogContext) {
        return CupertinoAlertDialog(
          title: Text('这是一个iOS风格的对话框'),
          content: Column(
            children: [
              SizedBox(
                height: 10,
              ),
              Align(
                child: Text('这是消息'),
              )
            ],
          ),
          actions: [
            CupertinoDialogAction(
              child: Text('取消'),
              onPressed: () {
                Navigator.pop(context);
                print('取消');
              },
            ),
            CupertinoDialogAction(
              child: Text('确定'),
              onPressed: () {
                //Navigator.pop(context);
                print('确定');
              },
            ),
          ],
        );
      },
    );
  }

  //属性说明如下：
  // applicationIcon：应用程序的图标。
  // applicationName：应用程序名称。
  // applicationVersion：应用程序版本。
  // applicationLegalese：著作权（copyright）的提示。
  // children：位置如上图的红蓝绿色的位置。
  // http://laomengit.com/flutter/widgets/AboutDialog.html
  void showAboutDialog2() {
    showAboutDialog(
        context: context,
        applicationIcon:
            Image.asset('images/bird.png', height: 100, width: 100),
        applicationName: '应用程序',
        applicationVersion: '1.0.0',
        applicationLegalese: 'copyrith 老孟，一枚有态度的程序员',
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
          ),
        ]);
  }

  void showAboutDialog3() {
    final TextStyle textStyle = Theme.of(context).textTheme.body1;
    final List<Widget> aboutBoxChildren = <Widget>[
      SizedBox(
        height: 24,
      ),
      RichText(
        text: TextSpan(children: <TextSpan>[
          TextSpan(
              style: textStyle,
              text: 'Flutter is Google’s UI toolkit for building beautiful, '
                  'natively compiled applications for mobile, web, and desktop '
                  'from a single codebase. Learn more about Flutter at '),
          TextSpan(
              style: textStyle.copyWith(color: Theme.of(context).accentColor),
              text: 'https://flutter.dev'),
          TextSpan(style: textStyle, text: '.'),
        ]),
      )
    ];
    var aboutListTile = AboutListTile(
      icon: FlutterLogo(),
      child: Text('About 老孟程序员'),
      applicationName: '老孟程序员',
      applicationVersion: 'V1.0.0',
      applicationIcon: FlutterLogo(),
      applicationLegalese: '专注分享Flutter相关内容',
      aboutBoxChildren: aboutBoxChildren,
      dense: false,
    );
    showDialog(
        context: context,
        builder: (BuildContext context) {
          return Scaffold();
        });
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
                child: Text('showAlertDialog'),
                onPressed: () {
                  Navigator.of(context).pop();
                  showAlertDialog();
                },
              ),
              SimpleDialogOption(
                child: Text('showCupertinoAlertDialog'),
                onPressed: () {
                  Navigator.of(context).pop();
                  showCupertinoAlertDialog();
                },
              ),
              SimpleDialogOption(
                child: Text('showAboutDialog2()'),
                onPressed: () {
                  Navigator.of(context).pop();
                  showAboutDialog2();
                },
              ),
              SimpleDialogOption(
                child: Text('showAboutDialog3()'),
                onPressed: () {
                  Navigator.of(context).pop();
                  showAboutDialog3();
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
