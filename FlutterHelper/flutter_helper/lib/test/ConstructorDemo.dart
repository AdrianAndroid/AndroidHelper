import 'package:flutter/cupertino.dart';

class Person {
  String name;
  num age;

  Person({this.name, this.age});

  Person.fromDictionary(Map dic) {
    this.name = dic['name'];
    this.age = dic['age'];
  }
}

class Person2 {
  String firstName;

  // 无参数的，非命名的构造函数
  Person2() {
    print('in Person2');
  }
}

class Son2 extends Person2 {
  // 因为父类有无参数的，非命名的构造函数， 所以可以不用手动调用父类的构造函数
  Son2.fromDictionary(Map data) {
    print('in son2');
  }
}

// 如果父类不现实提供无名无参的构造函数，在子类中必须手动调用父类的一个构造函数。
// 这种情况下，调用父类的构造函数的代码放在子类构造函数名后，子类构造函数体前，中间使用：分割
class Son3 extends Son2 {
  // 父类没有无参数的，非命名的 构造函数，所以必须手动调用一个父类的构造函数
  Son3.fromDictionary(Map data) : super.fromDictionary(data) {
    print('in Son3');
  }
}

// 父类中的命名构造函数不能被子类继承。如果想要子类也拥有一个父类一样名字的构造函数，
// 必须在子类中实现这个构造函数

//如果想要让类产生一个永远不会改变的对象，可以让这些对象成为编译时常量。为此，需要定义一个 const 构造函数并确保所有的实例变量都是 final 的
class ImmutablePoint {
  final num x;
  final num y;

  const ImmutablePoint(this.x, this.y);

  static final ImmutablePoint origin = const ImmutablePoint(0, 0);
}

class Point {
  num x;
  num y;

  // 主构造函数
  Point(this.x, this.y);

  // 重定向构造函数， 指向主构造函数， 函数体为空
  // 貌似swift中遍历构造函数，但略有不同
  Point.alongXAxis(num x) : this(x, 0);
}

class Vehicle {
  Vehicle() {
    print("super constructor");
  }

  Vehicle.get() {
    print("super Run");
  }

  Vehicle.create() {
    print("super create");
  }
}

class Audi extends Vehicle {
  Audi() {
    print("Audi constructor");
  }

  Audi.get() : super.get() {
    print("Audi run");
  }

  Audi.create() {
    print("Audi create");
  }
}

class Point3 {
  num x, y;

  Point3(this.x, this.y);

  Point3.origin() {
    this.x = 10;
    this.y = 10;
  }

  // 构造参数为实例变量直接赋值
  Point3.rect(this.x, this.y);

  Point3.fromJson(Map<String, num> json)
      : x = json['x'],
        y = json['y'] {
    print("In Point.fromJson(): ($x, $y)");
  }

  void pointPrint() {
    print("x = $x, y = $y");
  }
}

class Rect {
  num x, y, wid, hei;

  Rect(this.x, this.y, this.wid, this.hei);

  Rect.withSize(num width, num height) : this(0, 0, width, height);
}

// 常量构造函数（单利）
class ImmutablePoint2 {
  static final ImmutablePoint2 origin = const ImmutablePoint2(0, 0);
  final num x, y;

  const ImmutablePoint2(this.x, this.y);
}

// 工厂构造函数(池子)
class Logger {
  final String name;
  bool mute = false;

  // _cache is library-private, thanks to
  // the _ in front of its name.
  static final Map<String, Logger> _cache = <String, Logger>{};

  factory Logger(String name){
    if(_cache.containsKey(name)) {
      return _cache[name];
    } else {
      final logger = Logger._internal(name);
      _cache[name] = logger;
      return logger;
    }
  }

  Logger._internal(this.name);

  void log(String msg) {
    if(!mute) print(msg);
  }



}

int main() {
  Audi audi0 = Audi();
  Audi audi1 = Audi.create();
  Audi audi2 = Audi.get();

  Point3 p3 = Point3.origin();
  p3.pointPrint();

  // 调用
  var logger = Logger('UI');
  logger.log("Button clicked");
}
