import 'dart:io' show Platform;

import 'package:flutter/material.dart';

import 'cupertino_app.dart';
import 'material_app.dart';

void main() => runApp(Platform.isIOS ? TypeAheadCupertinoApp() : TypeAheadMaterialApp());
