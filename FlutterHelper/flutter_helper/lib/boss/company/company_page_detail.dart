import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/company/company.dart';
import 'package:flutter_helper/boss/company/company_detail.dart';
import 'package:flutter_helper/boss/company/scroll_img_item.dart';
import 'package:flutter_helper/boss/company/welfare_item.dart';

import 'gallery_page.dart';

class CompanyDetailPage extends StatefulWidget {
  final Company company;
  final String heroLogo;

  const CompanyDetailPage({Key key, this.company, this.heroLogo})
      : super(key: key);

  @override
  State<StatefulWidget> createState() => _CompanyDetailPageState();
}

class _CompanyDetailPageState extends State<CompanyDetailPage>
    with SingleTickerProviderStateMixin {
  ScrollController _scrollController;
  bool _isShow = false;

  Future<CompanyDetail> _fetchCompan() async {
    return CompanyDetail(
      id: "id",
      inc: "无锡红光标牌有限公司是一家集研发、生产、销售和服务于一体的专业标牌生产厂家。注册资金500万人民币。主要生产塑料基材、软塑透明树脂、金属、模内复合等标牌产品，洗衣机顶盖板总成，平衡板，以及塑印、彩印、顶盖板、吸音垫等产品。公司位于长江三角洲经济快速增长、风景秀丽的太湖之畔——无锡。 公司自1984年成立至今，已经过了3次跨越式的发展。2004年至今公司投入5000多万元资金建设新的生产基地，目前已竣工并投入生产，占地面积达40000m2，厂房面积近15000m2。公司2004年的年产值达4350多万元，并且每年以平均30%的速度快速增长。目前，本公司的产品已具备国际及国内多项质量认证证书，并为知名家用电器企业：小天鹅电器有限公司、三星电子有限公司、海尔集团、惠尔普等配套生产各类标牌。可以说客户是我们的老师，和他们合作使我们得到很多的学习机会来提高自身的技术水平和管理水平，是我们生产和发展的动力。 公司本着“千方百计生产出满足顾客期望和要求的产品”的宗旨，坚持“工厂出产的不仅仅是产品，更重要的是信誉和质量”的经营理念，不断吸收新技术、引进新设备，使公司的经济效益蒸蒸日上。相信公司将会永不停止探索和发展的脚步，和中国国内以及世界国际性大公司同步发展。",
      companyImgsResult: [
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017b2a5938c8bca8012193a37db72c.jpg%402o.jpg&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1630492567&t=e72260aeba623b96fafb25502781e504',
      ],
    );
  }

  _scrollListener() {
    setState(() {
      if (_scrollController.offset < 56 && _isShow) {
        _isShow = false;
      } else if (_scrollController.offset >= 56 && _isShow == false) {
        _isShow = true;
      }
    });
  }

  @override
  void initState() {
    _scrollController = ScrollController();
    _scrollController.addListener(_scrollListener);
    super.initState();
  }

  @override
  void dispose() {
    _scrollController.removeListener(_scrollListener);
    _scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Scaffold(
        backgroundColor: Color.fromARGB(255, 68, 76, 96),
        body: Container(
          decoration: BoxDecoration(
            image: DecorationImage(
              colorFilter: ColorFilter.mode(
                Colors.black.withOpacity(0.1),
                BlendMode.dstATop,
              ),
              fit: BoxFit.cover,
              image: NetworkImage(widget.company.logo),
              alignment: Alignment.center,
            ),
          ),
          child: _companyDetailView(context),
        ),
      ),
    );
  }

  _companyDetailView(BuildContext context) {
    return Stack(
      alignment: Alignment.bottomCenter,
      children: [
        CustomScrollView(
          controller: _scrollController,
          slivers: [
            _buildSliverAppBar(),
            _buildSliverList(),
          ],
        ),
      ],
    );
  }

  Widget _buildSliverAppBar() {
    return SliverAppBar(
      elevation: 0.0,
      pinned: true,
      backgroundColor: Color.fromARGB(_isShow ? 255 : 0, 68, 76, 96),
      centerTitle: false,
      title: Text(
        widget.company.company,
        style: TextStyle(
          fontSize: 20.0,
          color: Color.fromARGB(_isShow ? 255 : 0, 255, 255, 255),
        ),
      ),
      actions: [
        IconButton(
            onPressed: () {},
            icon: Icon(
              Icons.search,
              color: Colors.white,
            ))
      ],
    );
  }

  Widget _buildSliverList() {
    return SliverList(
      delegate: SliverChildListDelegate(
        [
          Row(
            children: [
              Expanded(
                flex: 3,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Padding(
                      padding: EdgeInsets.only(
                        top: 20.0,
                        left: 25.0,
                        bottom: 10.0,
                      ),
                      child: Text(
                        '${widget.company.company}',
                        style: TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.bold,
                          fontSize: 25.0,
                        ),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 25.0),
                      child: Text(
                        '${widget.company.info}',
                        style: TextStyle(
                          color: Colors.white,
                          fontSize: 25.0,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
              Expanded(
                child: Padding(
                  padding: EdgeInsets.only(
                    top: 25.0,
                    right: 30.0,
                  ),
                  child: Hero(
                    tag: widget.heroLogo,
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(8.0),
                      child: Image.network(
                        widget.company.logo,
                        width: 70,
                        height: 70,
                      ),
                    ),
                  ),
                ),
              ),
            ],
          ),
          FutureBuilder<CompanyDetail>(
            future: _fetchCompan(),
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return _companBody(context, snapshot);
              } else if (snapshot.hasError) {
                return Text('${snapshot.error}');
              } else {
                return Center(
                  child: CircularProgressIndicator(),
                );
              }
            },
          ),
        ],
      ),
    );
  }

  // 主体
  Widget _companBody(
      BuildContext context, AsyncSnapshot<CompanyDetail> snapshot) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: EdgeInsets.only(top: 30.0, left: 25.0, right: 20.0),
          child: _craeteWorkHours(),
        ),
        _createWelfareItem(),
        Padding(
          padding: EdgeInsets.only(left: 25.0, bottom: 20.0),
          child: Text(
            '公司介绍',
            style: TextStyle(
              color: Colors.white,
              fontSize: 20.0,
            ),
          ),
        ),
        Padding(
          padding: EdgeInsets.only(left: 25.0, bottom: 10.0, right: 25.0),
          child: Text(
            snapshot.data.inc,
            textAlign: TextAlign.justify,
            style: TextStyle(
              color: Colors.white,
              fontSize: 16.0,
            ),
          ),
        ),
        Padding(
          padding: EdgeInsets.only(top: 20.0, left: 25.0, bottom: 10.0),
          child: Text(
            "公司照片",
            style: new TextStyle(color: Colors.white, fontSize: 20.0),
          ),
        ),
        Container(
          margin:
              EdgeInsets.only(left: 20.0, top: 20.0, right: 0.0, bottom: 50.0),
          height: 120.0,
          child: _createImgList(context, snapshot),
        )
      ],
    );
  }

  // 上班时间
  Widget _craeteWorkHours() {
    return Wrap(
      spacing: 40.0,
      runSpacing: 16.0,
      direction: Axis.horizontal,
      children: <Widget>[
        Row(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Icon(
              Icons.access_alarm,
              color: Colors.white,
              size: 18.0,
            ),
            Padding(
              padding: EdgeInsets.only(right: 6.0),
            ),
            Text(
              '下午1:00-下午10:00',
              style: new TextStyle(color: Colors.white, fontSize: 16.0),
            ),
          ],
        ),
        Row(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Icon(
              Icons.account_balance_wallet,
              color: Colors.white,
              size: 18.0,
            ),
            Padding(
              padding: EdgeInsets.only(right: 6.0),
            ),
            Text(
              '大小周',
              style: new TextStyle(color: Colors.white, fontSize: 16.0),
            ),
          ],
        ),
        Row(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Icon(
              Icons.movie,
              color: Colors.white,
              size: 18.0,
            ),
            Padding(
              padding: EdgeInsets.only(right: 6.0),
            ),
            Text(
              '偶尔加班',
              style: new TextStyle(color: Colors.white, fontSize: 16.0),
            ),
          ],
        ),
      ],
    );
  }

  // 公司福利
  Widget _createWelfareItem() {
    return Padding(
      padding: const EdgeInsets.only(
        top: 30.0,
        bottom: 10.0,
      ),
      child: Container(
        margin: EdgeInsets.only(left: 20.0, top: 0.0, right: 0.0, bottom: 20.0),
        height: 120.0,
        child: ListView(
          scrollDirection: Axis.horizontal,
          children: <Widget>[
            WelfareItem(iconData: Icons.flip, title: "五险一金"),
            WelfareItem(iconData: Icons.security, title: "补充医疗\n保险"),
            WelfareItem(iconData: Icons.access_alarm, title: "定期体检"),
            WelfareItem(iconData: Icons.face, title: "年终奖"),
            WelfareItem(iconData: Icons.brightness_5, title: "带薪年假"),
          ],
        ),
      ),
    );
  }

  // 公司照片
  // 公司照片
  Widget _createImgList(BuildContext context, AsyncSnapshot snapshot) {
    List imgList = snapshot.data.companyImgsResult;
    return ListView.builder(
        key: PageStorageKey('img-list'),
        scrollDirection: Axis.horizontal,
        itemCount: imgList.length,
        itemBuilder: (BuildContext context, int index) {
          return ScrollImageItem(
            url: imgList[index],
            heroTag: 'heroTag$index',
            onPressed: () {
              Navigator.of(context).push(
                PageRouteBuilder<Null>(
                  pageBuilder: (BuildContext context,
                      Animation<double> animation,
                      Animation<double> secondaryAnimation) {
                    return AnimatedBuilder(
                        animation: animation,
                        builder: (BuildContext context, Widget child) {
                          return Opacity(
                            opacity: animation.value,
                            child: GalleryPage(
                              url: imgList[index],
                              heroTag: 'heroTag$index',
                            ),
                          );
                        });
                  },
                  transitionDuration: Duration(milliseconds: 300),
                ),
              );
            },
          );
        });
  }
}
