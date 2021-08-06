import 'package:flutter_helper/samples/flutter_neumorphic/lib/flutter_neumorphic.dart';
import 'package:flutter_helper/templates/movie_details_ui/models.dart';

class MovieDetailHeader extends StatelessWidget {
  final Movie movie;

  MovieDetailHeader(this.movie);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Padding(
          padding: const EdgeInsets.only(bottom: 140.0),
          child: ArcBannerImage(imageUrl: movie.bannerUrl),
        ),
        Positioned(
          bottom: 0.0,
          left: 10.0,
          right: 10.0,
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.end,
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              Poster(posterUrl: movie.posterUrl, height: 180.0),
              SizedBox(width: 10),
              Expanded(child: _movieInformation(context, movie)),
            ],
          ),
        ),
      ],
    );
  }

  _movieInformation(BuildContext context, Movie movie) {
    var textTheme = Theme.of(context).textTheme;
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(movie.title, style: textTheme.title, maxLines: 1),
        //The Secret Life of Pets
        SizedBox(height: 6),
        _ratingInformation(context, movie),
        SizedBox(height: 6),
        _rowChips(textTheme, movie),
      ],
    );
  }

  _rowChips(TextTheme textTheme, Movie movie) {
    return Row(
      children: movie.categories
          .map(
            (category) => Padding(
              padding: EdgeInsets.only(right: 8.0),
              child: Chip(
                label: Text(category),
                labelStyle: textTheme.caption,
                backgroundColor: Colors.black12,
              ),
            ),
          )
          .toList(),
    );
  }

  _ratingInformation(BuildContext context, Movie movie) {
    var theme = Theme.of(context);
    var textTheme = theme.textTheme;
    var ratingCaptionStyle = textTheme.caption.copyWith(color: Colors.black45);

    var textStyle = textTheme.title
        .copyWith(fontWeight: FontWeight.w400, color: theme.accentColor);
    var numbericRating = Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(movie.rating.toString(), style: textStyle),
        SizedBox(height: 4.0),
        Text('Ratings', style: ratingCaptionStyle),
      ],
    );

    _buildRatingBar(ThemeData theme) {
      var stars = <Widget>[];
      for (int i = 1; i < 6; i++) {
        var color = i <= movie.starRating ? theme.accentColor : Colors.black12;
        var star = Icon(Icons.star, color: color);
        stars.add(star);
      }
      return Row(children: stars);
    }

    var starRating = Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        _buildRatingBar(theme),
        SizedBox(height: 4),
        Text('Grade now', style: ratingCaptionStyle),
      ],
    );

    return Row(
      children: [
        numbericRating,
        SizedBox(width: 14.0),
        starRating,
      ],
    );
  }
}

class ArcBannerImage extends StatelessWidget {
  final String imageUrl;

  const ArcBannerImage({Key key, this.imageUrl}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var screenWidth = MediaQuery.of(context).size.width;
    return ClipPath(
      clipper: ArcClipper(),
      child: Image.asset(
        imageUrl,
        width: screenWidth,
        height: 230.0,
        fit: BoxFit.cover,
      ),
    );
  }
}

class ArcClipper extends CustomClipper<Path> {
  @override
  Path getClip(Size size) {
    var path = Path();
    path.lineTo(0.0, size.height - 30);
    var firstControlPoint = Offset(size.width / 4, size.height);
    var firstPoint = Offset(size.width / 2, size.height);
    path.quadraticBezierTo(firstControlPoint.dx, firstControlPoint.dy,
        firstPoint.dx, firstPoint.dy);

    var secondControlPoint = Offset(size.width - (size.width / 4), size.height);
    var secondPoint = Offset(size.width, size.height - 30);
    path.quadraticBezierTo(secondControlPoint.dx, secondControlPoint.dy,
        secondPoint.dx, secondPoint.dy);

    path.lineTo(size.width, 0.0);
    path.close();

    return path;
  }

  @override
  bool shouldReclip(covariant CustomClipper<Path> oldClipper) {
    return false;
  }
}

class Poster extends StatelessWidget {
  static const POSTER_RATIO = 0.7;
  final String posterUrl;
  final double height;

  const Poster({Key key, this.posterUrl, this.height = 100.0})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    var width = POSTER_RATIO * height;
    return Material(
      borderRadius: BorderRadius.circular(4.0),
      elevation: 2.0,
      child: Image.asset(
        posterUrl,
        fit: BoxFit.cover,
        width: width,
        height: height,
      ),
    );
  }
}
