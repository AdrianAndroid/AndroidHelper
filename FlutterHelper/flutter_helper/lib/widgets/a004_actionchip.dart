import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';

/// http://laomengit.com/flutter/widgets/Chip.html#actionchip
/// This is them application widgets
class ActionChipApp extends StatelessWidget with HighMixin{
  ActionChipApp({key}) : super(key: key) {}

  @override
  Widget build(BuildContext context) {
    String _title = 'Flutter Code Sample';
    return MaterialApp(
      title: _title,
      home: Scaffold(
        appBar: AppBar(
          title: Text(_title),
        ),
        body: const Center(
          child: MyStatelessWidget(),
        ),
      ),
    );
  }

  @override
  High getHigh() => High(toStringShort(),
      """
      class ActionChipApp extends StatelessWidget with Highlight{
  ActionChipApp({key}) : super(key: key) {}

  @override
  Widget build(BuildContext context) {
    String _title = 'Flutter Code Sample';
    return MaterialApp(
      title: _title,
      home: Scaffold(
        appBar: AppBar(
          title: Text(_title),
        ),
        body: const Center(
          child: MyStatelessWidget(),
        ),
      ),
    );
  }

  @override
  High getHigh() => High(toStringShort(), 
      """
          """);
}

class MyStatelessWidget extends StatelessWidget {
  const MyStatelessWidget({key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scrollbar(
        child: SingleChildScrollView(
      child: Column(
        children: [
          RawChip(
            label: Text('老孟'),
          ),
          RawChip(
            label: Text('老孟禁用'),
            isEnabled: false,
          ),
          RawChip(
            avatar: CircleAvatar(
              child: Text('孟'),
            ),
            label: Text('老孟'),
          ),
          RawChip(
            label: Text('老孟'),
            labelStyle: TextStyle(color: Colors.blue),
            labelPadding: EdgeInsets.symmetric(horizontal: 100 /*左右间距*/),
          ),
          RawChip(
            label: Text('老孟'),
            onDeleted: () {
              print('onDeleted');
            },
            deleteIcon: Icon(Icons.delete),
            deleteIconColor: Colors.red,
            deleteButtonTooltipMessage: '删除',
          ),
          RawChip(
            label: Text('老孟'),
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
            backgroundColor: Colors.blue,
            padding: EdgeInsets.symmetric(vertical: 10),
          ),
          RawChip(
            label: Text('老孟'),
            elevation: 8,
            shadowColor: Colors.blue,
          ),
//bool _selected = false;
// RawChip(
//   label: Text('老孟'),
//   selected: _selected,
//   onSelected: (v){
//     setState(() {
//       _selected = v;
//     });
//   },
//   selectedColor: Colors.blue,
//   selectedShadowColor: Colors.red,
// )
//
          RawChip(
            label: Text('老孟'),
            selected: true,
            showCheckmark: true,
            checkmarkColor: Colors.red,
          ),
          RawChip(
            label: Text('老孟'),
            onPressed: () {
              print('onPressed');
            },
            pressElevation: 12,
          ),
          Wrap(
            spacing: 15,
            children: [
              ChoiceChip(label: Text('老孟 0'), selected: false),
              ChoiceChip(label: Text('老孟 1'), selected: false),
              ChoiceChip(label: Text('老孟 2'), selected: false),
              ChoiceChip(label: Text('老孟 3'), selected: false),
              ChoiceChip(label: Text('老孟 4'), selected: false),
              ChoiceChip(label: Text('老孟 5'), selected: false),
              ChoiceChip(label: Text('老孟 6'), selected: false),
              ChoiceChip(label: Text('老孟 7'), selected: false),
            ],
          ),
          Column(
            children: [
              Wrap(
                children: [
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                ],
              ),
              Text('选中：1'),
            ],
          ),
          ActionChip(
              avatar: CircleAvatar(
                backgroundColor: Colors.grey.shade800,
                child: Text('孟'),
              ),
              label: Text('老孟'),
              onPressed: () {
                print("onPressed");
              })
        ],
      ),
    ));
  }
}

  """);
}

class MyStatelessWidget extends StatelessWidget {
  const MyStatelessWidget({key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scrollbar(
        child: SingleChildScrollView(
      child: Column(
        children: [
          RawChip(
            label: Text('老孟'),
          ),
          RawChip(
            label: Text('老孟禁用'),
            isEnabled: false,
          ),
          RawChip(
            avatar: CircleAvatar(
              child: Text('孟'),
            ),
            label: Text('老孟'),
          ),
          RawChip(
            label: Text('老孟'),
            labelStyle: TextStyle(color: Colors.blue),
            labelPadding: EdgeInsets.symmetric(horizontal: 100 /*左右间距*/),
          ),
          RawChip(
            label: Text('老孟'),
            onDeleted: () {
              print('onDeleted');
            },
            deleteIcon: Icon(Icons.delete),
            deleteIconColor: Colors.red,
            deleteButtonTooltipMessage: '删除',
          ),
          RawChip(
            label: Text('老孟'),
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
            backgroundColor: Colors.blue,
            padding: EdgeInsets.symmetric(vertical: 10),
          ),
          RawChip(
            label: Text('老孟'),
            elevation: 8,
            shadowColor: Colors.blue,
          ),
//bool _selected = false;
// RawChip(
//   label: Text('老孟'),
//   selected: _selected,
//   onSelected: (v){
//     setState(() {
//       _selected = v;
//     });
//   },
//   selectedColor: Colors.blue,
//   selectedShadowColor: Colors.red,
// )
//
          RawChip(
            label: Text('老孟'),
            selected: true,
            showCheckmark: true,
            checkmarkColor: Colors.red,
          ),
          RawChip(
            label: Text('老孟'),
            onPressed: () {
              print('onPressed');
            },
            pressElevation: 12,
          ),
          Wrap(
            spacing: 15,
            children: [
              ChoiceChip(label: Text('老孟 0'), selected: false),
              ChoiceChip(label: Text('老孟 1'), selected: false),
              ChoiceChip(label: Text('老孟 2'), selected: false),
              ChoiceChip(label: Text('老孟 3'), selected: false),
              ChoiceChip(label: Text('老孟 4'), selected: false),
              ChoiceChip(label: Text('老孟 5'), selected: false),
              ChoiceChip(label: Text('老孟 6'), selected: false),
              ChoiceChip(label: Text('老孟 7'), selected: false),
            ],
          ),
          Column(
            children: [
              Wrap(
                children: [
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                  FilterChip(label: Text("zj 1"), onSelected: (v) {}),
                ],
              ),
              Text('选中：1'),
            ],
          ),
          ActionChip(
              avatar: CircleAvatar(
                backgroundColor: Colors.grey.shade800,
                child: Text('孟'),
              ),
              label: Text('老孟'),
              onPressed: () {
                print("onPressed");
              })
        ],
      ),
    ));
  }
}
