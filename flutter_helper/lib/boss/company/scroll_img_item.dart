import 'package:flutter/cupertino.dart';

class ScrollImageItem extends StatelessWidget {
  final String url;
  final String heroTag;
  VoidCallback onPressed;

  ScrollImageItem({Key key, this.url, this.heroTag, this.onPressed}) : super(key:
  key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onPressed,
      child: Hero(
        tag: heroTag,
        child: Container(
          margin: EdgeInsets.only(right: 20.0),
          child: ClipRRect(
            borderRadius: BorderRadius.circular(8.0),
            child: Image.network(
              url,
              width: 300,
              fit: BoxFit.cover,
            ),
          ),
        ),
      ),
    );
  }
}
