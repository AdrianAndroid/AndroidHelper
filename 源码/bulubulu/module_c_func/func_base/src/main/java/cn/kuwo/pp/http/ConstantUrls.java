package cn.kuwo.pp.http;

/**
 * 用来存放所有的URL请求前缀
 *
 * @author shihc
 */
public interface ConstantUrls {
//    boolean IS_DEBUG = false;


//    String URL_DEBUG = "http://test-bulu-api.kuwo.cn/";
//    String URL_RELEASE = "http://bulu-api.kuwo.cn/";


    //    String API_HOST = "";//IS_DEBUG ? Urls.URL_DEBUG : Urls.URL_RELEASE;
//    String API_USER_HOST = "";//IS_DEBUG ? Urls.URL_DEBUG : Urls.URL_RELEASE;
//    String API_BULU_URL = "";//IS_DEBUG ? Urls.URL_DEBUG : Urls.URL_RELEASE;
//    String API_IM_HOST = "https://console.tim.qq.com/";

    //配置接口
//    String URL_CONFIG = API_CONFIG_HOST + "api/conf/getconf?token=201809191653418954199400&plat=2";

//    String HEADER_DEBUG = Urls.KEY_BASEURL + ":" + Urls.URL_DEBUG; // DEBUG环境的Header
//    String HEADER_RELEASE = Urls.KEY_BASEURL + ":" + Urls.URL_RELEASE; // RELEASE换季ing的Header

    //=================================================登录相关接口===========================================================
    String USER_GET_SIG = "api/bulu/user/userSig";
    String USER_SEND_SMS = "api/ucenter/code/sendsms/{mobile}";
    String USER_LOGIN = "api/ucenter/user/login";
    String USER_BIND = "api/ucenter/user/bind/{uid}";
    String USER_UPDATE_PROFILE = "api/ucenter/user/{uid}";
    String USER_UPLOAD_PIC = "api/ucenter/user/upload/{uid}";
    String USER_MINE_TREND_LIST = "api/bulu/timeline/mine"; // 获取用户详情
    String USER_OTHER_TREND_LIST = "api/bulu/timeline/other";
    String USER_TREND_DELETE = "api/bulu/timeline/delete";

    //=================================================视频相关接口===========================================================

    String URL_GET_QINIU_TOKEN = "api/bulu/qiniu/key";
    String URL_GET_LIKE_URL = "api/bulu/voice/like";
    String URL_GET_RECOMMEND_VOICES = "api/bulu/voice/getvoice";
    String URL_POST_JUDGE_VOICE = "api/bulu/voice/upload"; //http://60.28.210.74:10009/api/bulu/voice/upload
    String URL_GET_USER_DETIAL = "api/bulu/user/info";
    String URL_GET_PLAY_VOICE = "api/bulu/voice/play";
    String URL_GET_GUIDE_LIST = "api/bulu/voice/guide";
    String URL_GET_SAVE_VOICE = "api/bulu/voice/confirm";
    String URL_CONTACT_PHONE = "api/bulu/contact/phone/{uid}";

    // 获取话题下题目 uid topicid pn rn
    String URL_topic_quesions = "api/bulu/topic/questions";
    String URL_topic_answer = "api/bulu/topic/answer";

    String URL_GET_QUESTION_LIST = "api/bulu/question/data";
    String URL_GET_QUESTION_DETAIL = "api/bulu/question/id"; // 获取题目信息
    String URL_GET_QUESTION_ANSWER = "api/bulu/question/answer";
    String URL_GET_QUESTION_PASS = "api/bulu/question/pass"; // 肯定是跳过这道题
    String URL_GET_QUESTION_LIMIT = "api/bulu/question/limit";
    String URL_GET_QUESTION_SYNC = "api/bulu/question/sync";
    String URL_GET_HOT_TREND_LIST = "api/bulu/timeline/recommend"; // 热门动态
    String URL_GET_FAVOR_TREND_LIST = "api/bulu/timeline/follow";
    String URL_GET_TOPIC_HOT_TREND_LIST = "api/bulu/topic/thot";
    String URL_GET_TOPIC_LEAST_TREND_LIST = "api/bulu/topic/timeline"; //最新话题动态列表
    String URL_GET_LATEST_TREND_LIST = "api/bulu/timeline/latest";
    String URL_POST_BUILD_QUESTION = "api/bulu/question/add";
    String URL_POST_USER_TREND = "api/bulu/timeline/publish";
    String URL_GET_MATCH_USERS = "api/bulu/question/match";
    String URL_GET_LIKE_TREND = "api/bulu/timeline/like";
    String URL_GET_UNLIKE_TREND = "api/bulu/timeline/unlike";
    String URL_GET_CHAT_QUESTION = "api/bulu/question/chat"; //获取题目

    String URL_POST_COMMENT_ADD = "api/comments/{source}/{sourceId}/add";
    String URL_POST_COMMENT_FETCH = "api/comments/{source}/{sourceId}/new";
    String URL_POST_COMMENT_LIKE = "api/comments/{source}/{sourceId}/like";
    String URL_POST_COMMENT_UNLIKE = "api/comments/{source}/{sourceId}/unlike";

    String URL_POST_REPORT_ACTION = "api/bulu/data/report";//用户行为数据上报

    //=================================================举报，分享接口===================================================
    String URL_GET_REPORT = "api/bulu/report";
    String URL_POST_REPORT_CHAT = "api/bulu/report/chat";  //聊天举报接口
    //=================================================im相关接口===========================================================
//    String IM_ADD_FRIEND = API_IM_HOST + "v4/sns/friend_add";
    String URL_GET_APPUID = "api/bulu/tools/appuid"; //生成appuid接口

    // 好友关系，原谅这些接口名吧
    String URL_FRIEND_STATUS = "api/bulu/relation/status";
    String URL_FRIEND_FOLLOW = "api/bulu/relation/follow";
    String URL_FRIEND_CANCEL = "api/bulu/relation/cancel";
    String URL_FRIEND_FOLLOWED = "api/bulu/relation/followed";
    String URL_FRIEND_STRANGER_LIST = "api/bulu/relation/stranger/list";
    String URL_FRIEND_STRANGER_ADD = "api/bulu/relation/stranger/add"; //添加邂逅
    String URL_FRIEND_STRANGER_DEL = "api/bulu/relation/stranger/del";
    String URL_FRIEND_MINE = "api/bulu/relation/mine";
    String URL_FRIEND_BOTH = "api/bulu/relation/both";
    String URL_MATCH_VALUE = "api/bulu/user/matchu"; //获取匹配度接口
    String URL_USER_ONLINE = "api/bulu/user/status";//返回的用户状态，目前支持的状态有：
    String URL_USER_COMMON = "api/bulu/user/common";

    String URL_POINTS_INCR = "api/bulu/points/incr";
    String URL_POINTS_INCR_RECORD = "api/bulu/points/incrrec";
    String URL_POINTS_USE = "api/bulu/points/use";
    String URL_POINTS_USE_RECORD = "api/bulu/points/userec";

    //系统消息
    String URL_SYSTEM_MESSAGE_ALL = "api/bulu/message/all"; //获取消息
    String URL_SYSTEM_MESSAGE_REPLY = "api/bulu/message/reply"; //回复消息

    //话题
    String URL_GET_TOPIC_HOT_LIST = "api/bulu/topic/hot";
    String URL_POST_ADD_TOPIC = "api/bulu/topic/add";
    String URL_POST_SEARCH_TOPIC = "api/bulu/topic/search";
    String URL_GET_TOPIC_SUB_LIST = "api/bulu/topic/sublist";
    String URL_POST_SUB_TOPIC = "api/bulu/topic/sub";
    String URL_POST_Cancel_SUB_TOPIC = "api/bulu/topic/cansub";
    String URL_GET_TOPIC_DETAIL = "api/bulu/topic/data";

    String URL_CHALLENGE_H5 = "https://h5app.kuwo.cn/m/meetChallenge/index.html";
    String URL_USER_AGREEMENT = "https://h5app.kuwo.cn/m/3dab9c3a/server.html";
}
