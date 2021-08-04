import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Widgets2App extends StatelessWidget {
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

  var _dragData;

  _buildDraggable() {
    return Draggable(
      data: Color(0x000000FF),
      child: Container(
        height: 100,
        width: 100,
        alignment: Alignment.center,
        decoration: BoxDecoration(
          color: Colors.red,
          borderRadius: BorderRadius.circular(10),
        ),
        child: Text(
          '梦',
          style: TextStyle(color: Colors.white, fontSize: 18),
        ),
      ),
      feedback: Container(
        height: 100,
        width: 100,
        alignment: Alignment.center,
        decoration: BoxDecoration(
          color: Colors.blue,
          borderRadius: BorderRadius.circular(10),
        ),
        child: DefaultTextStyle.merge(
          child: Text('梦'),
          style: TextStyle(color: Colors.white, fontSize: 18),
        ),
      ),
      childWhenDragging: Container(
        height: 100,
        width: 100,
        alignment: Alignment.center,
        decoration: BoxDecoration(
            color: Colors.grey,
            borderRadius: BorderRadius.circular(10)),
        child: Text(
          '梦',
          style: TextStyle(color: Colors.white, fontSize: 18),
        ),
      ),
    );
  }

  buildBody() => SingleChildScrollView(
        child: Column(
          children: [
            _buildDraggable(),
            SizedBox(
              height: 100,
            ),
            DragTarget(
              builder: (BuildContext context, List<Color> candidateData,
                  List<dynamic> rejectedData) {
                print(
                    'candidateData:$candidateData,rejectedData:$rejectedData');
                return _dragData == null
                    ? Container(
                        height: 100,
                        width: 100,
                        alignment: Alignment.center,
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(10),
                          border: Border.all(color: Colors.red),
                        ),
                      )
                    : Container(
                        height: 100,
                        width: 100,
                        alignment: Alignment.center,
                        decoration: BoxDecoration(
                          color: Colors.red,
                          borderRadius: BorderRadius.circular(10),
                        ),
                        child: Text('梦',
                            style:
                                TextStyle(color: Colors.white, fontSize: 18)),
                      );
              },
              onWillAccept: (Color color) {
                print('onWillAccept:$color');
                return true;
              },
              onAccept: (Color color) {
                setState(() {
                  _dragData = color;
                });
                print('onAccept:$color');
              },
              onLeave: (color) {
                print('onLeave:$color');
              },
            ),
            SizedBox(
              height: 10,
            ),
            Draggable(
              onDragStarted: () {},
              onDragEnd: (details) {},
              onDraggableCanceled: (velocity, offset) {},
              onDragCompleted: () {},
              // axis: Axis.vertical, //拖动的方向
              child: Container(
                width: 100,
                height: 100,
                alignment: Alignment.center,
                decoration: BoxDecoration(
                  color: Colors.red,
                  borderRadius: BorderRadius.circular(10),
                ),
                child: Text(
                  '梦',
                  style: TextStyle(color: Colors.white, fontSize: 18),
                ),
              ),
              feedback: Container(
                height: 100,
                width: 100,
                alignment: Alignment.center,
                decoration: BoxDecoration(
                  color: Colors.blue,
                  borderRadius: BorderRadius.circular(10),
                ),
                child: Text(
                  '梦',
                  style: TextStyle(color: Colors.white, fontSize: 18),
                ),
              ),
              childWhenDragging: Container(
                height: 100,
                width: 100,
                alignment: Alignment.center,
                decoration: BoxDecoration(
                    color: Colors.grey,
                    borderRadius: BorderRadius.circular(10)),
                child: Text(
                  '梦',
                  style: TextStyle(color: Colors.white, fontSize: 18),
                ),
              ),
            ),
            SizedBox(
              height: 10,
            ),
            Container(
              height: 100,
              color: Colors.green,
              child: VerticalDivider(
                width: 20,
                thickness: 2,
                color: Colors.blue,
                indent: 10,
                endIndent: 30,
              ),
            ),
            SizedBox(
              height: 10,
            ),
            SizedBox(
              height: 10,
            ),
            Divider(
              height: 10,
              thickness: 5,
              color: Colors.red,
              indent: 10,
              endIndent: 10,
            ),
            SizedBox(
              height: 10,
            ),
            Dismissible(
              crossAxisEndOffset: 0.5,
              movementDuration: Duration(seconds: 3),
              dismissThresholds: {
                DismissDirection.endToStart: 0.8,
              },
              resizeDuration: Duration(seconds: 2),
              direction: DismissDirection.horizontal,
              onResize: () {
                print('onResize');
              },
              onDismissed: (direction) {
                print('onDissed:$direction');
              },
              confirmDismiss: (DismissDirection direction) async {
                return true;
              },
              key: ValueKey('key2'),
              child: Container(
                height: 80,
                color: Colors.red,
              ),
            ),
            Dismissible(
              confirmDismiss: (DismissDirection direction) async {
                return false;
              },
              key: ValueKey('key'),
              child: Container(
                height: 80,
                color: Colors.red,
              ),
            ),
          ],
        ),
      );
}
