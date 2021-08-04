import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class SliderApp extends StatelessWidget {
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
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  double _sliderValue = 0;
  double _sliderValue2 = 0;
  double _sliderValue3 = 0;
  RangeValues _rangeValues = RangeValues(0, 1);
  double _sliderValue4 = 0;
  double _sliderValue5 = 0;
  String _value = '语文';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('APPBAR'),
      ),
      body: Scrollbar(
        child: Column(
          children: [
            Slider(
              value: _sliderValue,
              onChanged: (v) {
                setState(() {
                  _sliderValue = v;
                });
              },
            ),
            Slider(
              min: 0,
              max: 100,
              value: _sliderValue2,
              onChanged: (v) {
                setState(() {
                  _sliderValue2 = v;
                });
              },
            ),
            Slider(
              label: '$_sliderValue3',
              min: 0,
              max: 100,
              divisions: 5,
              value: _sliderValue3,
              onChanged: (v) {
                setState(() {
                  _sliderValue3 = v;
                });
              },
            ),
            Slider(
              activeColor: Colors.red,
              inactiveColor: Colors.blue,
              label: '$_sliderValue3',
              min: 0,
              max: 100,
              divisions: 5,
              value: _sliderValue3,
              onChanged: (v) {
                setState(() {
                  _sliderValue3 = v;
                });
              },
            ),
            RangeSlider(
              values: _rangeValues,
              onChanged: (v) {
                setState(() {
                  _rangeValues = v;
                });
              },
            ),
            CupertinoSlider(
              value: _sliderValue4,
              onChanged: (v) {
                setState(() {
                  _sliderValue4 = v;
                });
              },
            ),
            Slider.adaptive(
              value: _sliderValue5,
              onChanged: (v) {
                setState(() {
                  _sliderValue5 = v;
                });
              },
            ),
            CupertinoSlidingSegmentedControl(
              children: {
                '语文': Container(
                  child: Text('语文'),
                  padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
                ),
                '数学': Container(
                  child: Text('数学'),
                  padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
                ),
                '体育': Container(
                  child: Text('体育'),
                  padding: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
                ),
              },
              groupValue: _value,
              onValueChanged: (value) {
                setState(() {
                  _value = value;
                });
              },
            ),
          ],
        ),
      ),
    );
  }
}
