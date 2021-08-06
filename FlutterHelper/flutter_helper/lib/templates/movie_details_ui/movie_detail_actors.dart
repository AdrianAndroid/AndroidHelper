
import 'package:flutter_helper/samples/flutter_neumorphic/lib/flutter_neumorphic.dart';
import 'package:flutter_helper/templates/movie_details_ui/models.dart';

class MovieDetailActors extends StatelessWidget {
  final List<Actor> actors;

  const MovieDetailActors({Key key, this.actors}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var textTheme = Theme.of(context).textTheme;
    var textStyle = textTheme.subhead.copyWith(fontSize: 18.0);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text('Actors', style: textStyle),
        SizedBox(height: 10),
        SizedBox.fromSize(
          size: const Size.fromHeight(120.0),
          child: ListView.builder(
            itemCount: actors.length,
            scrollDirection: Axis.horizontal,
            itemBuilder: (BuildContext context, int index) {
              var actor = actors[index];
              return Padding(
                padding: EdgeInsets.only(right: 16.0),
                child: Column(
                  children: [
                    CircleAvatar(
                      backgroundImage: AssetImage(actor.avatarUrl),
                      radius: 40.0,
                    ),
                    Text(actor.name),
                  ],
                ),
              );
            },
          ),
        ),
      ],
    );
  }
}
