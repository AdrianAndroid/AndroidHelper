```
MaterialApp.router(
  title: 'Flutter Demo',
  theme: ThemeData(
    primarySwatch: Colors.blue,
    visualDensity: VisualDensity.adaptivePlatformDensity,
  ),
  routeInformationParser: MyRouteParser(),
  routerDelegate: delegate,
)
```

```
class MyRouterDelegate extends RouterDelegate<String> with PopNavigatorRouterDelegateMixin<String>, ChangeNotifier{
    final _stack = <String>[];

    static MyRouteDelegate of(BuildContext context) {
        final delegate = Router.of(context).routerDelegate;
        assert(delegate is MyRouteDelegate, 'Delegate type must match');
        return delegate as MyRouteDelegate;
    }

    MyRouteDelegate({
        @required this.onGenerateRoute,
    });

    //...
    Widget build(BuildContext context) {
        print('${describeIdentiy(this)}.stack: $_stack');
        return Navigator(
            key: navigatorKey,
            onPopPage: _onPage,
            pages: [
                for (final name in _stack) {
                    MyPage(
                        key: ValueKey(name),
                        name: name,
                        routeFactory: onGenerateRoute,
                    ),
                }
            ],
        );
    }
}
```


```dart
class MyRouteDelegate extends RouterDelegate<String>
    with PopNavigatorRouterDelegateMixin<String>, ChangeNotifier {
  final _stack = <String>[];

  static MyRouteDelegate of(BuildContext context) {
    final delegate = Router.of(context).routerDelegate;
    assert(delegate is MyRouteDelegate, 'Delegate type must match');
    return delegate as MyRouteDelegate;
  }

  MyRouteDelegate({
    @required this.onGenerateRoute,
  });

  final RouteFactory onGenerateRoute;

  @override
  GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

  @override
  String get currentConfiguration => _stack.isNotEmpty ? _stack.last : null;

  List<String> get stack => List.unmodifiable(_stack);

  void push(String newRoute) {
    _stack.add(newRoute);
    notifyListeners();
  }

  void pop() {
    if (_stack.isNotEmpty) {
      _stack.remove(_stack.last);
    }
    notifyListeners();
  }

  @override
  Future<void> setInitialRoutePath(String configuration) {
    return setNewRoutePath(configuration);
  }

  @override
  Future<void> setNewRoutePath(String configuration) {
    print('setNewRoutePath $configuration');
    _stack
      ..clear()
      ..add(configuration);
    return SynchronousFuture<void>(null);
  }

  bool _onPopPage(Route<dynamic> route, dynamic result) {
    if (_stack.isNotEmpty) {
      if (_stack.last == route.settings.name) {
        _stack.remove(route.settings.name);
        notifyListeners();
      }
    }
    return route.didPop(result);
  }

  @override
  Widget build(BuildContext context) {
    print('${describeIdentity(this)}.stack: $_stack');
    return Navigator(
      key: navigatorKey,
      onPopPage: _onPopPage,
      pages: [
        for (final name in _stack)
            MyPage(
              key: ValueKey(name),
              name: name,
            ),
      ],
    );
  }
}

```



# RouterParser

```dart
class MyRouteParse extends RouteInformationParser<String> {
  @override
  Future<String> parseRouteInformation(RouteInformation routeInformation){
    return synchronousFuture(routeInfomation.location);
  }
  
  @override
  RouteInformation restoreRouteInformation(String configuration) {
    return RouteInfomation(location: configuration);
  }
}	
```



# VeggieRouteInformationParser

```dart
 class VeggieRouteInformationParser extends RouteInformationparser<VeggieRoutePath> {
   @override
   Future<VeggieRoutePath> parseRouteInformation(RouteInformation routeInformation) async {
     print("parseRouteInformation");
     final uri = Uri.parse(routeInformation.location);
     // Handle '/'
     if (uri.pathSegments.length == 0) {
       return VeggieRoutePath.home();
     }
     // Handle '/vegggie/:id'
     if (uri.pathSegments.length == 2) {
     		if (uri.pathSegments[0] != 'veggie') return VeggieRoutePath.unknown();
      	var remainint = uri.pathSegments[1];
       var id = int.tryParse(remaining);
       if (id == null) return VeggieRoutePath.unknown();
       return VeggieRoutePath.details(id);
     }	
     return VeggieRoutePath.unkonown();
   }
     @override
  RouteInformation restoreRouteInformation(VeggieRoutePath path) {
    print("restoreRouteInformation");
    if (path.isUnknown) {
      return RouteInformation(location: '/404');
    }
    if (path.isHomePage) {
      return RouteInformation(location: '/');
    }
    if (path.isDetailsPage) {
      return RouteInformation(location: '/veggie/${path.id}');
    }
    return null;
  }
 }

class VeggieRoutePath {
  final int id;
  final bool isUnknown;

  VeggieRoutePath.home() : id = null, isUnknown = false;

  VeggieRoutePath.details(this.id) : isUnknown = false;

  VeggieRoutePath.unknown()
      : id = null,
        isUnknown = true;

  bool get isHomePage => id == null;

  bool get isDetailsPage => id != null;
}

```















