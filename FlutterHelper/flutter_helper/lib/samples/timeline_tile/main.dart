import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_fonts/google_fonts.dart';

import 'src/showcase_timeline_tile.dart';

void main() {
  runApp(TimelineTileApp());
}

class TimelineTileApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TimelineTile Showcase',
      theme: ThemeData(
        brightness: Brightness.dark,
        textTheme: GoogleFonts.nanumPenScriptTextTheme(
          Theme.of(context).textTheme,
        ).apply(bodyColor: Colors.white),
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: ShowcaseTimelineTile(),
    );
  }
}
