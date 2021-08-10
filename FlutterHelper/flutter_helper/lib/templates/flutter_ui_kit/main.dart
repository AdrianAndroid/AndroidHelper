import 'package:flutter/material.dart';

import 'di/dependency_injection.dart';
import 'myapp.dart';
// import 'package:flutter_uikit/di/dependency_injection.dart';
// import 'package:flutter_uikit/myapp.dart';

void main() {
  // SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]);
  Injector.configure(Flavor.MOCK);
  runApp(FlutterUiKitApp());
}
