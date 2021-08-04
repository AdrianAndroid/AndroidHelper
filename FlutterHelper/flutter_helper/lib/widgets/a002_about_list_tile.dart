/// Flutter code sample for AboutListTile

// This sample shows two ways to open [AboutDialog]. The first one
// uses an [AboutListTile], and the second uses the [showAboutDialog] function.

import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/HighIconButton.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';
import 'package:flutter_helper/hightlight/syntax_highlight.dart';

// void main() => runApp(const MyApp());

/// This is the main application widget.
class AboutListTileApp extends StatelessWidget with HighMixin{
  const AboutListTileApp({key}) : super(key: key);

  static const String _title = 'Flutter Code Sample';

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: _title,
      home: MyStatelessWidget(),
    );
  }

  @override
  High getHigh() => High(toStringShort(),
      """
import 'package:flutter/material.dart';
import 'package:flutter_helper/hightlight/HighIconButton.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';
import 'package:flutter_helper/hightlight/syntax_highlight.dart';

// void main() => runApp(const MyApp());

/// This is the main application widget.
class AboutListTileApp extends StatelessWidget with Highlight{
  const AboutListTileApp({key}) : super(key: key);

  static const String _title = 'Flutter Code Sample';

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: _title,
      home: MyStatelessWidget(),
    );
  }

  @override
  High getHigh() => High(toStringShort(), 
      """

          """
  );
}

/// This is the stateless widget that the main application instantiates.
class MyStatelessWidget extends StatelessWidget {
  const MyStatelessWidget({key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final ThemeData theme = Theme.of(context);
    final TextStyle textStyle = theme.textTheme.bodyText2;
    final List<Widget> aboutBoxChildren = <Widget>[
      const SizedBox(height: 24),
      RichText(
        text: TextSpan(
          children: <TextSpan>[
            TextSpan(
                style: textStyle,
                text: "Flutter is Google's UI toolkit for building beautiful, "
                    'natively compiled applications for mobile, web, and desktop '
                    'from a single codebase. Learn more about Flutter at '),
            TextSpan(
                style: textStyle.copyWith(color: theme.colorScheme.primary),
                text: 'https://flutter.dev'),
            TextSpan(style: textStyle, text: '.'),
          ],
        ),
      ),
    ];

    return Scaffold(
      appBar: AppBar(
        title: const Text('Show About Example'),
        actions: [
          HightIconButton(),
        ],
      ),
      drawer: Drawer(
        child: SingleChildScrollView(
          child: SafeArea(
            child: AboutListTile(
              icon: const Icon(Icons.info),
              applicationIcon: const FlutterLogo(),
              applicationName: 'Show About Example',
              applicationVersion: 'August 2019',
              applicationLegalese: '\u{a9} 2014 The Flutter Authors',
              aboutBoxChildren: aboutBoxChildren,
            ),
          ),
        ),
      ),
      body: Center(
        child: ElevatedButton(
          child: const Text('Show About Example'),
          onPressed: () {
            showAboutDialog(
              context: context,
              applicationIcon: const FlutterLogo(),
              applicationName: 'Show About Example',
              applicationVersion: 'August 2019',
              applicationLegalese: '\u{a9} 2014 The Flutter Authors',
              children: aboutBoxChildren,
            );
          },
        ),
      ),
    );
  }
}
      """
  );
}

/// This is the stateless widget that the main application instantiates.
class MyStatelessWidget extends StatelessWidget {
  const MyStatelessWidget({key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final ThemeData theme = Theme.of(context);
    final TextStyle textStyle = theme.textTheme.bodyText2;
    final List<Widget> aboutBoxChildren = <Widget>[
      const SizedBox(height: 24),
      RichText(
        text: TextSpan(
          children: <TextSpan>[
            TextSpan(
                style: textStyle,
                text: "Flutter is Google's UI toolkit for building beautiful, "
                    'natively compiled applications for mobile, web, and desktop '
                    'from a single codebase. Learn more about Flutter at '),
            TextSpan(
                style: textStyle.copyWith(color: theme.colorScheme.primary),
                text: 'https://flutter.dev'),
            TextSpan(style: textStyle, text: '.'),
          ],
        ),
      ),
    ];

    return Scaffold(
      appBar: AppBar(
        title: const Text('Show About Example'),
        actions: [
          HightIconButton(),
        ],
      ),
      drawer: Drawer(
        child: SingleChildScrollView(
          child: SafeArea(
            child: AboutListTile(
              icon: const Icon(Icons.info),
              applicationIcon: const FlutterLogo(),
              applicationName: 'Show About Example',
              applicationVersion: 'August 2019',
              applicationLegalese: '\u{a9} 2014 The Flutter Authors',
              aboutBoxChildren: aboutBoxChildren,
            ),
          ),
        ),
      ),
      body: Center(
        child: ElevatedButton(
          child: const Text('Show About Example'),
          onPressed: () {
            showAboutDialog(
              context: context,
              applicationIcon: const FlutterLogo(),
              applicationName: 'Show About Example',
              applicationVersion: 'August 2019',
              applicationLegalese: '\u{a9} 2014 The Flutter Authors',
              children: aboutBoxChildren,
            );
          },
        ),
      ),
    );
  }
}
