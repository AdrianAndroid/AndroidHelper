import 'package:flutter/material.dart';
import 'navigator_v2/route_Information_parser.dart';
import 'navigator_v2/router_delegate.dart';

void main() {
  runApp(NavigatorVeggiesApp());
}

class NavigatorVeggiesApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _NavigatorVeggiesAppState();
}

class _NavigatorVeggiesAppState extends State<NavigatorVeggiesApp> {
  VeggieRouterDelegate _routerDelegate = VeggieRouterDelegate();
  VeggieRouteInformationParser _routeInformationParser =
      VeggieRouteInformationParser();

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      title: 'Veggies App',
      routerDelegate: _routerDelegate,
      routeInformationParser: _routeInformationParser,
    );
  }
}
