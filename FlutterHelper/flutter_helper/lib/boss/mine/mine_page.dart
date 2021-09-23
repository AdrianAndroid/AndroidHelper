import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/mine/contact_item.dart';
import 'package:flutter_helper/boss/mine/menu_item.dart';

class MinePage extends StatefulWidget {
  @override
  _MinePageState createState() => _MinePageState();
}

class _MinePageState extends State<MinePage>
    with AutomaticKeepAliveClientMixin {
  final double _appBarHeight = 180.0;
  final String _userHead =
      'https://img.bosszhipin.com/beijin/mcs/useravatar/20171211/4d147d8bb3e2a3478e20b50ad614f4d02062e3aec7ce2519b427d24a3f300d68_s.jpg';

  @override
  bool get wantKeepAlive => true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromARGB(255, 242, 242, 245),
      body: CustomScrollView(
        slivers: [
          SliverAppBar(
            expandedHeight: _appBarHeight,
            flexibleSpace: FlexibleSpaceBar(
              collapseMode: CollapseMode.parallax,
              background: backgroundWidget(),
            ),
          ),
          SliverList(delegate: SliverChildListDelegate(delegateChildren()))
        ],
      ),
    );
  }

  Widget backgroundWidget() => Stack(
        fit: StackFit.expand,
        children: [
          DecoratedBox(
            decoration: BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment(0.0, -1.0),
                end: Alignment(0.0, -0.4),
                colors: [
                  Color(0x00000000),
                  Color(0x00000000),
                ],
              ),
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Expanded(
                flex: 3,
                child: Column(
                  children: [
                    Padding(
                      padding: EdgeInsets.only(
                        top: 30.0,
                        left: 30.0,
                        bottom: 15.0,
                      ),
                      child: Text(
                        'kimi he',
                        style: TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.bold,
                          fontSize: 35.0,
                        ),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 30.0),
                      child: Text(
                        '在职-不考虑v机会',
                        style: TextStyle(
                          color: Colors.white,
                          fontSize: 15.0,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
              Expanded(
                flex: 1,
                child: Padding(
                  padding: EdgeInsets.only(top: 40.0, right: 30.0),
                  child: CircleAvatar(
                    radius: 35.0,
                    backgroundImage: NetworkImage(_userHead),
                  ),
                ),
              )
            ],
          ),
        ],
      );

  List<Widget> delegateChildren() => [
        Container(
          color: Colors.white,
          child: Padding(
            padding: EdgeInsets.only(top: 10.0, bottom: 10.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                ContactItem(count: '696', title: '沟通过'),
                ContactItem(count: '0', title: '面试'),
                ContactItem(count: '71', title: '已投递'),
                ContactItem(count: '53', title: '感兴趣'),
              ],
            ),
          ),
        ),
        Container(
          color: Colors.white,
          margin: const EdgeInsets.only(top: 10.0),
          child: Column(
            children: [
              MenuItem(icon: Icons.face, title: "体验新版本"),
              MenuItem(icon: Icons.print, title: "我的微简历"),
              MenuItem(icon: Icons.archive, title: "附件简历"),
              MenuItem(icon: Icons.home, title: "管理求职意见"),
              MenuItem(icon: Icons.title, title: "提升简历排名"),
              MenuItem(icon: Icons.chat, title: "牛人问答"),
              MenuItem(icon: Icons.assessment, title: "关注公司"),
              MenuItem(icon: Icons.add_shopping_cart, title: "钱包"),
              MenuItem(icon: Icons.security, title: "隐私设置"),
            ],
          ),
        ),
      ];
}
