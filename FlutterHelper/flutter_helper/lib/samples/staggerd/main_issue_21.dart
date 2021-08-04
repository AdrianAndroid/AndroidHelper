import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'StaggeredGridView Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyScreen(),
    );
  }
}

class MyScreen extends StatefulWidget {
  @override
  _MyScreenState createState() => _MyScreenState();
}

class _MyScreenState extends State<MyScreen> {
  int _count = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StaggeredTest(_count, _count),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          setState(() {
            _count++;
          });
        },
        child: const Icon(Icons.add),
      ),
    );
  }
}

class GridTest extends StatelessWidget {
  const GridTest(this.count, this.value);
  final int count;
  final int value;

  @override
  Widget build(BuildContext context) {
    return GridView.builder(
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 3,
        crossAxisSpacing: 2,
        mainAxisSpacing: 2,
      ),
      itemCount: count,
      itemBuilder: (context, index) {
        return Container(
          color: Colors.blue,
          child: Text('$value'),
        );
      },
    );
  }
}

class StaggeredTest extends StatelessWidget {
  const StaggeredTest(this.count, this.value);
  final int count;
  final int value;

  @override
  Widget build(BuildContext context) {
    return StaggeredGridView.countBuilder(
      itemCount: count,
      crossAxisCount: 3,
      crossAxisSpacing: 2,
      mainAxisSpacing: 2,
      addAutomaticKeepAlives: false,
      staggeredTileBuilder: (index) => const StaggeredTile.extent(1, 30),
      itemBuilder: (context, index) {
        return Container(
          color: Colors.green,
          child: Text('$value'),
        );
      },
    );
  }
}
