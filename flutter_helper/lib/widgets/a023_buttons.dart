import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ButtonsApp extends StatelessWidget {
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

  var _dropValue = null;
  var _popValue = Text('学科');

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('zhaojan'),
      ),
      drawer: Drawer(
        child: Scrollbar(
            child: SingleChildScrollView(
          child: Column(
            children: [
              RaisedButton(
                textTheme: ButtonTextTheme.primary,
                textColor: Colors.black,
                disabledTextColor: Colors.grey,
                color: Colors.green,
                highlightColor: Colors.red,
                splashColor: Colors.blue,
                onHighlightChanged: (high) {
                  print('$high');
                },
                onPressed: () {},
                child: Text('Button'),
              ),
              RaisedButton(
                shape: CircleBorder(),
                textTheme: ButtonTextTheme.primary,
                textColor: Colors.black,
                disabledTextColor: Colors.grey,
                color: Colors.green,
                highlightColor: Colors.red,
                splashColor: Colors.blue,
                onHighlightChanged: (high) {
                  print('$high');
                },
                onPressed: () {},
                child: Text('Button'),
              ),
              FlatButton(
                onPressed: () {},
                child: Text('Button'),
                color: Colors.blue,
              ),
              OutlineButton(
                borderSide: BorderSide(color: Colors.blue, width: 2),
                disabledBorderColor: Colors.red,
                onPressed: () {},
                child: Text('Button'),
              ),
              DropdownButton(
                icon: Icon(Icons.add),
                iconSize: 24,
                iconDisabledColor: Colors.red,
                iconEnabledColor: Colors.red,
                selectedItemBuilder: (context) {
                  return [
                    Text(
                      '111',
                      style: TextStyle(color: Colors.red),
                    ),
                    Text(
                      '222',
                      style: TextStyle(color: Colors.red),
                    ),
                    Text(
                      '333',
                      style: TextStyle(color: Colors.red),
                    ),
                  ];
                },
                value: _dropValue,
                hint: Text('请选择科目'),
                items: [
                  DropdownMenuItem(
                    child: Text('111'),
                    value: '111',
                  ),
                  DropdownMenuItem(
                    child: Text('222'),
                    value: '222',
                  ),
                  DropdownMenuItem(
                    child: Text('333'),
                    value: '333',
                  ),
                ],
                onChanged: (value) {
                  setState(() {
                    _dropValue = value;
                    print("dropValue=$value");
                  });
                },
              ),
              RawMaterialButton(
                onPressed: () {},
                fillColor: Colors.blue,
                child: Text('Button'),
              ),
              PopupMenuButton<String>(
                  shape: RoundedRectangleBorder(
                    side: BorderSide(color: Colors.blue, width: 5),
                    borderRadius: BorderRadius.circular(10),
                  ),
                  icon: Icon(Icons.add),
                  //child: _popValue,
                  elevation: 5,
                  padding: EdgeInsets.all(5),
                  color: Colors.red,
                  tooltip: 'PopupMenuButton',
                  onSelected: (value) {
                    print("$value");
                    setState(() {
                      _popValue = Text(value);
                    });
                  },
                  onCanceled: () {
                    print("onCancel!");
                  },
                  initialValue: "数学",
                  itemBuilder: (context) {
                    return <PopupMenuEntry<String>>[
                      PopupMenuItem(child: Text('语文'), value: '语文'),
                      PopupMenuItem(child: Text('语文'), value: '语文'),
                      PopupMenuItem(child: Text('语文'), value: '语文'),
                      PopupMenuItem(child: Text('语文'), value: '语文'),
                      PopupMenuItem(child: Text('语文'), value: '语文'),
                      PopupMenuItem(child: Text('语文'), value: '语文'),
                      PopupMenuItem(child: Text('数学'), value: '数学'),
                      PopupMenuItem(child: Text('语文'), value: '语文'),
                      PopupMenuItem(child: Text('语文'), value: '语文'),
                    ];
                  }),
              IconButton(
                tooltip: '这是一个图标按钮',
                icon: Icon(Icons.person),
                onPressed: () {},
                iconSize: 30,
                color: Colors.red,
              ),
              BackButton(),
              CloseButton(),
              ButtonBar(
                alignment: MainAxisAlignment.start,
                mainAxisSize: MainAxisSize.max,
                children: [
                  RaisedButton(),
                  RaisedButton(),
                  RaisedButton(),
                  RaisedButton(),
                ],
              ),
              CupertinoButton(
                child: Text('ios 风格按钮'),
                onPressed: () {},
                color: Colors.blue,
                pressedOpacity: .5,
                borderRadius: BorderRadius.circular(40),
              ),
            ],
          ),
        )),
      ),
      body: SizedBox(),
      floatingActionButton: RaisedButton(
        onPressed: () {
          setState(() {});
        },
      ),
    );
  }
}
