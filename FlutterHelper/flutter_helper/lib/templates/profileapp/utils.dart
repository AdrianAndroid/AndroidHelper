import 'package:flutter/material.dart';

class Profile {
  String firstName;
  String lastName;
  String location;
  int numberOfFollowers;
  int numberFollowing;
  int totalLikes;

  String get fullName => "$firstName $lastName";

  String get numberOfFollowersString => _abbreviatedCount(numberOfFollowers);

  String get numberFollowingString => _abbreviatedCount(numberFollowing);

  String get totalLikesString => _abbreviatedCount(totalLikes);

  String _abbreviatedCount(int num) {
    if (num < 1000) return "$num";
    if (num >= 1000 && num < 1000000) {
      String s = (num / 1000).toStringAsFixed(1);
      if (s.endsWith(".0")) {
        int idx = s.indexOf(".0");
        s = s.substring(0, idx);
      }
      return "${s}K";
    } else if (num >= 1000000 && num < 1000000000) {
      String s = (num / 1000000).toStringAsFixed(1);
      if (s.endsWith(".0")) {
        int idx = s.indexOf(".0");
        s = s.substring(0, idx);
      }
      return "${s}M";
    }
    return "";
  }
}

Profile getProfile() {
  return new Profile()
    ..firstName = "Emma"
    ..lastName = "Watson"
    ..location = "New York"
    ..numberOfFollowers = 5700000
    ..numberFollowing = 924
    ..totalLikes = 1700;
}

class ProfileFontNames {
  static final String TimeBurner = "Timeburner";
}


class PColors {
  static final Color primaryColor = new Color(0xFF9A10FF);
}

class TIcons {
  static const fontFamily = "Themify";

  static final IconData bell = new IconData(0xe6b8, fontFamily: fontFamily);
  static final IconData home = new IconData(0xe69b, fontFamily: fontFamily);
  static final IconData user = new IconData(0xe602, fontFamily: fontFamily);
  static final IconData thumbUp = new IconData(0xe670, fontFamily: fontFamily);
  static final IconData tag = new IconData(0xe608, fontFamily: fontFamily);
}

class LAIcons {
  static const fontFamily = "LineAwesome";

  static final IconData bell = new IconData(0xf141, fontFamily: fontFamily);
  static final IconData home = new IconData(0xf237, fontFamily: fontFamily);
  static final IconData user = new IconData(0xf364, fontFamily: fontFamily);
  static final IconData thumbsUp = new IconData(0xf33f, fontFamily: fontFamily);
  static final IconData bookmark = new IconData(0xf14f, fontFamily: fontFamily);
}
