import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_helper/flutter_tags/lib/flutter_tags.dart';

import 'more_widgets.dart';

class SearchAppBarWidget extends StatefulWidget implements PreferredSizeWidget {
  final double height;
  final double elevation; //阴影
  final Widget leading;
  final String hintText;
  final FocusNode focusNode;
  final TextEditingController controller;
  final IconData prefixIcon;
  final List<TextInputFormatter> inputFormatters;
  final VoidCallback onEditingComplete;
  final OnChangedCallback onChangedCallback;

  const SearchAppBarWidget(
      {Key key,
      this.height: 46.0,
      this.elevation: 0.5,
      this.leading,
      this.hintText = '请输入关键词',
      this.focusNode,
      this.controller,
      this.prefixIcon = Icons.search,
      this.inputFormatters,
      this.onEditingComplete,
      this.onChangedCallback})
      : super(key: key);

  @override
  State<StatefulWidget> createState() => SearchAppBarState();

  @override
  Size get preferredSize => Size.fromHeight(height);
}

class SearchAppBarState extends State<SearchAppBarWidget> {
  bool _hasdeleteIocn = false;

  @override
  Widget build(BuildContext context) => PreferredSize(
        child: Stack(
          children: [
            Offstage(
              offstage: false,
              child: MoreWidgets.buildAppBar(
                context,
                '',
                elevation: 2.0,
                leading: widget.leading,
              ),
            ),
            Offstage(
              offstage: false,
              child: Container(
                padding: const EdgeInsets.only(left: 30.0, top: 26.0),
                child: TextField(
                  focusNode: widget.focusNode,
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  controller: widget.controller,
                  maxLines: 1,
                  inputFormatters: widget.inputFormatters,
                  decoration: _inputDecoration(),
                  onChanged: (str) {
                    if (null != widget.onChangedCallback) {
                      widget.onChangedCallback(str);
                    }
                    setState(() {
                      if (str.isEmpty) {
                        _hasdeleteIocn = false;
                      } else {
                        _hasdeleteIocn = true;
                      }
                    });
                  },
                  onEditingComplete: widget.onEditingComplete,
                ),
              ),
            ),
          ],
        ),
        preferredSize: Size.fromHeight(widget.height),
      );

  InputDecoration _inputDecoration() => InputDecoration(
        hintText: widget.hintText,
        hintStyle: TextStyle(color: Colors.black, fontSize: 16.5),
        prefixIcon: Padding(
          padding: EdgeInsetsDirectional.only(start: 24.0),
          child: Icon(widget.prefixIcon, color: Colors.black),
        ),
        suffixIcon: Padding(
          padding: EdgeInsetsDirectional.only(
            start: 2.0,
            end: _hasdeleteIocn ? 20.0 : 0,
          ),
          child: !_hasdeleteIocn
              ? Text('')
              : InkWell(
                  onTap: () {
                    if (null != widget.onChangedCallback) {
                      widget.onChangedCallback('');
                    }
                    setState(() {
                      widget.controller.text = '';
                      _hasdeleteIocn = false;
                    });
                  },
                  child: Icon(Icons.clear, size: 18.9, color: Colors.black),
                ),
        ),
        contentPadding: EdgeInsets.fromLTRB(0, 10, 0, 0),
        filled: true,
        fillColor: Colors.transparent,
        border: InputBorder.none,
      );
}
