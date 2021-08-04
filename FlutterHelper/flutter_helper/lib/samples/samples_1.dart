import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';

class GridViewCount extends StatelessWidget with HighMixin {
  @override
  Widget build(BuildContext context) => GridView.count(
        crossAxisCount: 2,
        children: List.generate(
            100,
            (index) => Text(
                  '$index',
                  style: Theme.of(context).textTheme.headline5,
                )),
      );

  @override
  High getHigh() => High(toStringShort(), """
  @override
  Widget build(BuildContext context) => GridView.count(
        crossAxisCount: 2,
        children: List.generate(
            100,
            (index) => Text(
                  'index',
                  style: Theme.of(context).textTheme.headline5,
                )),
      );
  """);
}

class ListViewHorizontal extends StatelessWidget with HighMixin{
  @override
  Widget build(BuildContext context) => ListView(
        scrollDirection: Axis.horizontal,
        children: <Widget>[
          Container(
            width: 160.0,
            color: Colors.red,
          ),
          Container(
            width: 160.0,
            color: Colors.blue,
          ),
          Container(
            width: 160.0,
            color: Colors.green,
          ),
          Container(
            width: 160.0,
            color: Colors.yellow,
          ),
          Container(
            width: 160.0,
            color: Colors.orange,
          ),
        ],
      );

  @override
  High getHigh() => High(toStringShort(), """
  
  """);
}
