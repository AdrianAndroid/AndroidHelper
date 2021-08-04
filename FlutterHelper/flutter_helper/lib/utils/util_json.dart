import 'dart:convert';

class JsonUtil {
  /// Converts object [value] to a JSON string.
  static String encodeObj(dynamic value) {
    return value == null ? null : json.encode(value);
  }

  /// Converts JSON string [source] to object;
  static T getObj<T>(String source, T f(Map v)) {
    if (source == null || source.isEmpty) return null;
    try {
      Map map = json.decode(source);
      return f(map);
    } catch (e, s) {
      print('JsonUtil convert error, Exception: ${e.toString()}');
    }
    return null;
  }

  /// Converst JSON string or JSON map [source] t object.
  static T getObject<T>(dynamic source, T f(Map v)) {
    if (source == null || source.toString().isEmpty) return null;
    try {
      Map map;
      if (source is String) {
        map = json.decode(source);
      } else {
        map = source;
      }
      return f(map);
    } catch (e) {
      print('JsonUtil convert error, Exception: ${e.toString()}');
    }
    return null;
  }

  // Converts JSON string list [source] to object list.
  static List<T> getObjectList<T>(dynamic source, T f(Map v)) {
    if (source == null || source.toString().isEmpty) return null;
    try {
      List list;
      if (source is String) {
        list = json.decode(source);
      } else {
        list = source;
      }
      return list.map((value) {
        if (value is String) {
          value = json.decode(value);
        }
        return f(value);
      });
    } catch (e) {
      print('JsonUtil convert error, Exception: ${e.toString()}');
    }
    return null;
  }

  /// get List
  /// [1,2,3,4,5,6]
  /// "[\"tom\",\"tony\",\"jacky\"]";
  static List<T> getList<T>(dynamic source) {
    List list;
    if (source is String) {
      list = json.decode(source);
    } else {
      list = source;
    }
    return list?.map((e) => e as T)?.toList();
  }
}
