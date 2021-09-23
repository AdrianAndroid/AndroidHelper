import 'package:flutter/material.dart';
import 'package:flutter_helper/trip/trvavel/travel_dao.dart';
import 'package:flutter_helper/trip/trvavel/travel_model.dart';
import 'package:flutter_helper/trip/utils.dart';
import 'package:flutter_helper/trip/widget/LoadingContainer.dart';
import 'package:flutter_helper/trip/widget/webview.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

const _TRAVEL_URL =
    'https://m.ctrip.com/restapi/soa2/16189/json/searchTripShootListForHomePageV2?_fxpcqlniredt=09031014111431397988&__gw_appid=99999999&__gw_ver=1.0&__gw_from=10650013707&__gw_platform=H5';
const PAGE_SIZE = 10;

class TravelTabPage extends StatefulWidget {
  final String travelUrl;
  final Map params;
  final String groupChannelCode;

  const TravelTabPage(
      {Key key, this.travelUrl, this.params, this.groupChannelCode})
      : super(key: key);

  @override
  State<StatefulWidget> createState() => _TravelTabPageState();
}

class _TravelTabPageState extends State<TravelTabPage>
    with AutomaticKeepAliveClientMixin {
  List<TravelItem> travelItems;
  int pageIndex = 1;
  bool _loading = true;
  ScrollController _scrollController = ScrollController();

  @override
  void initState() {
    _loadData();
    _scrollController.addListener(() {
      if (_scrollController.position.pixels ==
          _scrollController.position.maxScrollExtent) {
        _loadData(loadMore: true);
      }
    });
    super.initState();
  }

  Future<Null> _handleRefresh() async {
    _loadData();
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Scaffold(
          body: LoadingContainer(
            isLoading: _loading,
            child: RefreshIndicator(
              onRefresh: _handleRefresh,
              child: MediaQuery.removePadding(
                context: context,
                child: StaggeredGridView.countBuilder(
                  controller: _scrollController,
                  crossAxisCount: 4,
                  itemCount: travelItems?.length ?? 0,
                  itemBuilder: (context, index) => _TravelItem(
                    index: index,
                    item: travelItems[index],
                  ),
                  staggeredTileBuilder: (index) => StaggeredTile.fit(2),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  void _loadData({bool loadMore = false}) {
    if (loadMore)
      pageIndex++;
    else
      pageIndex = 1;

    TravelDao.fetch(widget.travelUrl ?? _TRAVEL_URL, widget.params,
            widget.groupChannelCode, pageIndex, PAGE_SIZE)
        .then((model) {
      _loading = false;
      setState(() {
        List<TravelItem> items = _filterItems(model.resultList);
        if (travelItems != null) {
          travelItems.clear();
          travelItems.addAll(items);
        } else {
          travelItems = items;
        }
      });
    }).catchError((e) {
      _loading = false;
      print(e);
    });
  }

  List<TravelItem> _filterItems(List<TravelItem> resultList) {
    if (resultList == null) return [];
    List<TravelItem> filterItem = [];
    resultList.forEach((item) {
      if (item.article != null) filterItem.add(item);
    });
    return filterItem;
  }

  @override
  bool get wantKeepAlive => true;
}

class _TravelItem extends StatelessWidget {
  final TravelItem item;
  final int index;

  const _TravelItem({Key key, this.item, this.index}) : super(key: key);

  @override
  Widget build(BuildContext context) => GestureDetector(
        onTap: () {
          if (item.article.urls != null && item.article.urls.length > 0) {
            NavigatorUtil.push(
              context,
              WebView(
                url: item.article.urls[0].h5Url,
                title: '详情',
              ),
            );
          }
        },
        child: Card(
          child: PhysicalModel(
            color: Colors.transparent,
            clipBehavior: Clip.antiAlias,
            borderRadius: BorderRadius.circular(5),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                _itemImage(),
                Container(
                  padding: EdgeInsets.all(4),
                  child: Text(
                    item.article.articleTitle,
                    maxLines: 2,
                    softWrap: true,
                    overflow: TextOverflow.ellipsis,
                    style: TextStyle(fontSize: 14, color: Colors.black87),
                  ),
                ),
                _indexText(),
              ],
            ),
          ),
        ),
      );

  _itemImage() => Stack(
        children: [
          Image.network(item.article.images[0]?.dynamicUrl),
          Positioned(
            bottom: 8,
            left: 8,
            child: Container(
              padding: EdgeInsets.fromLTRB(5, 1, 5, 1),
              decoration: BoxDecoration(
                color: Colors.black54,
                borderRadius: BorderRadius.circular(10),
              ),
              child: Row(
                children: [
                  Padding(
                    padding: EdgeInsets.only(right: 3),
                    child: Icon(
                      Icons.location_on,
                      color: Colors.white,
                      size: 12,
                    ),
                  ),
                  LimitedBox(
                    maxWidth: 130,
                    child: Text(
                      _poiName(),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                      style: TextStyle(color: Colors.white, fontSize: 12),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      );

  String _poiName() {
    return item.article.pois == null || item.article.pois.length == 0
        ? '未知'
        : item.article.pois[0]?.poiName ?? '未知';
  }

  _indexText() => Container(
        padding: EdgeInsets.fromLTRB(6, 0, 6, 10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Row(
              children: [
                PhysicalModel(
                  color: Colors.transparent,
                  clipBehavior: Clip.antiAlias,
                  borderRadius: BorderRadius.circular(12),
                  child: Image.network(
                    item.article.author?.coverImage?.dynamicUrl,
                    width: 24,
                    height: 24,
                  ),
                ),
                Container(
                  padding: EdgeInsets.all(5),
                  width: 90,
                  child: Text(
                    item.article.author?.nickName,
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                    style: TextStyle(fontSize: 12),
                  ),
                ),
              ],
            ),
            Row(
              children: [
                Icon(
                  Icons.thumb_up,
                  size: 14,
                  color: Colors.grey,
                ),
                LimitedBox(
                  maxWidth: 30,
                  child: Padding(
                    padding: EdgeInsets.only(left: 3),
                    child: Text(
                      // '11111111111',
                      item.article.likeCount.toString(),
                      style: TextStyle(fontSize: 10),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      );
}
