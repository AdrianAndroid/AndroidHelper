// GENERATED CODE - DO NOT MODIFY BY HAND
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'intl/messages_all.dart';

// **************************************************************************
// Generator: Flutter Intl IDE plugin
// Made by Localizely
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, lines_longer_than_80_chars
// ignore_for_file: join_return_with_assignment, prefer_final_in_for_each
// ignore_for_file: avoid_redundant_argument_values

class S {
  S();
  
  static S current;
  
  static const AppLocalizationDelegate delegate =
    AppLocalizationDelegate();

  static Future<S> load(Locale locale) {
    final name = (locale.countryCode?.isEmpty ?? false) ? locale.languageCode : locale.toString();
    final localeName = Intl.canonicalizedLocale(name); 
    return initializeMessages(localeName).then((_) {
      Intl.defaultLocale = localeName;
      S.current = S();
      
      return S.current;
    });
  } 

  static S of(BuildContext context) {
    return Localizations.of<S>(context, S);
  }

  /// `Link has been successfully copied to clipboard.`
  String get copy_link_success {
    return Intl.message(
      'Link has been successfully copied to clipboard.',
      name: 'copy_link_success',
      desc: '',
      args: [],
    );
  }

  /// `Your account has been kicked off.Please log in again!`
  String get udb_kickoff_comment_msg {
    return Intl.message(
      'Your account has been kicked off.Please log in again!',
      name: 'udb_kickoff_comment_msg',
      desc: '',
      args: [],
    );
  }

  /// `We have received your message and thank you for your feedback!`
  String get report_problem_toast {
    return Intl.message(
      'We have received your message and thank you for your feedback!',
      name: 'report_problem_toast',
      desc: '',
      args: [],
    );
  }

  /// `Send OTP`
  String get send_otp {
    return Intl.message(
      'Send OTP',
      name: 'send_otp',
      desc: '',
      args: [],
    );
  }

  /// `Timatable`
  String get timetable {
    return Intl.message(
      'Timatable',
      name: 'timetable',
      desc: '',
      args: [],
    );
  }

  /// `This content is no longer available to be watched and will be cleared.`
  String get download_file_invalid {
    return Intl.message(
      'This content is no longer available to be watched and will be cleared.',
      name: 'download_file_invalid',
      desc: '',
      args: [],
    );
  }

  /// `This live link is not valid. Please contact the institution to update.`
  String get zoom_error_livelinkInvalid {
    return Intl.message(
      'This live link is not valid. Please contact the institution to update.',
      name: 'zoom_error_livelinkInvalid',
      desc: '',
      args: [],
    );
  }

  /// `Login to chat with your teacher`
  String get not_login_reminder_chatlist {
    return Intl.message(
      'Login to chat with your teacher',
      name: 'not_login_reminder_chatlist',
      desc: '',
      args: [],
    );
  }

  /// `Login to see your timetable`
  String get not_login_reminder_timatable {
    return Intl.message(
      'Login to see your timetable',
      name: 'not_login_reminder_timatable',
      desc: '',
      args: [],
    );
  }

  /// `Login to see your batch`
  String get not_login_reminder_batch {
    return Intl.message(
      'Login to see your batch',
      name: 'not_login_reminder_batch',
      desc: '',
      args: [],
    );
  }

  /// `Login to see your courses`
  String get not_login_reminder_course {
    return Intl.message(
      'Login to see your courses',
      name: 'not_login_reminder_course',
      desc: '',
      args: [],
    );
  }

  /// `Diagnostics complete, log has been generated.`
  String get check_network_toast {
    return Intl.message(
      'Diagnostics complete, log has been generated.',
      name: 'check_network_toast',
      desc: '',
      args: [],
    );
  }

  /// `Please do not quit this page or the APP during the process.`
  String get check_network_warning {
    return Intl.message(
      'Please do not quit this page or the APP during the process.',
      name: 'check_network_warning',
      desc: '',
      args: [],
    );
  }

  /// `We are currently checking your network status and generating a log, which would help our developers to better solve your problem. `
  String get check_network_describe {
    return Intl.message(
      'We are currently checking your network status and generating a log, which would help our developers to better solve your problem. ',
      name: 'check_network_describe',
      desc: '',
      args: [],
    );
  }

  /// `Leave Early`
  String get leave_early {
    return Intl.message(
      'Leave Early',
      name: 'leave_early',
      desc: '',
      args: [],
    );
  }

  /// `Checking network status...`
  String get check_network_state {
    return Intl.message(
      'Checking network status...',
      name: 'check_network_state',
      desc: '',
      args: [],
    );
  }

  /// `Live broadcast has ended`
  String get live_end {
    return Intl.message(
      'Live broadcast has ended',
      name: 'live_end',
      desc: '',
      args: [],
    );
  }

  /// `Please describe the problems you encountered.`
  String get report_problem_question_content {
    return Intl.message(
      'Please describe the problems you encountered.',
      name: 'report_problem_question_content',
      desc: '',
      args: [],
    );
  }

  /// `What problems have you encountered?`
  String get report_problem_question_title {
    return Intl.message(
      'What problems have you encountered?',
      name: 'report_problem_question_title',
      desc: '',
      args: [],
    );
  }

  /// `You can use this feature to report a bug/issue to the developers.	`
  String get report_problem_describe {
    return Intl.message(
      'You can use this feature to report a bug/issue to the developers.	',
      name: 'report_problem_describe',
      desc: '',
      args: [],
    );
  }

  /// `When you encounter a problem, you can communicate with the staff from the institution via WhatsApp`
  String get contact_via_whatsapp_describe {
    return Intl.message(
      'When you encounter a problem, you can communicate with the staff from the institution via WhatsApp',
      name: 'contact_via_whatsapp_describe',
      desc: '',
      args: [],
    );
  }

  /// `When you encounter a problem, you can leave a message to the institution. The institution will contact you later.`
  String get leave_message_describe {
    return Intl.message(
      'When you encounter a problem, you can leave a message to the institution. The institution will contact you later.',
      name: 'leave_message_describe',
      desc: '',
      args: [],
    );
  }

  /// `You can find answers to the most frequently asked questions here.`
  String get faq_describe {
    return Intl.message(
      'You can find answers to the most frequently asked questions here.',
      name: 'faq_describe',
      desc: '',
      args: [],
    );
  }

  /// `Looks like you don't have any events!`
  String get timetable_Nodata_alertString {
    return Intl.message(
      'Looks like you don\'t have any events!',
      name: 'timetable_Nodata_alertString',
      desc: '',
      args: [],
    );
  }

  /// `Deadline：%@`
  String get deadline {
    return Intl.message(
      'Deadline：%@',
      name: 'deadline',
      desc: '',
      args: [],
    );
  }

  /// `This function is not available on APP. Please move to the pc website for operation.`
  String get router_uri_cannot_open {
    return Intl.message(
      'This function is not available on APP. Please move to the pc website for operation.',
      name: 'router_uri_cannot_open',
      desc: '',
      args: [],
    );
  }

  /// `Hello World!`
  String get helloWorld {
    return Intl.message(
      'Hello World!',
      name: 'helloWorld',
      desc: 'The conventional newborn programmer greeting',
      args: [],
    );
  }
}

class AppLocalizationDelegate extends LocalizationsDelegate<S> {
  const AppLocalizationDelegate();

  List<Locale> get supportedLocales {
    return const <Locale>[
      Locale.fromSubtags(languageCode: 'en'),
      Locale.fromSubtags(languageCode: 'ko'),
      Locale.fromSubtags(languageCode: 'zh'),
    ];
  }

  @override
  bool isSupported(Locale locale) => _isSupported(locale);
  @override
  Future<S> load(Locale locale) => S.load(locale);
  @override
  bool shouldReload(AppLocalizationDelegate old) => false;

  bool _isSupported(Locale locale) {
    if (locale != null) {
      for (var supportedLocale in supportedLocales) {
        if (supportedLocale.languageCode == locale.languageCode) {
          return true;
        }
      }
    }
    return false;
  }
}