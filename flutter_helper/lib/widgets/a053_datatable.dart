import 'dart:math';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class DataTableApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter DEMO APP",
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: _Test(),
    );
  }
}

class _Test extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _TestState();
  }
}

class MySingleChildLayoutDelegate extends SingleChildLayoutDelegate {
  final Offset position;

  MySingleChildLayoutDelegate(this.position);

  @override
  Offset getPositionForChild(Size size, Size childSize) {
    return Offset(position.dx, position.dy);
  }

  @override
  bool shouldRelayout(covariant MySingleChildLayoutDelegate oldDelegate) {
    return oldDelegate.position != position;
  }
}

class _TestState extends State<_Test> with SingleTickerProviderStateMixin {
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  buildBody() => SingleChildScrollView(
        // child: buildChild(),
        // child: buildDataTable(),
        child: buildDataTable3(),
      );

  buildChild() => Column(
        children: [
          DataTable(
            // sortColumnIndex: 1,
            // sortAscending: true,
            columns: [
              DataColumn(label: Text('姓名')),
              DataColumn(label: Text('年龄'), numeric: true), //右边对齐
            ],
            rows: [
              DataRow(
                  cells: [DataCell(Text('老孟')), DataCell(Text('1'))],
                  onSelectChanged: (selected) {}),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('2'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('3'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('8'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('5'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('6'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('7'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('2'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('3'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('8'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('5'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('6'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('7'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('2'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('3'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('8'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('5'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('6'))]),
              DataRow(cells: [DataCell(Text('赵健')), DataCell(Text('7'))]),
            ],
          ),
        ],
      );

  List<User> data = [
    User('老孟', 1),
    User('老孟', 2, selected: true),
    User('老孟', 1),
    User('老孟', 4),
    User('老孟', 1),
    User('老孟', 5),
    User('老孟', 17),
    User('老孟', 17),
    User('老孟', 7),
    User('老孟', 9),
    User('老孟', 4),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
    User('老孟', 18),
  ];

  var _sortAscending = true;

  buildListDataRow() {
    List<DataRow> dataRows = [];
    for (int i = 0; i < data.length; i++) {
      dataRows.add(DataRow(
        selected: data[i].selected,
        onSelectChanged: (selected) {
          setState(() {
            data[i].selected = selected;
          });
        },
        cells: [
          DataCell(Text('${data[i].name}'), showEditIcon: true),
          DataCell(Text('${data[i].age}'), placeholder: true)
        ],
      ));
    }
    return dataRows;
  }

  buildDataTable() => DataTable(columns: [
        DataColumn(label: Text('姓名')),
        DataColumn(label: Text('年龄')),
      ], rows: buildListDataRow());

  buildDataTable2() => DataTable(
        sortColumnIndex: 1,
        sortAscending: _sortAscending,
        columns: [
          DataColumn(label: Text('姓名')),
          DataColumn(
              label: Text('年龄'),
              onSort: (int columnIndex, bool ascending) {
                setState(() {
                  _sortAscending = ascending;
                  if (ascending) {
                    data.sort((a, b) => a.age.compareTo(b.age));
                  } else {
                    data.sort((a, b) => b.age.compareTo(a.age));
                  }
                });
              }),
        ],
        rows: data.map((user) {
          return DataRow(cells: [
            DataCell(Text('${user.name}')),
            DataCell(Text('${user.age}')),
          ]);
        }).toList(),
      );

  buildDataTable3() {
    List<DataRow> dateRows = [];
    for (int i = 0; i < data.length; i++) {
      dateRows.add(DataRow(
        cells: [
          DataCell(Text('${data[i].name}')),
          DataCell(Text('${data[i].age}')),
          DataCell(Text('男')),
          DataCell(Text('2020')),
          DataCell(Text('10')),
        ],
      ));
    }
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: DataTable(columns: [
        DataColumn(label: Text('姓名')),
        DataColumn(
          label: Text('年龄'),
        ),
        DataColumn(
          label: Text('性别'),
        ),
        DataColumn(
          label: Text('出生年份'),
        ),
        DataColumn(
          label: Text('出生月份'),
        ),
      ], rows: dateRows),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('ZhaoJian'),
      ),
      body: buildBody(),
      // body: buildDataTable(),
    );
  }
}

class User {
  String name;
  int age;
  bool selected;

  User(this.name, this.age, {this.selected = false});
}
