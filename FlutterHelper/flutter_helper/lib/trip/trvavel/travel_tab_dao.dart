import 'dart:convert';

import 'package:flutter_helper/trip/trvavel/travel_tab_model.dart';
import 'package:http/http.dart' as http;

/// 旅拍类别接口
class TravelTabDao {
  static const _url =
      'http://www.devio.org/io/flutter_app/json/travel_page.json';

  static Future<TravelTabModel> fetch() async {
    final response = await http.get(_url);
    if (response.statusCode == 200) {
      Utf8Decoder utf8decoder = Utf8Decoder();
      var result = json.decode(utf8decoder.convert(response.bodyBytes));
      return TravelTabModel.fromJson(result);
    } else {
      throw Exception('Failed to load travel_page.json');
    }
  }
}
