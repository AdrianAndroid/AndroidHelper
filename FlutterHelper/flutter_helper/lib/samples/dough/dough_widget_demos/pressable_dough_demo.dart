import 'package:flutter_helper/samples/dough/lib/dough.dart';
import 'package:flutter/material.dart';

/// This page demonstrates how to use the [PressableDough] widget.
class PressableDoughDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // Just a regular old floating action button.
    final fab = FloatingActionButton(
      onPressed: () {},
      child: Icon(Icons.fingerprint),
    );

    // Now the floating action button is smooshy!
    final doughFab = PressableDough(
      child: fab,
    );

    // Just a regular old container
    final centerContainer = Container(
      width: 100,
      height: 100,
      child: Center(
        child: Text(
          'Drag me around!',
          textAlign: TextAlign.center,
          style: Theme.of(context).accentTextTheme.bodyText1,
        ),
      ),
      decoration: BoxDecoration(
        color: Colors.blue,
        borderRadius: BorderRadius.circular(10),
      ),
    );

    // Now let's say we want to make the center container
    // squishy, but we want a different kind of squish. To do
    // that we just wrap the dough widget in another recipe!
    // Easy peasy.
    final doughCenterContainer = DoughRecipe(
      data: DoughRecipeData(
        viscosity: 3000,
        expansion: 1.025,
      ),
      child: PressableDough(
        child: centerContainer,
        onReleased: (details) {
          // This callback is raised when the user release their
          // hold on the pressable dough.
          print('I was released with ${details.delta} delta!');
        },
      ),
    );

    return Scaffold(
      appBar: AppBar(
        title: Text('Pressable Dough'),
      ),
      body: Center(
        child: doughCenterContainer,
      ),
      floatingActionButton: doughFab,
    );
  }
}
