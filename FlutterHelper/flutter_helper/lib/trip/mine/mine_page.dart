import 'package:flutter/material.dart';
import 'package:flutter_helper/trip/widget/webview.dart';

class MinePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: WebView(
        url: 'https://m.ctrip.com/webapp/myctrip/',
        hideAppBar: true,
        backForbid: true,
        statusBarColor: '4c5bca',
      ),
    );
  }
}
