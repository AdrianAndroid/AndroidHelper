import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';

import 'routes.dart';

void main() => runApp(StaggerApp());

class StaggerApp extends StatelessWidget with HighMixin{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'StaggeredGridView Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      routes: routes,
    );
  }

  @override
  High getHigh() => High(toStringShort(), "text");
}
