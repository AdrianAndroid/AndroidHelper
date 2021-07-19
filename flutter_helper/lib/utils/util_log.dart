import 'dart:developer';

/// 工具类
class LogUtil {
  static const String _defTag = "common_utils";
  static bool _debugMode = false;
  static int _maxLen = 128;
  static String _tagValue = _defTag;

  static void init({
    String tag = _defTag,
    bool isDebug = false,
    int maxLen = 128,
  }) {
    _tagValue = tag;
    _debugMode = isDebug;
    _maxLen = _maxLen;
  }

  static void d(object, {tag}) {
    if (_debugMode) log('$tag d | ${object?.toString()}');
  }

  static void e(object, {tag}) {
    //if (_debugMode) log('$tag e | ${object?.toString()}');
    if (_debugMode) _printLog(tag, ' e ', object);
  }

  static void v(object, {tag}) {
    if (_debugMode) _printLog(tag, ' v ', object);
  }

  static void _printLog(tag, String stag, object) {
    String da = object?.toString() ?? 'null';
    tag = tag ?? _tagValue;
    if (da.length <= _maxLen) {
      print('$tag$stag $da');
    }
    print('$tag$stag---------------st--------------');
    while (da.isNotEmpty) {
      if (da.length > _maxLen) {
        print('$tag$stag| ${da.substring(0, _maxLen)}');
        da = da.substring(_maxLen, da.length);
      } else {
        print('$tag$stag| $da');
        da = '';
      }
    }
    print('$tag$stag---------------ed--------------');
  }
}
