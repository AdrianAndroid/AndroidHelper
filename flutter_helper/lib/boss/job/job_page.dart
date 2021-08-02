import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/job/job_item.dart';
import 'package:flutter_helper/boss/utils.dart';

import 'job.dart';

class JobPage extends StatefulWidget {
  @override
  _JobPageState createState() => _JobPageState();
}

class _JobPageState extends State<JobPage> with AutomaticKeepAliveClientMixin {
  List<Job> listJobs = [];

  Future<List<Job>> _fetchJobList() async {
    for (int i = 0; i < 100; i++) {
      listJobs.add(Job(
          id: "id",
          title: "开发工程师",
          salary: "10K-20K",
          company: "EYE公司",
          info: "Stemcor 於 1951 年在倫敦成立，乃國際化的鋼材貿易商、分銷商和儲存商。 作為私營公司，我們是獨立的貿易商，表示本公司不屬任何鋼鐵生產商所有，也不控制任何鋼鐵生產商。。",
          category: "category",
          head: "http://img0.baidu.com/it/u=3311900507,1448170316&fm=26&fmt=auto&gp=0.jpg",
          publish: "发布于12月31日"));
    }
    // sleep(Duration(milliseconds: 3000));
    return listJobs;
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Scaffold(
        appBar: AppBar(
          elevation: 0.0,
          centerTitle: true,
          title: Text(
            '职位',
            style: TextStyle(
              fontSize: 20.0,
              color: Colors.white,
            ),
          ),
        ),
        body: Center(
          child: FutureBuilder(
            future: _fetchJobList(),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.none ||
                  snapshot.connectionState == ConnectionState.waiting) {
                return CircularProgressIndicator();
              } else if (snapshot.hasError) {
                return Text('Error: ${snapshot.error}');
              } else {
                return _createListView(context, snapshot);
              }
            },
          ),
        ),
      ),
    );
  }

  Widget _createListView(BuildContext context, AsyncSnapshot snapshot) {
    List<Job> jobList = snapshot.data;
    return ListView.builder(
      key: PageStorageKey('job-list'),
      itemCount: jobList.length,
      itemBuilder: (BuildContext context, int index) {
        return JobItem(
          onPressed: () {
            showToast(jobList[index].toString());
          },
          job: jobList[index],
        );
      },
    );
  }

  @override
  bool get wantKeepAlive => true;
}
