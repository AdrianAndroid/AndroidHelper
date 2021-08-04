import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';

/// This is them application widgets
class AbsorbPointerApp extends StatelessWidget with HighMixin{
  AbsorbPointerApp({key}) : super(key: key) {}

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
  /// This is them application widgets
class AbsorbPointerApp extends StatelessWidget with Highlight{
  AbsorbPointerApp({key}) : super(key: key) {}

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

      """
  );
}

class MyStatelessWidget extends StatelessWidget {
  const MyStatelessWidget({key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      alignment: AlignmentDirectional.center,
      children: [
        SizedBox(
          width: 200.0,
          height: 100.0,
          child: ElevatedButton(
            onPressed: () {},
            child: null,
          ),
        ),
        SizedBox(
          width: 100.0,
          height: 200.0,
          child: AbsorbPointer(
            absorbing: true,
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                primary: Colors.blue.shade200,
              ),
              onPressed: () {},
              child: null,
            ),
          ),
        ),
      ],
    );
  }
}

  """
  );
}

class MyStatelessWidget extends StatelessWidget {
  const MyStatelessWidget({key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      alignment: AlignmentDirectional.center,
      children: [
        SizedBox(
          width: 200.0,
          height: 100.0,
          child: ElevatedButton(
            onPressed: () {},
            child: null,
          ),
        ),
        SizedBox(
          width: 100.0,
          height: 200.0,
          child: AbsorbPointer(
            absorbing: true,
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                primary: Colors.blue.shade200,
              ),
              onPressed: () {},
              child: null,
            ),
          ),
        ),
      ],
    );
  }
}
