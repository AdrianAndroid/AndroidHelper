import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/syntax_highlight.dart';

class HightIconButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) => IconButton(
        onPressed: () {
          Navigator.push(context, MaterialPageRoute(builder: (context) {
            return HighLight();
          }));
        },
        icon: Icon(Icons.code),
        tooltip: "显示源代码",
      );
}
