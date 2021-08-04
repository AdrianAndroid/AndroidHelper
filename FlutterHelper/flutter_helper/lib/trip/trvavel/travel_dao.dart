import 'dart:convert';

import 'package:flutter_helper/trip/trvavel/travel_model.dart';
import 'package:http/http.dart' as http;

///旅拍页接口

var Params = {
  "districtId": -1,
  "groupChannelCode": "RX-OMF",
  "type": null,
  "lat": -180,
  "lon": -180,
  "locatedDistrictId": 0,
  "pagePara": {
    "pageIndex": 1,
    "pageSize": 10,
    "sortType": 9,
    "sortDirection": 0
  },
  "imageCutType": 1,
  "head": {'cid': "09031014111431397988"},
  "contentType": "json"
};

class TravelDao {
  static Future<TravelItemModel> fetch(String url, Map params,
      String groupChannelCode, int pageIndex, int pageSize) async {
    // 拼接参数
    Map paramsMap = params['pagePara'];
    paramsMap['pageIndex'] = pageIndex;
    paramsMap['pageSize'] = pageSize;
    params['groupChannelCode'] = groupChannelCode;
    // 请求网络
    final response = await http.post(Uri.parse(url), body: jsonEncode(params));
    if(response.statusCode == 200) {
      Utf8Decoder utf8decoder = Utf8Decoder(); // 中文乱码
      var result = json.decode(utf8decoder.convert(response.bodyBytes));
      return TravelItemModel.fromJson(result);
    } else {
      throw Exception("Failed to load travel!!");
    }
  }
}
