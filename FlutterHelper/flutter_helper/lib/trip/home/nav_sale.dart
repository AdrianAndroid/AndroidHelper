import 'package:flutter/material.dart';
import 'package:flutter_helper/trip/home/home_model.dart';
import 'package:flutter_helper/trip/utils.dart';
import 'package:flutter_helper/trip/widget/webview.dart';

class SalesNav extends StatelessWidget {
  final SalesBoxModel salesBoxModel;

  const SalesNav({Key key, this.salesBoxModel}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(color: Colors.white),
      child: _items(context),
    );
  }

  _items(BuildContext context) {
    if (salesBoxModel == null) return null;
    List<Widget> items = [];
    items.add(_doubleItem(
      context,
      salesBoxModel.bigCard1,
      salesBoxModel.bigCard2,
      true,
      false,
    ));
    items.add(_doubleItem(
      context,
      salesBoxModel.smallCard1,
      salesBoxModel.smallCard2,
      false,
      false,
    ));
    items.add(_doubleItem(
      context,
      salesBoxModel.smallCard3,
      salesBoxModel.smallCard4,
      false,
      true,
    ));

    return Column(
      children: [
        Container(
          height: 44,
          margin: EdgeInsets.only(left: 10),
          decoration: BoxDecoration(
            border: Border(
              bottom: BorderSide(
                width: 1,
                color: Color(0xfff2f2f2),
              ),
            ),
          ),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Image.network(salesBoxModel.icon, width: 15, fit: BoxFit.fill),
              Container(
                padding: EdgeInsets.only(right: 7),
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(12),
                  gradient: LinearGradient(
                    colors: [Color(0xffff4e63), Color(0xffff6cc9)],
                    begin: Alignment.centerLeft,
                    end: Alignment.centerRight,
                  ),
                ),
                child: GestureDetector(
                  onTap: () {
                    NavigatorUtil.push(context,
                        WebView(url: salesBoxModel.moreUrl, title: "更多活动"));
                  },
                  child: Text(
                    '获取更多福利>',
                    style: TextStyle(color: Colors.white, fontSize: 12),
                  ),
                ),
              ),
            ],
          ),
        ),
        Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: items.sublist(0, 1)),
        Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: items.sublist(1, 2)),
        Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: items.sublist(2, 3)),
      ],
    );
  }
  Widget _doubleItem(BuildContext context, CommonModel leftCard,
      CommonModel rightCard, bool big, bool last) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        _item(context, leftCard, big, true, last),
        _item(context, rightCard, big, false, last)
      ],
    );
  }

  Widget _item(
      BuildContext context, CommonModel model, bool big, bool left, bool last) {
    BorderSide borderSide = BorderSide(width: 0.8, color: Color(0xfff2f2f2));
    return GestureDetector(
        onTap: () {
          NavigatorUtil.push(
              context,
              WebView(
                url: model.url,
                statusBarColor: model.statusBarColor,
                hideAppBar: model.hideAppBar,
              ));
        },
        child: Container(
          decoration: BoxDecoration(
              border: Border(
                  right: left ? borderSide : BorderSide.none,
                  bottom: last ? BorderSide.none : borderSide)),
          child: Image.network(
            model.icon,
            fit: BoxFit.fill,
            width: MediaQuery.of(context).size.width / 2 - 10,
            height: big ? 129 : 80,
          ),
        ));
  }
}
