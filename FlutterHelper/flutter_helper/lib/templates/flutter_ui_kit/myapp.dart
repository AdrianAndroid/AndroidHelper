import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'ui/page/dashboard/dashboard_one.page.dart';
import 'ui/page/dashboard/dashboard_two_page.dart';
import 'ui/page/home_page.dart';
import 'ui/page/login/login_page.dart';
import 'ui/page/login/login_two_page.dart';
import 'ui/page/notfound/notfound_page.dart';
import 'ui/page/payment/credit_card_page.dart';
import 'ui/page/payment/payment_success_page.dart';
import 'ui/page/profile/profile_one_page.dart';
import 'ui/page/profile/profile_two_page.dart';
import 'ui/page/settings/settings_one_page.dart';
import 'ui/page/shopping/product_detail_page.dart';
import 'ui/page/shopping/shopping_details_page.dart';
import 'ui/page/shopping/shopping_one_page.dart';
import 'ui/page/timeline/timeline_one_page.dart';
import 'ui/page/timeline/timeline_two_page.dart';
import 'utils/translations.dart';
import 'utils/uidata.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class FlutterUiKitApp extends StatelessWidget {
  final materialApp = MaterialApp(
      title: UIData.appName,
      theme: ThemeData(
          primaryColor: Colors.black,
          fontFamily: UIData.quickFont,
          primarySwatch: Colors.amber),
      debugShowCheckedModeBanner: false,
      showPerformanceOverlay: false,
      home: HomePage(),
      localizationsDelegates: [
        const TranslationsDelegate(),
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
      ],
      supportedLocales: [
        const Locale("en", "US"),
        const Locale("hi", "IN"),
      ],
      // initialRoute: UIData.notFoundRoute,

      //routes
      routes: <String, WidgetBuilder>{
        UIData.homeRoute: (context) => HomePage(),
        UIData.profileOneRoute: (context) => ProfileOnePage(),
        UIData.profileTwoRoute: (context) => ProfileTwoPage(),
        UIData.timelineOneRoute: (context) => TimelineOnePage(),
        UIData.timelineTwoRoute: (context) => TimelineTwoPage(),
        UIData.notFoundRoute: (context) => NotFoundPage(),
        UIData.settingsOneRoute: (context) => SettingsOnePage(),
        UIData.shoppingOneRoute: (context) => ShoppingOnePage(),
        UIData.shoppingTwoRoute: (context) => ShoppingDetailsPage(),
        UIData.shoppingThreeRoute: (context) => ProductDetailPage(),
        UIData.loginOneRoute: (context) => LoginPage(),
        UIData.loginTwoRoute: (context) => LoginTwoPage(),
        UIData.paymentOneRoute: (context) => CreditCardPage(),
        UIData.paymentTwoRoute: (context) => PaymentSuccessPage(),
        UIData.dashboardOneRoute: (context) => DashboardOnePage(),
        UIData.dashboardTwoRoute: (context) => DashboardTwoPage(),
      },
      onUnknownRoute: (RouteSettings rs) => new MaterialPageRoute(
          builder: (context) => new NotFoundPage(
                appTitle: UIData.coming_soon,
                icon: FontAwesomeIcons.solidSmile,
                title: UIData.coming_soon,
                message: "Under Development",
                iconColor: Colors.green,
              )));

  @override
  Widget build(BuildContext context) {
    return materialApp;
  }
}
