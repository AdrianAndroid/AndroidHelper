import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';

import 'flushbar.dart';

class YourAwesomeApp extends StatelessWidget with HighMixin {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView(
        children: [
          Image.asset('readme_resources/flushbar_logo.png'),
          Image.asset('readme_resources/floating_style.png'),
          Image.asset('readme_resources/grounded_style.png'),
          Image.asset('readme_resources/flushbar_animated.gif'),
          Image.asset('readme_resources/input_bar.png'),
          Image.asset('readme_resources/complete_bar.png'),
          Image.asset('readme_resources/gradient_bar.png'),
          Image.asset('readme_resources/position_bar.png'),
          Image.asset('readme_resources/left_bar_indicator.png'),
          Image.asset('readme_resources/padding_and_radius.png'),
          Image.asset('readme_resources/icon_and_button_bar.png'),
          Image.asset('readme_resources/background_color_bar.png'),
          Image.asset('readme_resources/text_bar.png'),
          Image.asset('readme_resources/basic_bar.png'),
        ],
      ),
    );
  }

  // Flushbar flush;
  // @override
  // Widget build(BuildContext context) {
  //   return Container(
  //     child: Center(
  //       child: MaterialButton(
  //         onPressed: () {
  //           flush = Flushbar<bool>(
  //             title: "Hey Ninja",
  //             message: "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
  //             icon: Icon(
  //               Icons.info_outline,
  //               color: Colors.blue,),
  //             mainButton: FlatButton(
  //               onPressed: () {
  //                 flush.dismiss(true); // result = true
  //               },
  //               child: Text(
  //                 "ADD",
  //                 style: TextStyle(color: Colors.amber),
  //               ),
  //             ),) // <bool> is the type of the result passed to dismiss() and collected by show().then((result){})
  //             ..show(context).then((result) {
  //               setState(() { // setState() is optional here
  //                 _wasButtonClicked = result;
  //               });
  //             });
  //         },
  //       ),
  //     ),
  //   );
  // }

  // @override
  // Widget build(BuildContext context) {
  //   return MaterialApp(
  //     title: 'YourAwesomeApp',
  //     home: Scaffold(
  //       appBar: AppBar(title: Text('flushbar'),),
  //       body: Container(
  //         child: Center(
  //           child: MaterialButton(
  //             onPressed: () {
  //               Flushbar(
  //                 title: "Hey Ninja",
  //                 message:
  //                     "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
  //                 duration: Duration(seconds: 3),
  //               )..show(context);
  //             },
  //           ),
  //         ),
  //       ),
  //     ),
  //   );
  // }

  @override
  High getHigh() => High(toStringShort(), '');
}
