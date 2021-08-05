import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/boss_app.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';
import 'package:flutter_helper/other/other_app.dart';
import 'package:flutter_helper/samples/beautiful_popup/main.dart';
import 'package:flutter_helper/samples/card_settings/main.dart';
import 'package:flutter_helper/samples/credit_card_input_form/main.dart';
import 'package:flutter_helper/samples/direct_select_flutter/main.dart';
import 'package:flutter_helper/samples/fb_reaction_box/main.dart';
import 'package:flutter_helper/samples/flip_panel/main.dart';
import 'package:flutter_helper/samples/flushbar/main.dart';
import 'package:flutter_helper/samples/flutter_image_sequence_animator/main.dart';
import 'package:flutter_helper/samples/flutter_neumorphic/main.dart';
import 'package:flutter_helper/samples/folding_cell/demos/demos_new.dart';
import 'package:flutter_helper/samples/liquid_pull_to_refresh/main.dart';
import 'package:flutter_helper/samples/pin_code_text_field/main.dart';
import 'package:flutter_helper/samples/radial_menu/demo.dart';
import 'package:flutter_helper/samples/samples_1.dart';
import 'package:flutter_helper/samples/scatcher/main.dart';
import 'package:flutter_helper/samples/snaplist/main.dart';
import 'package:flutter_helper/samples/staggerd/main.dart';
import 'package:flutter_helper/samples/step_touch/main.dart';
import 'package:flutter_helper/samples/timeline_tile/main.dart';
import 'package:flutter_helper/samples/timelines/main.dart';
import 'package:flutter_helper/samples/tinder_cards/main.dart';
import 'package:flutter_helper/samples/typeahed/cupertino_app.dart';
import 'package:flutter_helper/samples/typeahed/material_app.dart';
import 'package:flutter_helper/trip/trip_app.dart';
import 'package:flutter_helper/utils/util_log.dart';
import 'package:flutter_helper/widgets/widgets_page.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

import 'animated_selection_slide/lib/main.dart';
import 'flutter_tags/main.dart';
import 'samples/before_after/main.dart';
import 'samples/dough/main.dart';

void main() {
  LogUtil.init(isDebug: true);

  runApp(_HomeApp());
}

class _HomeApp extends StatefulWidget {
  @override
  __HomeAppState createState() => __HomeAppState();
}

class __HomeAppState extends State<_HomeApp> {
  var listApp = [
    AppData(app: BossApp()),
    AppData(app: TripApp(), imageUrl: 'images/ic_trip.png'),
    AppData(app: WidgetPage(), imageUrl: 'images/bird.png'),
    AppData(app: OtherApp(), imageUrl: 'images/beatiful_lady.jpeg'),
  ];

  Widget buildHorizontal() {
    return Container(
      height: 100,
      child: ListView.builder(
        scrollDirection: Axis.horizontal,
        itemBuilder: (BuildContext context, int index) {
          var name = listApp[index].app.toStringShort();
          return GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (_) {
                  return listApp[index].app;
                }),
              );
            },
            child: Padding(
              padding: EdgeInsets.only(right: 10),
              child: Column(
                children: [
                  Image.asset(
                    listApp[index].imageUrl,
                    width: 60,
                    height: 80,
                  ),
                  Text(name)
                ],
              ),
            ),
          );
        },
        itemCount: listApp.length,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter DEMO APP",
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text("Flutter"),
        ),
        body: Column(
          children: [
            buildHorizontal(),
            Expanded(
              child: Padding(
                padding: EdgeInsets.only(right: 30),
                child: _buildGrid(),
              ),
            ),
          ],
        ),
      ),
    );
  }

  List<Widget> _mwidgets = [
    TimelineTileApp(),
    TimeLinesApp(),
    CardSettingsApp(),
    DoughApp(),
    FlutterNeumorphicApp(),
    FlutterTagsApp(),
    AnimatedSelectionSlideApp(),
    CreditCardInputFormApp(),
    BeautifulPopupApp(),
    ImageSequenceAnimatorApp(),
    ScatcherApp(),
    BeforeAfterApp(),
    LiquidPullToRefreshApp(),
    DirectSelectFlutterApp(),
    FoldingCellSimpleDemo(),
    PinCodeTextFieldApp(),
    SnapListApp(),
    TypeAheadMaterialApp(),
    TypeAheadCupertinoApp(),
    StepTouchApp(),
    YourAwesomeApp(),
    FbReactionBoxApp(),
    FlipPanelApp(),
    TinderCardsApp(),
    GridViewCount(),
    ListViewHorizontal(),
    StaggerApp(),
    RadialMenuApp(),
  ];

  ScrollController _scrollController = ScrollController();

  _buildGrid() {
    return StaggeredGridView.countBuilder(
      controller: _scrollController,
      crossAxisCount: 4,
      itemCount: _mwidgets.length,
      itemBuilder: _builtItem,
      staggeredTileBuilder: (int index) => new StaggeredTile.fit(2),
    );
  }

  Widget _builtItem(BuildContext context, int index) {
    Widget w = _mwidgets[index];
    High high;
    if (w is HighMixin) {
      high = (w as HighMixin).getHigh();
    } else {
      high = High(w.toStringShort(), "没有实现minxin");
    }
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (_) {
            return w;
          }),
        );
      },
      child: Card(
        color: Colors.transparent,
        child: Container(
          color: Colors.white,
          width: 20,
          height: 280,
          child: Column(
            children: [
              Text(high.title),
              ConstrainedBox(
                  constraints: BoxConstraints(
                    // maxWidth: 150,
                    maxHeight: 250,
                  ),
                  child: w,),
            ],
          ),
        ),
      ),
    );
  }
}

class AppData {
  final String name;
  final String imageUrl;
  final Widget app;

  AppData({
    Key key,
    this.app,
    this.imageUrl = 'images/bossapp2x.png',
    this.name = "app",
  });
}
