import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/animated_selection_slide/lib/main.dart';
import 'package:flutter_helper/boss/boss_app.dart';
import 'package:flutter_helper/boss/utils.dart';
import 'package:flutter_helper/flutter_tags/main.dart';
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
import 'package:flutter_helper/samples/table_calendar/main.dart';
import 'package:flutter_helper/samples/table_planner/main.dart';
import 'package:flutter_helper/samples/timeline_tile/main.dart';
import 'package:flutter_helper/samples/timelines/main.dart';
import 'package:flutter_helper/samples/tinder_cards/main.dart';
import 'package:flutter_helper/samples/typeahed/cupertino_app.dart';
import 'package:flutter_helper/samples/typeahed/material_app.dart';
import 'package:flutter_helper/trip/trip_app.dart';
import 'package:flutter_helper/utils/util_log.dart';
import 'package:flutter_helper/widgets/widgets_page.dart';
import 'package:flutter_helper/widgets2/search_appbar.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

import 'before_after/main.dart';
import 'dough/main.dart';
import 'flutter_calendar/main.dart';
import 'flutter_calendar_carousel/main.dart';
void main() {
  LogUtil.init(isDebug: true);

  runApp(SamplesApp());
}

class SamplesApp extends StatefulWidget {
  @override
  _SamplesAppState createState() => _SamplesAppState();
}

class _SamplesAppState extends State<SamplesApp> {

  var _simpleItem = false;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Flutter DEMO APP",
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: _searchAppBarWidget,
        // appBar: AppBar(
        //   title: Text("Flutter"),
        //   actions: [
        //     IconButton(
        //         onPressed: () {
        //           setState(() {
        //             _simpleItem = !_simpleItem;
        //           });
        //         },
        //         icon: Icon(Icons.swap_calls)),
        //   ],
        // ),
        body: Column(
          children: [
            Expanded(
              child: Padding(
                padding: EdgeInsets.only(right: 30),
                child:
                _simpleItem ? _buildSImpleGridView(context) : _buildGrid(),
              ),
            ),
          ],
        ),
      ),
    );
  }

  List<Widget> _mwidgets = [
    CalendarApp(),
    CalendarCarouselApp(),
    TablePlanneryApp(),
    TableCanlendarApp(),
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

  var _listDisplay ;
  FocusNode _focusNode = new FocusNode();
  TextEditingController _controller = TextEditingController();

  PreferredSizeWidget get _searchAppBarWidget => SearchAppBarWidget(
    focusNode: _focusNode,
    controller: _controller,
    elevation: 2.0,
    leading: IconButton(
      onPressed: () {
        Navigator.pop(context);
      },
      icon: Icon(Icons.arrow_back),
    ),
    inputFormatters: [LengthLimitingTextInputFormatter(50)],
    onChangedCallback: (str) {
      if(str.isNotEmpty) {
        setState(() {
          // double _items.clear()
          LogUtil.e('onChangeCallBack -> $str');
          var tmp = _mwidgets
              .where((e) =>
              e.toStringShort().toLowerCase().contains(str.toLowerCase()))
              .toList();
          _listDisplay = tmp;
        });
      } else {
        setState(() {
          _listDisplay = null;
        });
      }
    },
    onEditingComplete: () {
      showToast('onEditingComplete!');
      setState(() {
        _listDisplay = null;
      });
    },
  );



  _buildSImpleGridView(BuildContext context) {
    if(_listDisplay == null) {
      _listDisplay = _mwidgets;
    }
    return GridView.builder(
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 3, //每行三列
          childAspectRatio: 1.0 //显示区域宽高相等
      ),
      itemCount: _listDisplay.length,
      itemBuilder: (context, index) {
        return GestureDetector(
          onTap: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (_) {
                return _listDisplay[index];
              }),
            );
          },
          child: Text("${_listDisplay[index].toStringShort()}"),
        );
      },
    );
  }

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
              SizedBox(height: 5),
              ConstrainedBox(
                constraints: BoxConstraints(
                  // maxWidth: 150,
                  maxHeight: 250,
                ),
                child: w,
              ),
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
