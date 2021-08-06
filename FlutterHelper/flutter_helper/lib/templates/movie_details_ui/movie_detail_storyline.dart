import 'package:flutter_helper/samples/flutter_neumorphic/lib/flutter_neumorphic.dart';

class MovieDetailStoryLine extends StatelessWidget {
  final String stroyline;

  const MovieDetailStoryLine({Key key, this.stroyline}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var theme = Theme.of(context);
    var textTheme = Theme.of(context).textTheme;
    var textStyle = textTheme.subhead.copyWith(fontSize: 20.0);
    var textStyle2 =
        textTheme.body1.copyWith(color: Colors.black45, fontSize: 16.0);
    var textStyle3 =
        textTheme.body1.copyWith(fontSize: 16.0, color: theme.accentColor);
    var accentColor = theme.accentColor;

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text('Story line', style: textStyle),
        SizedBox(height: 10),
        Text(stroyline, style: textStyle2),
        Row(
          crossAxisAlignment: CrossAxisAlignment.end,
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            Text('more', style: textStyle3),
            Icon(Icons.keyboard_arrow_down, size: 18.0, color: accentColor),
          ],
        ), //more
      ],
    );
  }
}
