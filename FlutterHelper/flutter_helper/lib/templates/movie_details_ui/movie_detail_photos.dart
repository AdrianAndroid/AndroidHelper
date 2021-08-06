import 'package:flutter_helper/samples/flutter_neumorphic/lib/flutter_neumorphic.dart';

class MovieDetailPhotos extends StatelessWidget {
  final List<String> photoUrls;

  const MovieDetailPhotos({Key key, this.photoUrls}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var textTheme = Theme.of(context).textTheme;
    var textStyle = textTheme.subhead.copyWith(fontSize: 20.0);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text('Photos', style: textStyle),
        SizedBox(height: 10),
        SizedBox.fromSize(
          size: const Size.fromHeight(100.0),
          child: ListView.builder(
            itemCount: photoUrls.length,
            scrollDirection: Axis.horizontal,
            itemBuilder: (BuildContext context, int index) {
              var photo = photoUrls[index];

              return Padding(
                padding: const EdgeInsets.only(right: 16.0),
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(4.0),
                  child: Image.asset(
                    photo,
                    width: 160.0,
                    height: 120.0,
                    fit: BoxFit.cover,
                  ),
                ),
              );
            },
          ),
        ),
      ],
    );
  }
}
