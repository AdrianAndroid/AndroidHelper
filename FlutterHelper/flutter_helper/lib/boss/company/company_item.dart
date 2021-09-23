import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'company.dart';

class CompanyItem extends StatelessWidget {
  final Company company;
  final String heroLogo;
  VoidCallback onPressed;

  CompanyItem({Key key, this.company, this.heroLogo, this.onPressed})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onPressed,
      child: Container(
        margin: EdgeInsets.only(bottom: 10.0),
        padding: EdgeInsets.fromLTRB(18.0, 10.0, 18.0, 10.0),
        color: Colors.white,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          children: [
            Row(
              children: [
                Padding(
                  padding: EdgeInsets.only(right: 20.0),
                  child: Hero(
                    tag: heroLogo,
                    child: Image.network(
                      company.logo,
                      width: 40,
                    ),
                  ),
                ),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Padding(
                      padding: EdgeInsets.only(bottom: 6.0),
                      child: Text(
                        company.company,
                        style: TextStyle(
                          color: Colors.black,
                          fontSize: 16,
                        ),
                      ),
                    ),
                    Text(
                      company.info,
                      style: TextStyle(
                        color: Colors.grey,
                        fontSize: 12,
                      ),
                    )
                  ],
                ),
              ],
            ),
            Container(
              decoration: BoxDecoration(
                color: new Color(0xFFF6F6F8),
                borderRadius: BorderRadius.all(Radius.circular(6.0)),
              ),
              padding: EdgeInsets.fromLTRB(3.0, 3.0, 8.0, 8.0),
              margin: EdgeInsets.only(top: 12.0),
              child: Text(
                company.hot,
                style: TextStyle(color: Color(0xFF9fa3b0)),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
