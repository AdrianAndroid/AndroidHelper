import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/utils.dart';
import 'package:syntax_highlighter/syntax_highlighter.dart';

class HighLight extends StatefulWidget {
  final String title;
  final String text;
  final Widget outWidget;

  const HighLight(
      {Key key, this.text = _exampleCode, this.title = "TEST", this.outWidget})
      : super(key: key);

  @override
  _HighLightState createState() => _HighLightState();
}

class _HighLightState extends State<HighLight> {
  @override
  Widget build(BuildContext context) {
    final SyntaxHighlighterStyle style =
        Theme.of(context).brightness == Brightness.dark
            ? SyntaxHighlighterStyle.darkThemeStyle()
            : SyntaxHighlighterStyle.lightThemeStyle();
    return MaterialApp(
      title: 'Syntax Highlighter Example',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: Scaffold(
        floatingActionButton: CircleAvatar(
          child: TextButton(
              onPressed: () {
                showToast("跳转效果");
                Navigator.push(context, MaterialPageRoute(builder: (context) {
                  return widget.outWidget;
                }));
              },
              child: Text(
                '跳',
                style: TextStyle(
                  fontWeight: FontWeight.w900,
                  color: Colors.red,
                ),
              )),
        ),
        appBar: AppBar(
          toolbarHeight: 30,
          title: Text(widget.title),
          leading: IconButton(
              onPressed: () {
                Navigator.pop(context);
              },
              icon: Icon(Icons.arrow_back)),
        ),
        body: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: RichText(
              softWrap: true,
              text: TextSpan(
                style: TextStyle(fontFamily: 'monospace', fontSize: 10.0),
                children: [
                  DartSyntaxHighlighter(style).format(widget.text),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

const _exampleCode = """
  class HighLight extends StatefulWidget {
  @override
  _HighLightState createState() => _HighLightState();
}

class _HighLightState extends State<HighLight> {
  static const _exampleCode = ''' 
  class MyHomePage extends StatefulWidget { MyHomePage({Key key, this.title}) : super(key: key); final String title; @override _MyHomePageState createState() => _MyHomePageState();}
  ''';

  @override
  Widget build(BuildContext context) {
    final SyntaxHighlighterStyle style =
        Theme.of(context).brightness == Brightness.dark
            ? SyntaxHighlighterStyle.darkThemeStyle()
            : SyntaxHighlighterStyle.lightThemeStyle();
    return MaterialApp(
      title: 'Syntax Highlighter Example',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: Scaffold(
        appBar: AppBar(title: Text("TEST")),
        body: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: RichText(
              text: TextSpan(
                style: TextStyle(fontFamily: 'monospace', fontSize: 10.0),
                children: [
                  DartSyntaxHighlighter(style).format(_exampleCode),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

  """;
