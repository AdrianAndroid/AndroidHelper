import 'package:flutter/cupertino.dart';

abstract class BaseApp extends StatelessWidget {
  final String name;
  final String imageName;

  const BaseApp({Key key, this.name, this.imageName}) : super(key: key);

  BaseApp.withNames(this.name, this.imageName) {
    print('$name - $imageName');
  }
}
