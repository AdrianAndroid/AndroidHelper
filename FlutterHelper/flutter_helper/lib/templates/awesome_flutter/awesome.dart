import 'package:flutter_helper/samples/flutter_neumorphic/lib/flutter_neumorphic.dart';
import 'package:flutter_helper/trip/widget/webview.dart';

class AwesomeFlutterApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: _AwsomeApp(),
    );
  }
}

class _AwsomeApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: WebView(
        url: "https://github.com/Solido/awesome-flutter#components",
        title: "Awesome-Flutter",
        hideAppBar: true,
      ),
    );
  }
}
