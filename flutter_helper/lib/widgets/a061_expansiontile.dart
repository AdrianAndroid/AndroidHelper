import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FlexiableApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print('${context.toString()}');
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
          Scaffold.of(context).openDrawer();
        },
      ),
    );
  }

  var _color = Colors.blue.withOpacity(.1);

  String _value = null;

  // buildBody() => DropdownButtonHideUnderline(child: DropdownButton());

  bool _expanded = false;

  List<bool> dataList = List.generate(20, (index) => false).toList();

  _buildExpansionPanelList() {
    return SingleChildScrollView(
      child: Container(
        child: ExpansionPanelList(
          expansionCallback: (index, isExpaned) {
            setState(() {
              dataList[index] = !isExpaned;
            });
          },
          children: dataList
              .map((value) => ExpansionPanel(
                    isExpanded: value,
                    headerBuilder: (context, isExpanded) {
                      return ListTile(
                        title: Text('老孟 $isExpanded'),
                      );
                    },
                    body: Container(
                      height: 100,
                      color: Colors.greenAccent,
                      child: Text('HelloWorl!'),
                    ),
                  ))
              .toList(),
        ),
      ),
    );
  }

  buildBody() => SingleChildScrollView(
        child: Column(
          children: [
            Row(
              children: [
                Container(
                  color: Colors.blue,
                  height: 50,
                  width: 50,
                  child: ExpandIcon(
                      isExpanded: _expanded,
                      onPressed: (value) {
                        setState(() {
                          _expanded = !_expanded;
                        });
                      }),
                ),
                Container(
                  color: Colors.blue,
                  height: 50,
                  width: 50,
                  child: ExpandIcon(
                      size: 48,
                      color: Colors.red,
                      isExpanded: _expanded,
                      onPressed: (value) {
                        setState(() {
                          _expanded = !_expanded;
                        });
                      }),
                ),
                Flexible(
                  child: Container(
                    color: Colors.red,
                    height: 50,
                  ),
                ),
                Container(
                  color: Colors.blue,
                  height: 50,
                  width: 100,
                  child: ExpandIcon(
                    disabledColor: Colors.green,
                    expandedColor: Colors.red,
                    color: Colors.yellow,
                    onPressed: (value) {
                      setState(() {
                        _expanded = !_expanded;
                      });
                    },
                    isExpanded: _expanded,
                  ),
                ),
              ],
            ),
            _buildExpansionPanelList(),
          ],
        ),
      );
}
