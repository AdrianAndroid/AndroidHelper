import 'package:flutter/material.dart';
import 'package:flutter_helper/boss/company/company.dart';
import 'package:flutter_helper/boss/company/company_item.dart';

import 'company_page_detail.dart';

class CompanyPage extends StatefulWidget {
  @override
  _CompanyPageState createState() => _CompanyPageState();
}

class _CompanyPageState extends State<CompanyPage>
    with AutomaticKeepAliveClientMixin {
  Future<List<Company>> _fetchCompanyList() async {
    List<Company> companyList = [];
    for (int i = 0; i < 100; i++) {
      companyList.add(Company(
        id: "$i",
        company: "科大讯飞$i",
        logo:
            "https://img.bosszhipin.com/beijin/mcs/useravatar/20171211/4d147d8bb3e2a3478e20b50ad614f4d02062e3aec7ce2519b427d24a3f300d68_s.jpg",
        info: "已经上市移动互联网",
        hot: "热🔥招：高级测试工程师 15k-18k",
      ));
    }
    return companyList;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0.0,
        centerTitle: true,
        title: Text(
          '公 司',
          style: TextStyle(fontSize: 20.0, color: Colors.white),
        ),
        actions: [
          IconButton(
            onPressed: () {},
            icon: Icon(
              Icons.search,
              color: Colors.white,
            ),
          ),
        ],
      ),
      body: Center(
        child: FutureBuilder(
          future: _fetchCompanyList(),
          builder: (context, snapshot) {
            if (ConnectionState.none == snapshot.connectionState ||
                ConnectionState.waiting == snapshot.connectionState) {
              return CircularProgressIndicator();
            } else if (snapshot.hasError) {
              return Text('Error: ${snapshot.error}');
            } else {
              return _createListView(context, snapshot);
            }
          },
        ),
      ),
    );
  }

  @override
  bool get wantKeepAlive => true;

  Widget _createListView(
      BuildContext context, AsyncSnapshot<dynamic> snapshot) {
    List<Company> companyList = snapshot.data;
    return ListView.builder(
      key: new PageStorageKey('company-list'),
      itemCount: companyList.length,
      itemBuilder: (BuildContext context, int index) {
        return CompanyItem(
          onPressed: () {
            Navigator.push(
              context,
              MaterialPageRoute(
                // fullscreenDialog: true,
                builder: (context) => CompanyDetailPage(
                  company: companyList[index],
                  heroLogo: "heroLogo$index",
                ),
              ),
            );
          },
          company: companyList[index],
          heroLogo: "heroLogo${index}",
        );
      },
    );
  }
}
