import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DropdownButtonFromFieldApp extends StatelessWidget {
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

  buildBody() => DropdownButtonFormField(
        hint: Text('请选择'),
        value: _value,
        items: [
          DropdownMenuItem(child: Text('语文'), value: '语文'),
          DropdownMenuItem(child: Text('数学'), value: '数学'),
          DropdownMenuItem(child: Text('英语'), value: '英语')
        ],
        selectedItemBuilder: (context) {
          return [
            OutlineButton(onPressed: () {}, child: Text('语文')),
            OutlineButton(onPressed: () {}, child: Text('数学')),
            OutlineButton(onPressed: () {}, child: Text('英语')),
          ];
        },
        onChanged: (String value) {
          setState(() {
            _value = value;
          });
        },
      );
}

class _SecondPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var stack = Stack(
      children: [
        Column(
          children: [
            Image.network(
              'https://flutter.github'
              '.io/assets-for-api-docs/assets/widgets/owl-2.jpg',
              height: 200,
            ),
            Container(
              height: 200,
              color: Colors.grey,
              alignment: Alignment.center,
              child: Text('电影介绍'),
            )
          ],
        ),
        Positioned.fill(
            child: DraggableScrollableSheet(
          expand: false,
          initialChildSize: 0.4,
          minChildSize: 0.4,
          maxChildSize: 1,
          builder: (BuildContext context, ScrollController scrollController) {
            return Container(
              color: Colors.blue[100],
              child: ListView.builder(
                  controller: scrollController,
                  itemCount: 100,
                  itemBuilder: (BuildContext context, int index) {
                    return ListTile(
                      title: Text('评评论 $index'),
                    );
                  }),
            );
          },
        ))
      ],
    );

    return Scaffold(
      appBar: AppBar(
        title: Text('SecondPage'),
      ),
      body: stack,
    );
  }
}
