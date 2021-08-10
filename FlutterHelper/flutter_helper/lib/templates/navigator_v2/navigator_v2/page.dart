import 'package:flutter/material.dart';
import '../data/veggie.dart';
import '../screen/details.dart';

class VeggieDetailsPage extends Page {
  final Veggie veggie;

  VeggieDetailsPage({
    this.veggie,
  }) : super(key: ValueKey(veggie));

  Route createRoute(BuildContext context) {
    return MaterialPageRoute(
      settings: this,
      builder: (BuildContext context) {
        return VeggieDetailsScreen(veggie: veggie);
      },
    );
  }
}