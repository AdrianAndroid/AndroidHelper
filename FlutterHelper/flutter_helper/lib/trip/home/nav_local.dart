import 'package:flutter/material.dart';
import 'package:flutter_helper/trip/home/home_model.dart';
import 'package:flutter_helper/trip/utils.dart';
import 'package:flutter_helper/trip/widget/webview.dart';

class LocalNav extends StatelessWidget {
  final List<CommonModel> localNavList;

  const LocalNav({Key key, this.localNavList}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 64,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.all(Radius.circular(6)),
      ),
      child: Padding(
        padding: EdgeInsets.all(7),
        child: _items(context),
      ),
    );
  }

  _items(BuildContext context) {
    if (localNavList == null) return null;
    List<Widget> items = [];
    localNavList.forEach((model) {
      items.add(_item(context, model));
    });
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: items,
    );
  }

  Widget _item(BuildContext context, CommonModel model) {
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
      child: Column(
        children: [
          Image.network(model.icon, width: 32, height: 32),
          Text(model.title, style: TextStyle(fontSize: 12)),
        ],
      ),
    );
  }
}
