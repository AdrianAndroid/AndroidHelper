import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class TextFieldApp extends StatelessWidget {
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

class TextFieldPage extends StatefulWidget {
  String title;

  TextFieldPage({Key key, this.title}) : super(key: key);

  @override
  _TextFieldPageState createState() => _TextFieldPageState();
}

class _TextFieldPageState extends State<TextFieldPage> {
  var _textFieldValue = '';
  TextEditingController _controller;

  @override
  void initState() {
    _controller = TextEditingController()
      ..addListener(() {
        //获取输入框的内容，变为大写
        _controller.text = _controller.text.toUpperCase();
      });
    super.initState();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  void test() {
    var _focusNode = FocusNode();
    _focusNode.unfocus();
    // FocusScope.of(context).requestFocus(_focusNode);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            TextField(
              maxLength: 100,
              buildCounter: (
                BuildContext context, {
                int currentLength,
                int maxLength,
                bool isFocused,
              }) {
                return Text(
                  '$currentLength/$maxLength',
                );
              },
            ),
            TextField(
              decoration: InputDecoration(hintText: 'hello'),
              onChanged: (value) {
                print('onChanged:$value');
              },
              onEditingComplete: () {
                print('onEditingComplete');
              },
              onTap: () {
                print('onTap');
              },
              textInputAction: TextInputAction.go,
            ),
            TextField(
              inputFormatters: [
                WhitelistingTextInputFormatter(RegExp("[a-zA-Z]")),
              ],
              decoration: InputDecoration(hintText: 'go'),
              textInputAction: TextInputAction.go,
            ),
            TextField(
              obscureText: true,
              decoration: InputDecoration(hintText: 'go'),
              textInputAction: TextInputAction.go,
            ),
            TextField(
              decoration: InputDecoration(hintText: 'done'),
              textInputAction: TextInputAction.done,
            ),
            TextField(
              keyboardType: TextInputType.phone,
              controller: _controller,
            ),
            Container(
              height: 60,
              width: 250,
              child: TextField(
                decoration: InputDecoration(
                  fillColor: Color(0x30cccccc),
                  filled: true,
                  enabledBorder: OutlineInputBorder(
                    borderSide: BorderSide(color: Color(0x00FF0000)),
                    borderRadius: BorderRadius.all(Radius.circular(100)),
                  ),
                  hintText: 'QQ👌/手机号/邮箱',
                  focusedBorder: OutlineInputBorder(
                    borderSide: BorderSide(color: Color(0x00000000)),
                    borderRadius: BorderRadius.all(Radius.circular(100)),
                  ),
                ),
              ),
            ),
            TextField(
              onChanged: (value) {
                print(value);
                setState(() {
                  _textFieldValue = value;
                });
              },
              decoration:
                  InputDecoration(counterText: '${_textFieldValue.length}/32'),
            ),
            TextField(
              decoration: InputDecoration(
                  prefixIcon: Icon(Icons.person),
                  suffixIcon: Icon(Icons.person)),
            ),
            TextField(
              decoration: InputDecoration(
                errorText: '用户名输入错误',
                errorStyle: TextStyle(fontSize: 12),
                errorMaxLines: 1,
                errorBorder: OutlineInputBorder(
                    borderSide: BorderSide(color: Colors.red)),
              ),
            ),
            TextField(),
            TextField(
              decoration: InputDecoration(
                icon: Icon(Icons.person),
              ),
            ),
            TextField(
              decoration: InputDecoration(
                labelText: '姓名:',
                labelStyle: TextStyle(color: Colors.red),
              ),
            ),
            TextField(
              decoration: InputDecoration(
                helperText: '用户长度为6-10个字符',
                helperStyle: TextStyle(color: Colors.blue),
                helperMaxLines: 1,
              ),
            ),
            TextField(
              decoration: InputDecoration(
                hintText: '请输入用户名',
                hintStyle: TextStyle(color: Colors.grey),
                hintMaxLines: 1,
              ),
            ),
          ],
        ),
      ),
    );
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
        currentIndex: 0,
        backgroundColor: Colors.blue,
        activeColor: Colors.red,
      ),
      tabBuilder: (BuildContext context, int index) {
        return CupertinoTabView(
          defaultTitle: '老孟',
          builder: (context) {
            var page = TextFieldPage();
            page.title = '$index';
            return page;
          },
        );
      },
    );
  }
}
