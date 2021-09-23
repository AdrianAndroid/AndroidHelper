import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/AnimatedModalBarrier.html#animatedmodalbarrier
class AppbarApp extends StatelessWidget {
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
  var _firstChild = Container(
    key: ValueKey("1"),
    height: 300,
    width: 300,
    color: Colors.red,
  );

  var _secondChild = Container(
    key: ValueKey("2"),
    height: 100,
    width: 100,
    color: Colors.green,
  );

  var _displayChild;

  @override
  void initState() {
    _displayChild = _firstChild;
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
        title: Text('zhaojan'),
      ),
      drawer: Drawer(
        child: Column(
          children: [
            SizedBox(
              height: 100,
            ),
            Text(".....", textAlign: TextAlign.left,),
            Text(".....", textAlign: TextAlign.left,),
            Text(".....", textAlign: TextAlign.left,),
            Text(".....", textAlign: TextAlign.left,),
            Text(".....", textAlign: TextAlign.left,),
          ],
        ),
      ),
      body: Center(
        child: Column(
          children: [
            AppBar(
              leading: BackButton(),
              title: Text('老孟'),
            ),
            RaisedButton(onPressed: () {
              Navigator.push(context, MaterialPageRoute(builder: (context) {
                return Scaffold(
                  appBar: AppBar(
                    automaticallyImplyLeading: false,
                    centerTitle: true,
                    title: Text('zhaojian'),
                  ),
                );
              }));
            }),
            AppBar(
              title: Text('laomeng'),
              actions: [
                IconButton(
                    icon: Icon(Icons.menu),
                    onPressed: () {
                      Navigator.push(context,
                          MaterialPageRoute(builder: (context) {
                        return Scaffold(
                          appBar: AppBar(
                            title: Text('zj'),
                            bottom: TabBar(
                              tabs: [
                                Text('语文'),
                                Text('数学'),
                                Text('英语'),
                                Text('体育'),
                                Text('音乐'),
                              ],
                              controller: TabController(length: 5, vsync: this),
                            ),
                          ),
                        );
                      }));
                    }),
                IconButton(icon: Icon(Icons.add), onPressed: () {}),
              ],
            ),
            AppBar(
              elevation: 10,
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(20)),
              backgroundColor: Colors.red,
            ),
            AppBar(
              iconTheme: IconThemeData(size: 74),
              actionsIconTheme: IconThemeData(size: 24),
              textTheme: TextTheme(title: TextStyle(color: Colors.red)),
              title: Text('zzzjjj'),
            ),
          ],
        ),
      ),
      floatingActionButton: RaisedButton(
        onPressed: () {
          setState(() {
            if (_displayChild == _firstChild) {
              _displayChild = _secondChild;
            } else {
              _displayChild = _firstChild;
            }
          });
        },
      ),
    );
  }
}
