
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';

class CupertinoDatePickeruApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter DEMO APP",
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: _HomePage(),
    );
  }
}

void test() {
  MaterialApp(
    routes: {
      'container': (context) => _HomePage(),
      'fitted': (context) => _HomePage(),
      'icon': (context) => _HomePage(),
    },
    initialRoute: '/',
    home: Scaffold(
      appBar: AppBar(
        title: Text('LLLL'),
      ),
    ),
    onGenerateRoute: (RouteSettings routeSettings) {
      print('onGenerateRouter:$routeSettings');
      if (routeSettings.name == 'icon') {
        return MaterialPageRoute(builder: (context) {
          return _HomePage();
        });
      }
      return null;
    },
    onUnknownRoute: (RouteSettings routeSettings) {
      print('onUnkonwRoute$routeSettings');
      return MaterialPageRoute(builder: (context) {
        return HomePage();
      });
    },
  );
}
//cupertinofullscreendialogtransition
DateTime _selectedDate = DateTime.now();

class _HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('老慢'),
        ),
        body: Scrollbar(
          child: SingleChildScrollView(
            child: Column(
              children: [
                // container(
                //     child: CupertinoDatePicker(
                //   onDateTimeChanged: (date) {},
                //   initialDateTime: DateTime.now(),
                // )),
                // YearPicker(
                //   selectedDate: _selectedDate,
                //   onChanged: (date) {
                //     print("$date");
                //     // setState(() {
                //     //   _selectedDate = date;
                //     // });
                //   },
                //   firstDate: DateTime(2000, 1),
                //   lastDate: DateTime(2020, 12),
                // ),
                MonthPicker(
                  selectedDate: DateTime.now(),
                  onChanged: (date) {
                    print("$date");
                    // setState(() {
                    //   _selectedDate = date;
                    // });
                  },
                  firstDate: DateTime(2020, 1),
                  lastDate: DateTime(2020, 12),
                ),

                // DayPicker(
                //   selectedDate: _selectedDate,
                //   currentDate: DateTime.now(),
                //   onChanged: (date) {
                //     // setState(() {
                //     //   _selectedDate = date;
                //     // });
                //   },
                //   firstDate: DateTime(2020, 5, 1),
                //   lastDate: DateTime(2020, 5, 31),
                //   displayedMonth: DateTime(2020, 5),
                // ),
              ],
            ),
          ),
        ),
        floatingActionButton: RaisedButton(onPressed: () async {
          var result = await showDatePicker(
              context: context,
              initialDate: DateTime.now(),
              firstDate: DateTime(2020),
              lastDate: DateTime(2030),
              builder: (context, child) {
                return Theme(
                  data: ThemeData.dark(),
                  child: child,
                );
              });
          print("$result");
        }));
  }
}

class _Test extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _TestState();
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

  var _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('zhaojan'),
      ),
      body: CustomScrollView(
        physics: NeverScrollableScrollPhysics(),
      ),
      bottomNavigationBar: BottomNavigationBar(
        // type: BottomNavigationBarType.fixed,
        type: BottomNavigationBarType.shifting,
        selectedItemColor: Theme.of(context).primaryColor,
        unselectedItemColor: Colors.black,
        currentIndex: _currentIndex,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            title: Text('首页'),
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.book),
            title: Text('书籍'),
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.perm_identity),
            title: Text('我的'),
          ),
        ],
      ),
    );
  }
}
