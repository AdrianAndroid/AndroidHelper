import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a001_about_dialog.dart';
import 'package:flutter_helper/widgets/a002_about_list_tile.dart';
import 'package:flutter_helper/widgets/a003_absorbpointer.dart';
import 'package:flutter_helper/widgets/a004_actionchip.dart';
import 'package:flutter_helper/widgets/a005_alertdialog.dart';
import 'package:flutter_helper/widgets/a006_align.dart';
import 'package:flutter_helper/widgets/a007_aniatedbuilder.dart';

void main() {
  //runApp(MyApp());
  // runApp(AboutListTileApp());
  // runApp(AbsorbPointerApp());
  // runApp(ActionChipApp());
  // runApp(AlertDialogApp());
  // runApp(AlignApp());
  runApp(AnimatedBuilderApp());
}

// class MyApp extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       title: 'Flutter Demo',
//       theme: ThemeData(
//         primarySwatch: Colors.blue,
//         visualDensity: VisualDensity.adaptivePlatformDensity,
//       ),
//       home: DialogHomePage(title: 'Flutter Demo Home Page'),
//     );
//   }
// }
//
// class MyHomePage extends StatefulWidget {
//   MyHomePage({Key key, this.title}) : super(key: key);
//
//   final String title;
//
//   @override
//   _MyHomePageState createState() => _MyHomePageState();
// }
//
// class _MyHomePageState extends State<MyHomePage> {
//   int _counter = 0;
//
//   void _incrementCounter() {
//     setState(() {
//       showSimpleDialog();
//       _counter++;
//     });
//   }
//
//   // 显示第一个Dialog
//   void showSimpleDialog() {
//     showDialog(
//         context: context,
//         barrierDismissible: false,
//         builder: (BuildContext context) {
//           return new AlertDialog(
//             title: Text("标题"),
//             //可滑动
//             content: SingleChildScrollView(
//               child: ListBody(
//                 children: [
//                   Text("内容1"),
//                   Text("内容2"),
//                   Text("内容1"),
//                   Text("内容2"),
//                   Text("内容2"),
//                 ],
//               ),
//             ),
//             actions: [
//               FlatButton(
//                   onPressed: () {
//                     Navigator.of(context).pop();
//                   },
//                   child: Text("确定")),
//               FlatButton(
//                   onPressed: () {
//                     Navigator.of(context).pop();
//                   },
//                   child: Text("取消"))
//             ],
//           );
//         });
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(
//         title: Text(widget.title),
//       ),
//       body: Center(
//         child: Column(
//           mainAxisAlignment: MainAxisAlignment.center,
//           children: <Widget>[
//             Text(
//               'You have pushed the button this many times:',
//             ),
//             Text(
//               '$_counter',
//               style: Theme.of(context).textTheme.headline4,
//             ),
//           ],
//         ),
//       ),
//       floatingActionButton: FloatingActionButton(
//         onPressed: _incrementCounter,
//         tooltip: 'Increment',
//         child: Icon(Icons.add),
//       ), // This trailing comma makes auto-formatting nicer for build methods.
//     );
//   }
// }
