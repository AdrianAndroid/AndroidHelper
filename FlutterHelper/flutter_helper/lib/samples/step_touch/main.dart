import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';
import 'package:flutter_helper/samples/step_touch/src/stepper.dart';

void main() => runApp(
      MaterialApp(
        theme: ThemeData(
          scaffoldBackgroundColor: const Color(0xFF6D72FF),
        ),
        home: StepTouchApp(),
      ),
    );

class StepTouchApp extends StatefulWidget with HighMixin{
  @override
  _StepTouchAppState createState() => _StepTouchAppState();

  @override
  High getHigh() => High(toStringShort(), """
  import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';
import 'package:flutter_helper/samples/step_touch/src/stepper.dart';

void main() => runApp(
      MaterialApp(
        theme: ThemeData(
          scaffoldBackgroundColor: const Color(0xFF6D72FF),
        ),
        home: StepTouchApp(),
      ),
    );

class StepTouchApp extends StatefulWidget with HighMixin{
  @override
  _StepTouchAppState createState() => _StepTouchAppState();

  @override
  High getHigh() => High(toStringShort(), """
      """);
}

class _StepTouchAppState extends State<StepTouchApp> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: StepperTouch(
                  initialValue: 0,
                  direction: Axis.vertical,
                  withSpring: false,
                  onChanged: (int value) => print('new value value'),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: StepperTouch(
                  initialValue: 0,
                  onChanged: (int value) => print('new value value'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

  """);
}

class _StepTouchAppState extends State<StepTouchApp> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: StepperTouch(
                  initialValue: 0,
                  direction: Axis.vertical,
                  withSpring: false,
                  onChanged: (int value) => print('new value $value'),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: StepperTouch(
                  initialValue: 0,
                  onChanged: (int value) => print('new value $value'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
