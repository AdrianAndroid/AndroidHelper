package com.elbbbird.android.analytics;

public interface UmengEventId {
    public static String PK_VOTE = "PK_VOTE";   //PK页投票
    public static String PK_MATCH = "PK_MATCH"; //匹配

    public static String CLICK_COMMENT = "CLICK_COMMENT"; //评论
    public static String CLICK_PRAISE = "CLICK_PRAISE";     //喜欢
    public static String CLICK_SHARE = "CLICK_SHARE";     //分享
    public static String CLICK_USER_HEADER = "CLICK_USER_HEADER";     //用户头像

    public static String FRIEND_CHAT = "FRIEND_CHAT"; //聊天页进入

    public static String MATCH_LOGIN = "MATCH_LOGIN"; //匹配登录入口
    public static String OTHER_LOGIN = "OTHER_LOGIN"; //其他登录入口

    public static String NO_LOGIN_ENTRY = "NO_LOGIN_ENTRY"; //登录入口放弃
    public static String NO_LOGIN_SMS = "NO_LOGIN_SMS";     //短信界面放弃
    public static String NO_LOGIN_CHOOSE = "NO_LOGIN_CHOOSE"; //选择男女放弃
    public static String NO_LOGIN_EDIT = "NO_LOGIN_EDIT";   //编辑信息入口放弃

    public static String PUBLISH_FROM_TREND = "PUBLISH_FROM_TREND";    //动态发布
    public static String PUBLISH_FROM_H5 = "PUBLISH_FROM_H5";    // 从H5过来
    public static String PUBLISH_FROM_USER = "PUBLISH_FROM_USER";      //个人页发布

    // 入圈测试中--点击“测试中的任意选项”（答题次数）
    String CHANGELEGE_TEST_BEGIN = "CHANGELEGE_TEST_BEGIN";

    // 点击世界，进入话题热榜
    String WORLD = "WORLD"; // 感觉创建的时候就调用一次
    // 话题热榜页--点击某话题，进入话题二级页
    String HOT_INTO_TOPIC_DETAIL = "HOT_INTO_TOPIC_DETAIL";
    // 话题二级页--点击“制作发布”
    String TOPIC_DETAIL_INTO_PUBLISH = "TOPIC_DETAIL_INTO_PUBLISH";
    // 话题二级页--点击“入圈挑战”，进入测试
    String TOPIC_DETAIL_INTO_CHANLLEGE = "TOPIC_DETAIL_INTO_CHANLLEGE";

    // 入圈测试中--点击“查看结果“，进入测试结果页
    String CHANNLEGE_INTO_CHANNLEGE_RESULT = "CHANNLEGE_INTO_CHANNLEGE_RESULT";
    // 测试结果页--点击“右上角分享”次数
    String CHANNLEGE_RESULT_SHARE_TOPRIGHT = "CHANNLEGE_RESULT_SHARE_TOPRIGHT";
    // 测试结果页--点击“撩TA”次数
    String CHANNLEGE_RESULT_FLIRT = "CHANNLEGE_RESULT_FLIRT";
    // 测试结果页--点击“分享解锁”次数
    String CHANLLEGE_RESULT_SHARE_UNLOCK = "CHANLLEGE_RESULT_SHARE_UNLOCK";
    // 测试结果页--点击“考考别人”次数 -- 同 分享解锁
//    String CHANLLEGE_RESULT_TESTOTHER = "CHANLLEGE_RESULT_TESTOTHER";
    // 测试结果页--点击“重新挑战”次数
    String CHANLLEGE_RESULT_RECHANLLEGE = "CHANLLEGE_RESULT_RECHANLLEGE";
    // 测试结果页--点击“分享”--点击”分享图片”
    String CHANLLEGE_RESULT_PIC = "CHANLLEGE_RESULT_PIC";
    // 测试结果页--点击“分享”--点击”分享链接”
    String CHANLLEGE_RESULT_LINK = "CHANLLEGE_RESULT_LINK";
    // 测试结果页--截图次数
    String CHANLLEGE_RESULT_SCREENSHOT = "CHANLLEGE_SCREENSHOT";
}
