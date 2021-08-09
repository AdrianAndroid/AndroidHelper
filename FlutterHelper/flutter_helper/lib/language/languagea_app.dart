import 'package:flutter_helper/generated/l10n.dart';
import 'package:flutter_helper/samples/flutter_neumorphic/lib/flutter_neumorphic.dart';
import 'package:flutter_localizations/flutter_localizations.dart'; // 国际化

class LanguageApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    // var localization = AppLocalizationDelegate();
    // var support = localization.supportedLocales;
    return const MaterialApp(
      title: 'Localizations Sample App',
      locale: Locale('ko', ''),
      localizationsDelegates: [
        S.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate
      ],
      supportedLocales: [
        Locale('en', ''), //English, no country code
        Locale('ko', ''), //Spanish, no country code
        Locale('zh', ''), //Spanish, no country code
      ],
      home:  Scaffold(
        body: _MyHome(),
      ),
    );
  }
}

class _MyHome extends StatefulWidget {
  const _MyHome();

  @override
  __MyHomeState createState() => __MyHomeState();
}

class __MyHomeState extends State<_MyHome> {
  @override
  Widget build(BuildContext context) {
    return Center(child: Text(S.of(context).check_network_describe));
  }
}
