import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';


/// 1. ConstrainedBox、
///     ConstrainedBox组件约束子组件的最大宽高和最小宽高，假如一个组件宽高都是300，包裹在ConstrainedBox中，并给ConstrainedBox添加最大宽高约束
/// 2. UnconstrainedBox、
///     UnconstrainedBox组件不对子组件做任何约束，比如有一个父组件大小是200x200，子组件是UnconstrainedBox，UnconstrainedBox包裹一个300x300的组件
/// 3. SizedBox、
///     SizedBox是具有固定宽高的组件，直接指定具体的宽高，
/// 4. AspectRatio、
///     AspectRatio组件是固定宽高比的组件，如果组件的宽度固定，希望高是宽的1/2，可以用AspectRatio实现此效果，
/// 5. FractionallySizedBox、
///     当我们需要一个控件的尺寸是相对尺寸时，比如当前按钮的宽度占父组件的70%，可以使用FractionallySizedBox来实现此效果。
///     使用FractionallySizedBox包裹子控件，设置widthFactor宽度系数或者heightFactor高度系数，系数值的范围是0-1，0.7表示占父组件的70%，
/// 6. LimitedBox
///     LimitedBox组件是当不受父组件约束时限制它的尺寸，什么叫不受父组件约束？
///     就像这篇文章介绍的其他组件，它们都会对子组件约束，没有约束的父组件有ListView、Row、Column等，如果LimitedBox的父组件受到约束，此时LimitedBox将会不做任何操作，我们可以认为没有这个组件，代码如下：
/// 7. Container
/// 总结
/// 这么多约束类的容器组件，到底要使用哪一个组件呢？总结如下：
///
/// ConstrainedBox：适用于需要设置最大/小宽高，组件大小以来子组件大小，但不能超过设置的界限。
/// UnconstrainedBox：用到情况不多，当作ConstrainedBox的子组件可以“突破”ConstrainedBox的限制，超出界限的部分会被截取。
/// SizedBox：适用于固定宽高的情况，常用于当作2个组件之间间隙组件。
/// AspectRatio：适用于固定宽高比的情况。
/// FractionallySizedBox：适用于占父组件百分比的情况。
/// LimitedBox：适用于没有父组件约束的情况。
/// Container：适用于不仅有尺寸的约束，还有装饰（颜色、边框、等）、内外边距等需求的情况。
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
