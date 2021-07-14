import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a001_about_dialog.dart';
import 'package:flutter_helper/widgets/a002_about_list_tile.dart';
import 'package:flutter_helper/widgets/a003_absorbpointer.dart';
import 'package:flutter_helper/widgets/a004_actionchip.dart';
import 'package:flutter_helper/widgets/a005_alertdialog.dart';
import 'package:flutter_helper/widgets/a006_align.dart';
import 'package:flutter_helper/widgets/a007_aniatedbuilder.dart';
import 'package:flutter_helper/widgets/a008_animated_container.dart';
import 'package:flutter_helper/widgets/a009_animated_crossfade.dart';
import 'package:flutter_helper/widgets/a010_animate_default_textstyle.dart';
import 'package:flutter_helper/widgets/a011_animate_icon.dart';
import 'package:flutter_helper/widgets/a012_animate_list.dart';
import 'package:flutter_helper/widgets/a013_animate_modalbarrier.dart';
import 'package:flutter_helper/widgets/a014_animate_opacity.dart';
import 'package:flutter_helper/widgets/a015_animate_padding.dart';
import 'package:flutter_helper/widgets/a016_animate_physicalmodel.dart';
import 'package:flutter_helper/widgets/a017_animate_positioned.dart';
import 'package:flutter_helper/widgets/a018_animate_positioned_directional.dart';
import 'package:flutter_helper/widgets/a019_animate_size.dart';
import 'package:flutter_helper/widgets/a020_animate_switcher.dart';
import 'package:flutter_helper/widgets/a021_appbar.dart';
import 'package:flutter_helper/widgets/a023_buttons.dart';
import 'package:flutter_helper/widgets/a024_backdropfilter.dart';
import 'package:flutter_helper/widgets/a025_banner.dart';
import 'package:flutter_helper/widgets/a026_baseline.dart';
import 'package:flutter_helper/widgets/a027_border.dart';
import 'package:flutter_helper/widgets/a028_bottomappbar.dart';

void main() {
  //runApp(MyApp());
  // runApp(AboutListTileApp());
  // runApp(AbsorbPointerApp());
  // runApp(ActionChipApp());
  // runApp(AlertDialogApp());
  // runApp(AlignApp());
  // runApp(AnimatedBuilderApp());
  // runApp(AnimatedContainerApp());
  // runApp(AnimatedCrossFadeApp());
  // runApp(AnimatedDefaultTextStyleApp());
  //runApp(AnimatedIconApp());
  // runApp(AnimatedListApp());
  // runApp(AnimatedModalBarrierApp());
  // runApp(AnimatedOpacityApp());
  // runApp(AnimatedPaddingApp());
  // runApp(AnimatedPhysicalModelApp());
  // runApp(AnimatedPositionedApp());
  // runApp(AnimatedPositionedDirectionalApp());
  // runApp(AnimatedSizeApp());
  // runApp(AnimatedSwitcherApp());
  // runApp(AppbarApp());
  // runApp(ButtonsApp());
  // runApp(BackdropFilterApp());
  // runApp(BannerApp());
  // runApp(BaselineApp());
  // runApp(BorderApp());
  runApp(BottomAppBarApp());
}

// class MyApp extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {å
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
