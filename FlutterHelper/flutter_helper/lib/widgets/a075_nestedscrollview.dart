import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///http://laomengit.com/flutter/widgets/NestedScrollView.html#%E6%BB%9A%E5%8A%A8%E9%9A%90%E8%97%8Fappbar
class NestedScrollViewApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    print('${context.toString()}');
    return MaterialApp(
      title: "Flutter DEMO APP",
      locale: Locale('zh'),
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: HeroDemo(),
    );
  }
}

class HeroDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _HeroDemo();
}

class _HeroDemo extends State<HeroDemo> with SingleTickerProviderStateMixin {
  ScrollController _scrollController;
  TabController _tabController;
  var tabs = <Widget>[
    Tab(text: '资讯'),
    Tab(text: '技术'),
  ];

  @override
  void initState() {
    _scrollController = ScrollController();

//监听滚动位置
    _scrollController.addListener(() {
      print('${_scrollController.position}');
    });
    //滚动到指定位置
    // _scrollController.animateTo(20.0);
    _tabController = TabController(vsync: this, length: tabs.length);

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: buildBody(),
    );
  }

  buildBody() => NestedScrollView(
        headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled) {
          return <Widget>[
            SliverAppBar(
              expandedHeight: 230.0,
              pinned: true,
              flexibleSpace: Padding(
                padding: EdgeInsets.symmetric(vertical: 8),
                child: PageView(),
              ),
            ),
            SliverPersistentHeader(
              pinned: true,
              delegate: StickyTabBarDelegate(
                child: TabBar(
                  labelColor: Colors.black,
                  controller: this._tabController,
                  tabs: tabs,
                ),
              ),
            ),
          ];
        },
        body: TabBarView(
          controller: this._tabController,
          children: <Widget>[
            RefreshIndicator(
              onRefresh: () async {
                print(('onRefresh'));
              },
              child: _buildTabNewsList(),
            ),
            _buildTabNewsList(),
          ],
        ),
      );

  _buildTabNewsList() {
    return ListView.builder(
      itemBuilder: (BuildContext context, int index) {
        return Container(
          height: 80,
          color: Colors.primaries[index % Colors.primaries.length],
          alignment: Alignment.center,
          child: Text(
            '$index',
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
        );
      },
      itemCount: 20,
    );
  }
}

class StickyTabBarDelegate extends SliverPersistentHeaderDelegate {
  final TabBar child;

  StickyTabBarDelegate({@required this.child});

  @override
  Widget build(
      BuildContext context, double shrinkOffset, bool overlapsContent) {
    return Container(
      color: Theme.of(context).backgroundColor,
      child: this.child,
    );
  }

  @override
  double get maxExtent => this.child.preferredSize.height;

  @override
  double get minExtent => this.child.preferredSize.height;

  @override
  bool shouldRebuild(SliverPersistentHeaderDelegate oldDelegate) {
    return true;
  }
}
