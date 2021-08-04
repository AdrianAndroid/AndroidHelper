import 'dart:convert';

import 'package:flutter_helper/trip/home/home_model.dart';
import 'package:http/http.dart' as http;

class HomeDao {

  static final String HOME_URL = 'http://www.devio.org/io/flutter_app/json/home_page.json';

  static Future<HomeModel> fetch() async {

    final response = await http.get(Uri.parse(HOME_URL));
    if(response.statusCode == 200) {
      Utf8Decoder utf8decoder = Utf8Decoder();
      var result = json.decode(utf8decoder.convert(response.bodyBytes));
      return HomeModel.fromJson(result);
    } else {
      throw Exception('Failed to load home_page.json');
    }
  }

}