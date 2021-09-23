class TravelTabModel {
  Map params;
  String url;
  List<TravelTab> tabs;

  TravelTabModel({this.url, this.tabs});

  TravelTabModel.fromJson(Map<String, dynamic> json) {
    url = json['url'];
    params = json['params'];
    List __tabs = json['tabs'];
    tabs = [];
    __tabs?.forEach((v) {
      tabs.add(TravelTab.fromJson(v));
    });
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['url'] = this.url;
    if (this.tabs != null) {
      data['tabs'] = this.tabs.map((e) => e.toJson()).toList();
    }
    return data;
  }
}

class TravelTab {
  String labelName;
  String groupChannelCode;


  TravelTab({this.labelName, this.groupChannelCode});

  TravelTab.fromJson(Map<String, dynamic> json) {
    labelName  = json['labelName'];
    groupChannelCode = json['groupChannelCode'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['labelName'] = labelName;
    data['groupChannelCode'] = groupChannelCode;
    return data;
  }
}
