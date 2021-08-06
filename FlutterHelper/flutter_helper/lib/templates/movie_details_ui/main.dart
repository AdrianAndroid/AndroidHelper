import 'package:flutter/material.dart';
import 'package:flutter_helper/templates/movie_details_ui/models.dart';
import 'package:flutter_helper/templates/movie_details_ui/movie_detail_actors.dart';
import 'package:flutter_helper/templates/movie_details_ui/movie_detail_header.dart';
import 'package:flutter_helper/templates/movie_details_ui/movie_detail_photos.dart';
import 'package:flutter_helper/templates/movie_details_ui/movie_detail_storyline.dart';
import 'package:flutter_helper/templates/movie_details_ui/utils.dart';

void main() => runApp(MovieDetailsUiApp());

class MovieDetailsUiApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        accentColor: const Color(0xFFFF5959),
      ),
      home: MovieDetailsPage(movie: testMovie),
    );
  }
}

class MovieDetailsPage extends StatelessWidget {
  final Movie movie;

  const MovieDetailsPage({Key key, this.movie}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            //Header
            MovieDetailHeader(movie),
            SizedBox(height: 20),
            // Story line
            _padding(MovieDetailStoryLine(stroyline: movie.storyline)),
            SizedBox(height: 20),
            // Photos
            _padding(MovieDetailPhotos(photoUrls: movie.photoUrls)),
            SizedBox(height: 20),
            // Actors
            _padding(MovieDetailActors(actors: movie.actors)),
          ],
        ),
      ),
    );
  }

  _padding(Widget widget) {
    var ei = EdgeInsets.only(left: 10, right: 10);
    return Padding(padding: ei, child: widget);
  }
}
