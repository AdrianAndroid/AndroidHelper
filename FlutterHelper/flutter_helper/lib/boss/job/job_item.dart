import 'package:flutter/material.dart';

import 'job.dart';

class JobItem extends StatelessWidget {
  final Job job;
  VoidCallback onPressed;

  JobItem({Key key, this.job, this.onPressed}) : super(key: key);

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
                Expanded(
                  child: Text(
                    job.title,
                    style: TextStyle(color: Colors.black, fontSize: 16),
                  ),
                ),
                Text(
                  job.salary,
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 16,
                  ),
                )
              ],
            ),
            Padding(
              padding: EdgeInsets.only(top: 8.0),
              child: Text(job.company),
            ),
            Container(
              decoration: BoxDecoration(
                color: Color(0xFFF6F6F8),
                borderRadius: BorderRadius.all(Radius.circular(6.0)),
              ),
              padding: EdgeInsets.fromLTRB(3.0, 3.0, 8.0, 8.0),
              child: Text(
                job.info,
                style: TextStyle(color: Color(0xFF9fa3b0)),
              ),
            ),
            Row(
              children: [
                CircleAvatar(
                  backgroundImage: NetworkImage('https://img.bosszhipin.com/beijin/mcs/useravatar/20171211/4d147d8bb3e2a3478e20b50ad614f4d02062e3aec7ce2519b427d24a3f300d68_s.jpg'),
                  radius: 15,
                ),
                Padding(
                  padding: EdgeInsets.only(left: 8.0),
                  child: Text(job.publish),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
