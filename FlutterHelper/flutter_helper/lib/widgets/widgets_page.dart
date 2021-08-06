import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/boss/utils.dart';
import 'package:flutter_helper/hightlight/mixin_highlight.dart';
import 'package:flutter_helper/hightlight/syntax_highlight.dart';
import 'package:flutter_helper/utils/util_log.dart';
import 'package:flutter_helper/widgets/a002_about_list_tile.dart';
import 'package:flutter_helper/widgets/a003_absorbpointer.dart';
import 'package:flutter_helper/widgets/a004_actionchip.dart';
import 'package:flutter_helper/widgets/a005_alertdialog.dart';
import 'package:flutter_helper/widgets/a006_align.dart';
import 'package:flutter_helper/widgets/a007_aniatedbuilder.dart';
import 'package:flutter_helper/widgets/a008_animated_container.dart';
import 'package:flutter_helper/widgets/a009_animated_crossfade.dart';
import 'package:flutter_helper/widgets/a010_animate_default_textstyle.dart';
import 'package:flutter_helper/widgets/a011_animate_icon.dart';
import 'package:flutter_helper/widgets/a012_animate_list.dart';
import 'package:flutter_helper/widgets/a013_animate_modalbarrier.dart';
import 'package:flutter_helper/widgets/a014_animate_opacity.dart';
import 'package:flutter_helper/widgets/a015_animate_padding.dart';
import 'package:flutter_helper/widgets/a016_animate_physicalmodel.dart';
import 'package:flutter_helper/widgets/a017_animate_positioned.dart';
import 'package:flutter_helper/widgets/a018_animate_positioned_directional.dart';
import 'package:flutter_helper/widgets/a019_animate_size.dart';
import 'package:flutter_helper/widgets/a020_animate_switcher.dart';
import 'package:flutter_helper/widgets/a021_appbar.dart';
import 'package:flutter_helper/widgets/a023_buttons.dart';
import 'package:flutter_helper/widgets/a024_backdropfilter.dart';
import 'package:flutter_helper/widgets/a025_banner.dart';
import 'package:flutter_helper/widgets/a026_baseline.dart';
import 'package:flutter_helper/widgets/a027_border.dart';
import 'package:flutter_helper/widgets/a028_bottomappbar.dart';
import 'package:flutter_helper/widgets/a029_bottomnavigationbar.dart';
import 'package:flutter_helper/widgets/a031_builder.dart';
import 'package:flutter_helper/widgets/a032_card.dart';
import 'package:flutter_helper/widgets/a033_circleavator.dart';
import 'package:flutter_helper/widgets/a034_cpertinoactionsheet.dart';
import 'package:flutter_helper/widgets/a035_indicator.dart';
import 'package:flutter_helper/widgets/a036_cupertinocontextmenu.dart';
import 'package:flutter_helper/widgets/a037_cupertinodatepicker.dart';
import 'package:flutter_helper/widgets/a038_cupertinofullscrendialogtransitionr.dart';
import 'package:flutter_helper/widgets/a039_cupertinonavigationnar.dart';
import 'package:flutter_helper/widgets/a040_cupertinopicker.dart';
import 'package:flutter_helper/widgets/a041_scrollbar.dart';
import 'package:flutter_helper/widgets/a042_slider.dart';
import 'package:flutter_helper/widgets/a043_refreshindicator.dart';
import 'package:flutter_helper/widgets/a044_switch.dart';
import 'package:flutter_helper/widgets/a045_cupertinotab.dart';
import 'package:flutter_helper/widgets/a046_textfield.dart';
import 'package:flutter_helper/widgets/a047_cliprect.dart';
import 'package:flutter_helper/widgets/a048_custommultichildlayout.dart';
import 'package:flutter_helper/widgets/a050_custompaint.dart';
import 'package:flutter_helper/widgets/a051_customscrollview.dart';
import 'package:flutter_helper/widgets/a052_customsinglechildlayout.dart';
import 'package:flutter_helper/widgets/a053_datatable.dart';
import 'package:flutter_helper/widgets/a054_decoratedbox.dart';
import 'package:flutter_helper/widgets/a055decoratedboxtransition.dart';
import 'package:flutter_helper/widgets/a056_teststyle.dart';
import 'package:flutter_helper/widgets/a057_directionly.dart';
import 'package:flutter_helper/widgets/a058_widgets.dart';
import 'package:flutter_helper/widgets/a059_draggablescrollableactuator.dart';
import 'package:flutter_helper/widgets/a059_draggablescrollablesheet.dart';
import 'package:flutter_helper/widgets/a060_dropdownbuttonfromfield.dart';
import 'package:flutter_helper/widgets/a061_expansiontile.dart';
import 'package:flutter_helper/widgets/a062_flexiable.dart';
import 'package:flutter_helper/widgets/a063_fadetransition.dart';
import 'package:flutter_helper/widgets/a064_dialog.dart';
import 'package:flutter_helper/widgets/a065_wrap.dart';
import 'package:flutter_helper/widgets/a066_fittedbox.dart';
import 'package:flutter_helper/widgets/a067_flexible.dart';
import 'package:flutter_helper/widgets/a068_flexiblespacebar.dart';
import 'package:flutter_helper/widgets/a069_flow.dart';
import 'package:flutter_helper/widgets/a070_textfield.dart';
import 'package:flutter_helper/widgets/a071_futurebuilder.dart';
import 'package:flutter_helper/widgets/a072_gesturedetector.dart';
import 'package:flutter_helper/widgets/a073_hero.dart';
import 'package:flutter_helper/widgets/a074_intrinsicheight.dart';
import 'package:flutter_helper/widgets/a075_nestedscrollview.dart';
import 'package:flutter_helper/widgets/a076_notificationlistener.dart';
import 'package:flutter_helper/widgets/a077_pageview.dart';
import 'package:flutter_helper/widgets/a078_fractionallysizedbox.dart';
import 'package:flutter_helper/widgets/a078_media_query.dart';
import 'package:flutter_helper/widgets2/search_appbar.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';

class WidgetPage extends StatefulWidget {
  @override
  _WidgetPageState createState() => _WidgetPageState();
}

class _WidgetPageState extends State<WidgetPage> {
  var list = [
    MediaQueryApp(),
    HighLight(),
    AboutListTileApp(),
    AbsorbPointerApp(),
    ActionChipApp(),
    AlertDialogApp(),
    AlignApp(),
    AnimatedBuilderApp(),
    AnimatedContainerApp(),
    AnimatedCrossFadeApp(),
    AnimatedDefaultTextStyleApp(),
    AnimatedIconApp(),
    AnimatedListApp(),
    AnimatedModalBarrierApp(),
    AnimatedOpacityApp(),
    AnimatedPaddingApp(),
    AnimatedPhysicalModelApp(),
    AnimatedPositionedApp(),
    AnimatedPositionedDirectionalApp(),
    AnimatedSizeApp(),
    AnimatedSwitcherApp(),
    AppbarApp(),
    ButtonsApp(),
    BackdropFilterApp(),
    BannerApp(),
    BaselineApp(),
    BorderApp(),
    BottomAppBarApp(),
    BottomNavigationBarApp(),
    BuildApp(),
    CardApp(),
    CircleAvatorApp(),
    CupertinoActionSheetApp(),
    IndicatorApp(),
    CupertinoContextMenuApp(),
    CupertinoDatePickeruApp(),
    CupertinoFullscreenDialogTransitionApp(),
    CupertinoPageScaffoldApp(),
    CupertinoPickerApp(),
    ScrollbarApp(),
    SliderApp(),
    RefreshIndicatorApp(),
    SwitchApp(),
    CupertinoTabBarApp(),
    TextFieldApp(),
    ClipRectApp(),
    CustomMultiChildLayoutApp(),
    CustomPaintApp(),
    CustomScrollViewApp(),
    CustomSingleChildLayoutApp(),
    DataTableApp(),
    DecoratedBoxApp(),
    DecoratedBoxTransitionApp(),
    TextStyleApp(),
    DirectionlyApp(),
    Widgets2App(),
    ScraaggableScrollableActuatorApp(),
    DraggableScrollableSheetApp(),
    DropdownButtonFromFieldApp(),
    FlexiableApp(),
    ExpansionTileApp(),
    FadeTransitionApp(),
    DialogsApp(),
    WrapApp(),
    FittedBoxApp(),
    FlexibleApp(),
    FlexibleSpaceBarApp(),
    DemoFlowPopMenuApp(),
    DemoFlowCircelApp(),
    DemoFlowMenuApp(),
    TextField222App(),
    FutureBuilderApp(),
    GestureDetectorApp(),
    HeroApp(),
    IntrinsicHeightApp(),
    NestedScrollViewApp(),
    NotificationListenerApp(),
    PageViewApp(),
    FractionallySizedBoxApp(),
  ];

  var _listDisplay = null;

  @override
  Widget build(BuildContext context) => Scaffold(
        backgroundColor: Colors.white,
        primary: true,
        appBar: _searchAppBarWidget,
        body: SafeArea(child: buildList(context)),
      );

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
              // TODO _items.clear()
              LogUtil.e('onChangeCallBack -> $str');
              var tmp = list
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

  Widget buildList(BuildContext context) {
    if (_listDisplay == null) {
      list.sort((a, b) {
        var aName = a.toStringShort().toLowerCase();
        var bName = b.toStringShort().toLowerCase();
        return aName.compareTo(bName);
      });
      _listDisplay = list;
    }
    return _buildListView(_listDisplay);
  }

  _buildListView(_list) {
    return ListView.builder(
      // itemBuilder: (BuildContext context, int index) => buildSizeBox(context, index, _listDisplay[index]),
      itemBuilder: (BuildContext context, int index) => SizedBox(
        height: 30,
        child: Row(
          children: [
            Padding(
              padding: EdgeInsets.only(right: 10, bottom: 2),
              child: ElevatedButton(
                child: Text('IN'),
                onPressed: () {
                  LogUtil.d(_list[index]);
                  Navigator.push(context, MaterialPageRoute(builder: (context) {
                    return _list[index];
                  }));
                },
              ),
            ),
            SizedBox(
              width: 10,
            ),
            Expanded(child: Text(_list[index].toStringShort())),
            Padding(
              padding: EdgeInsets.only(right: 10, bottom: 2),
              child: ElevatedButton(
                child: Text('源'),
                onPressed: () {
                  LogUtil.d(_list[index]);
                  if (_list[index] is HighMixin) {
                    var h = (_list[index] as HighMixin).getHigh();
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) {
                      return HighLight(
                        title: h.title,
                        text: h.text,
                        outWidget: _list[index],
                      );
                    }));
                  } else {
                    showToast("未实现mixin HighMixin");
                  }
                },
              ),
            ),
          ],
        ),
      ),
      itemCount: _list.length,
    );
  }

  buildSizeBox(BuildContext context, int index, Widget app) => SizedBox(
        height: 30,
        child: Row(
          children: [
            Padding(
              padding: EdgeInsets.only(right: 10, bottom: 2),
              child: ElevatedButton(
                child: Text('IN'),
                onPressed: () {
                  LogUtil.d(app);
                  Navigator.push(context, MaterialPageRoute(builder: (context) {
                    return app;
                  }));
                },
              ),
            ),
            SizedBox(
              width: 10,
            ),
            Expanded(child: Text(app.toStringShort())),
            Padding(
              padding: EdgeInsets.only(right: 10, bottom: 2),
              child: ElevatedButton(
                child: Text('源'),
                onPressed: () {
                  LogUtil.d(app);
                  if (app is HighMixin) {
                    var h = (app as HighMixin).getHigh();
                    Navigator.push(context,
                        MaterialPageRoute(builder: (context) {
                      return HighLight(
                        title: h.title,
                        text: h.text,
                        outWidget: app,
                      );
                    }));
                  } else {
                    showToast("未实现mixin HighMixin");
                  }
                },
              ),
            ),
          ],
        ),
      );
}

class CardItem extends StatelessWidget {
  final Widget outWidget; // 从外面传进来的widget，要显示

  const CardItem({Key key, this.outWidget}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        showToast(outWidget.toStringShort());
      },
      child: Text(outWidget.toStringShort()),
    );
  }
}
