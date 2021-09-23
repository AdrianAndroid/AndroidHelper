import 'package:flutter/material.dart';
import './pages/Home.dart';

void main() => runApp(TodoApp());

class TodoApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter TODO',
      home: HomePage(),
    );
  }
}
