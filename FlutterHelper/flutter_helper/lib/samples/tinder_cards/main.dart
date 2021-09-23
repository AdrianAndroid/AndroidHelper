import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';
import 'swipe_feed_page.dart';

void main() => runApp(TinderCardsApp());

class TinderCardsApp extends StatelessWidget with HighMixin{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Tinder cards demo',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: SwipeFeedPage(),
    );
  }

  @override
  High getHigh() => High(toStringShort(), """
  """);
}
