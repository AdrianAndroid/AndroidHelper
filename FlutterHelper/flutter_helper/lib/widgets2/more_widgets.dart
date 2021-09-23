import 'package:flutter_helper/samples/flutter_neumorphic/lib/flutter_neumorphic.dart';

import 'functions.dart';

class MoreWidgets {

  /*
  *  生成常用的AppBar
  */
  static Widget buildAppBar(BuildContext context, String text,
      {double fontSize: 18.0,
        double height: 50.0,
        double elevation: 0.5,
        Widget leading,
        bool centerTitle: false,
        List<Widget> actions,
        OnItemDoubleClick onItemDoubleClick}) {
    return PreferredSize(
        child: GestureDetector(
            onDoubleTap: () {
              if (null != onItemDoubleClick) {
                onItemDoubleClick(null);
              }
            },
            child: AppBar(
              elevation: elevation, //阴影
              centerTitle: centerTitle,
              title: Text(text, style: TextStyle(fontSize: fontSize)),
              leading: leading,
              actions: actions,
            )),
        preferredSize: Size.fromHeight(height));
  }

}