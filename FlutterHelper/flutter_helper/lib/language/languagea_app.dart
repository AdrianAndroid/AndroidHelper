import 'package:flutter_helper/samples/flutter_neumorphic/lib/flutter_neumorphic.dart';
import 'package:flutter_localizations/flutter_localizations.dart'; // 国际化

class LanguageApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Localizations Sample App',
      localizationsDelegates: [
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate
      ],
      supportedLocales: [
        Locale('en', ''), //English, no country code
        Locale('ko', ''), //Spanish, no country code
        Locale('zh', ''), //Spanish, no country code
      ],
      home: _MyHome(),
    );
  }
}

class _MyHome extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(child: Text('Hello'));
  }
}
