import 'package:flutter/material.dart';
import 'package:flutter_helper/templates/profileapp/page_book.dart';
import 'package:flutter_helper/templates/profileapp/page_drink.dart';
import 'package:flutter_helper/templates/profileapp/page_travel.dart';

import 'page_profile.dart';
import 'utils.dart';

void main() => runApp(new ProfileApp());

class ProfileApp extends StatefulWidget {
  @override
  _ProfileAppState createState() => _ProfileAppState();
}

class _ProfileAppState extends State<ProfileApp> {
  var _curIndex = 3;

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Profile Challenge',
      home: Scaffold(
        body: _buildBody(),
        bottomNavigationBar: BottomNavigationBar(
          currentIndex: _curIndex,
          onTap: (index) {
            if (_curIndex != index) {
              setState(() {
                _curIndex = index;
              });
            }
          },
          fixedColor: PColors.primaryColor,
          iconSize: 25.0,
          type: BottomNavigationBarType.fixed,
          items: [
            _navigationItems(LAIcons.home),
            _navigationItems(LAIcons.bookmark),
            _navigationItems(LAIcons.thumbsUp),
            _navigationItems(LAIcons.user),
          ],
        ),
      ),
    );
  }

  _buildBody() {
    switch(_curIndex) {
      case 0: return BookPage();
      case 1: return DrinkPage();
      case 2: return TravelPage();
      case 3: return ProfilePage();
    }
  }

  _navigationItems(IconData id) =>
      BottomNavigationBarItem(icon: Icon(id), title: Text(''));
}
