import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/utils.dart';
import 'package:flutter_helper/utils/util_log.dart';

class MenuItem extends StatelessWidget {
  final IconData icon;
  final String title;
  final VoidCallback onPressed;

  const MenuItem({Key key, this.icon, this.title, this.onPressed})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        LogUtil.e('$title');
        showToast(this.title);
        onPressed();
      },
      child: Column(
        children: [
          Padding(
            padding: EdgeInsets.fromLTRB(20.0, 12.0, 20.0, 10.0),
            child: Row(
              children: [
                Padding(
                  padding: EdgeInsets.only(right: 8.0),
                  child: Icon(icon, color: Colors.black54),
                ),
                Expanded(
                  child: Text(
                    title,
                    style: TextStyle(
                      color: Colors.black54,
                      fontSize: 16.0,
                    ),
                  ),
                ),
                Icon(Icons.chevron_right, color: Colors.grey),
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(left: 20.0, right: 20.0),
            child: Divider(),
          )
        ],
      ),
    );
  }
}
