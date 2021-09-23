import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class SwitchApp extends StatelessWidget {
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
    return Scaffold(
      appBar: AppBar(
        title: Text('ZHAOJIAN'),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Switch(
            value: _switchValue,
            onChanged: (value) {
              setState(() {
                _switchValue = value;
              });
            },
          ),
          Switch(
            activeColor: Colors.red,
            activeTrackColor: Colors.blue,
            inactiveTrackColor: Colors.green,
            value: _switchValue,
            onChanged: (value) {
              setState(() {
                _switchValue = value;
              });
            },
          ),
          Switch(
            activeThumbImage: AssetImage('images/beatiful_lady.jpeg'),
            inactiveThumbImage: AssetImage('images/bird.png'),
            activeColor: Colors.red,
            activeTrackColor: Colors.blue,
            inactiveTrackColor: Colors.green,
            value: _switchValue,
            onChanged: (value) {
              setState(() {
                _switchValue = value;
              });
            },
          ),
          Switch(
            inactiveThumbColor: Colors.black54,
            inactiveThumbImage: AssetImage('images/bird.png'),
            activeColor: Colors.red,
            activeTrackColor: Colors.blue,
            inactiveTrackColor: Colors.green,
            value: _switchValue,
            onChanged: (value) {
              setState(() {
                _switchValue = value;
              });
            },
          ),
          SwitchListTile(
            value: _switchValue,
            title: Text('是否允许4G下载'),
            onChanged: (value) {
              setState(() {
                _switchValue = value;
              });
            },
          ),
          CupertinoSwitch(
            value: _switchValue,
            onChanged: (value) {
              setState(() {
                _switchValue = value;
              });
            },
          ),
        ],
      ),
    );
  }
}
