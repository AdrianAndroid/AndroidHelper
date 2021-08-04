import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/AnimatedIcon.html
class AnimatedListApp extends StatelessWidget {
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
  List<int> _list = [];
  final GlobalKey<AnimatedListState> _listKey = GlobalKey<AnimatedListState>();

  void _addItem() {
    final int _index = _list.length;
    _list.insert(_index, _index);
    _listKey.currentState.insertItem(_index);
  }

  void _removeItem() {
    final int _index = _list.length - 1;
    var item = _list[_index].toString();
    _listKey.currentState.removeItem(
        _index, (context, animation) => _buildItem(item, animation));
    _list.removeAt(_index);
  }

  Widget _buildItem(String _item, Animation _animation) {
    if (true)
      return _buildItem2(_item, _animation);
    else
      return SlideTransition(
        position: _animation
            .drive(
              CurveTween(curve: Curves.easeIn),
            )
            .drive(Tween<Offset>(begin: Offset(1, 1), end: Offset(0, 1))),
        child: Card(
          child: ListTile(
            title: Text(_item),
          ),
        ),
      );
  }

  Widget _buildItem2(String _item, Animation _animation) {
    return SizeTransition(
      sizeFactor: _animation,
      child: Card(
        child: ListTile(
          title: Text(_item),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("FFFFFF"),
      ),
      body: AnimatedList(
        key: _listKey,
        initialItemCount: _list.length,
        itemBuilder: (context, index, animation) {
          return _buildItem(_list[index].toString(), animation);
        },
      ),
      floatingActionButton: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          FloatingActionButton(
            onPressed: () {
              _addItem();
            },
            child: Icon(Icons.add),
          ),
          SizedBox(
            width: 60,
          ),
          FloatingActionButton(
            onPressed: () => _removeItem(),
            child: Icon(Icons.remove),
          ),
        ],
      ),
    );
  }
}
