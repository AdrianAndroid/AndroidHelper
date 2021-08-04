class ObjectUtil {
  /// Returns true if the string is null or 0-length
  static bool isEmptyString(String str) => str == null || str.isEmpty;

  /// Returns true if the list is null or 0-lenght
  static bool isEmptyList(Iterable list) => list == null || list.isEmpty;

  /// Returns true if there is no key/value pair in the map
  static bool isEmptyMap(Map map) => map == null || map.isEmpty;

  /// Returns true String or List or Map is empty.
  static bool isEmpty(Object object) {
    if (object == null) return true;
    if (object is String && object.isEmpty)
      return true;
    else if (object is Iterable && object.isEmpty)
      return true;
    else if (object is Map && object.isEmpty)
      return true;
    else
      return false;
  }

  /// Returns true String or List or Map is not empty.
  static bool isNotEmpty(Object object) => !isEmpty(object);

  static bool twoListIsEqual(List listA, List listB) {
    if (listA == listB) return true;
    if (listA == null || listB == null) return false;
    if (listA.length != listB.length) return false;
    listA.forEach((element) {
      if (!listB.contains(element)) {
        return false;
      }
    });
    return true;
  }

  /// get length.
  static int getLength(Object value) {
    if (value == null) return 0;
    if (value is String) return value.length;
    if (value is Iterable) return value.length;
    if (value is Map) return value.length;
    return 0;
  }
}
