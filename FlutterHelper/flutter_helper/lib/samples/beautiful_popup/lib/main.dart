library beautiful_popup;

import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'dart:ui' as ui;
import 'package:image/image.dart' as img;
import 'package:flutter/services.dart';
import 'templates/Common.dart';
import 'templates/OrangeRocket.dart';
import 'templates/GreenRocket.dart';
import 'templates/OrangeRocket2.dart';
import 'templates/Coin.dart';
import 'templates/BlueRocket.dart';
import 'templates/Thumb.dart';
import 'templates/Gift.dart';
import 'templates/Camera.dart';
import 'templates/Notification.dart';
import 'templates/Geolocation.dart';
import 'templates/Success.dart';
import 'templates/Fail.dart';
import 'templates/Authentication.dart';
import 'templates/Term.dart';
import 'templates/RedPacket.dart';

export 'templates/Common.dart';
export 'templates/OrangeRocket.dart';
export 'templates/GreenRocket.dart';
export 'templates/OrangeRocket2.dart';
export 'templates/Coin.dart';
export 'templates/BlueRocket.dart';
export 'templates/Thumb.dart';
export 'templates/Gift.dart';
export 'templates/Camera.dart';
export 'templates/Notification.dart';
export 'templates/Geolocation.dart';
export 'templates/Success.dart';
export 'templates/Fail.dart';
export 'templates/Authentication.dart';
export 'templates/Term.dart';
export 'templates/RedPacket.dart';

class BeautifulPopup {
  BuildContext _context;
  BuildContext get context => _context;

  Type _template;
  Type get template => _template;

  BeautifulPopupTemplate Function(BeautifulPopup options) _build;
  BeautifulPopupTemplate get instance {
    final build = _build;
    if (build != null) return build(this);
    switch (template) {
      case TemplateOrangeRocket:
        return TemplateOrangeRocket(this);
      case TemplateGreenRocket:
        return TemplateGreenRocket(this);
      case TemplateOrangeRocket2:
        return TemplateOrangeRocket2(this);
      case TemplateCoin:
        return TemplateCoin(this);
      case TemplateBlueRocket:
        return TemplateBlueRocket(this);
      case TemplateThumb:
        return TemplateThumb(this);
      case TemplateGift:
        return TemplateGift(this);
      case TemplateCamera:
        return TemplateCamera(this);
      case TemplateNotification:
        return TemplateNotification(this);
      case TemplateGeolocation:
        return TemplateGeolocation(this);
      case TemplateSuccess:
        return TemplateSuccess(this);
      case TemplateFail:
        return TemplateFail(this);
      case TemplateAuthentication:
        return TemplateAuthentication(this);
      case TemplateTerm:
        return TemplateTerm(this);
      case TemplateRedPacket:
      default:
        return TemplateRedPacket(this);
    }
  }

  ui.Image _illustration;
  ui.Image get illustration => _illustration;

  dynamic title = '';
  dynamic content = '';
  List<Widget> actions;
  Widget close;
  bool barrierDismissible;

  Color primaryColor;

  BeautifulPopup({
    @required BuildContext context,
    @required Type template,
  })   : _context = context,
        _template = template {
    primaryColor = instance.primaryColor; // Get the default primary color.
  }

  static BeautifulPopup customize({
    @required BuildContext context,
    @required BeautifulPopupTemplate Function(BeautifulPopup options) build,
  }) {
    final popup = BeautifulPopup(
      context: context,
      template: null,
    );
    popup._build = build;
    return popup;
  }

  /// Recolor the BeautifulPopup.
  /// This method is  kind of slow.R
  Future<BeautifulPopup> recolor(Color color) async {
    this.primaryColor = color;
    final illustrationData = await rootBundle.load(instance.illustrationKey);
    final buffer = illustrationData.buffer.asUint8List();
    img.Image asset;
    asset = img.readPng(buffer);
    if (asset != null) {
      img.adjustColor(
        asset,
        saturation: 0,
        // hue: 0,
      );
      img.colorOffset(
        asset,
        red: color.red,
        // I don't know why the effect is nicer with the number ╮(╯▽╰)╭
        green: color.green ~/ 3,
        blue: color.blue ~/ 2,
        alpha: 0,
      );
    }
    final paint = await PaintingBinding.instance.instantiateImageCodec(
        asset != null ? Uint8List.fromList(img.encodePng(asset)) : buffer);
    final nextFrame = await paint?.getNextFrame();
    _illustration = nextFrame?.image;
    return this;
  }

  /// `title`: Must be a `String` or `Widget`. Defaults to `''`.
  ///
  /// `content`: Must be a `String` or `Widget`. Defaults to `''`.
  ///
  /// `actions`: The set of actions that are displaed at bottom of the dialog,
  ///
  ///  Typically this is a list of [BeautifulPopup.button]. Defaults to `[]`.
  ///
  /// `barrierDismissible`: Determine whether this dialog can be dismissed. Default to `False`.
  ///
  /// `close`: Close widget.
  Future<T> show<T>({
    dynamic title,
    dynamic content,
    List<Widget> actions,
    bool barrierDismissible = false,
    Widget close,
  }) {
    this.title = title;
    this.content = content;
    this.actions = actions;
    this.barrierDismissible = barrierDismissible;
    this.close = close ?? instance.close;
    final child = WillPopScope(
      onWillPop: () {
        return Future.value(barrierDismissible);
      },
      child: instance,
    );
    return showGeneralDialog<T>(
      barrierColor: Colors.black38,
      barrierDismissible: barrierDismissible,
      barrierLabel: barrierDismissible ? 'beautiful_popup' : null,
      context: context,
      pageBuilder: (context, animation1, animation2) {
        return child;
      },
      transitionDuration: Duration(milliseconds: 150),
      transitionBuilder: (ctx, a1, a2, child) {
        return Transform.scale(
          scale: a1.value,
          child: Opacity(
            opacity: a1.value,
            child: child,
          ),
        );
      },
    );
  }

  BeautifulPopupButton get button => instance.button;
}
