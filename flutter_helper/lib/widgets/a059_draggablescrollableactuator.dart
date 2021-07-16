import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ScraaggableScrollableActuatorApp extends StatelessWidget {
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
    );
  }

  buildBody2() => SingleChildScrollView(
        child: Column(
          children: [],
        ),
      );

  buildBody() => DraggableScrollableActuator(
        child: DraggableScrollableSheet(
          builder: (BuildContext context, ScrollController scrollController) {
            return Container(
              color: Colors.blue[100],
              child: ListView.builder(
                controller: scrollController,
                itemCount: 100,
                itemBuilder: (BuildContext context, int index) {
                  return ListTile(
                    title: Text('评论 $index'),
                    onTap: () {
                      DraggableScrollableActuator.reset(context);
                    },
                  );
                },
              ),
            );
          },
        ),
      );
}
