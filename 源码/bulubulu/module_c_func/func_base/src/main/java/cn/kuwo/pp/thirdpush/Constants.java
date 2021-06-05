package cn.kuwo.pp.thirdpush;

/**
 * Created by Administrator on 2018/7/2.
 */

public class Constants {

    public static final int SDKAPPID = 1400178783; // 替换成您在腾讯云控制台云通信的应用SDKAPPID，链接 https://console.cloud.tencent.com/avc
    public static final String DEFAULT_USER = "wwww";
    public static final String DEFAULT_SIG = "eJxlj11PgzAYhe-5FaS3M1oKjGHihWWGNYhzGfPjirDRzXcbBUsHm8b-LuISm3hun*fk5Hwapmmi5H5*ma1W5UGoVJ0qjsxrE2F08QerCvI0U6kt83*QHyuQPM3WisseWq7rEox1B3IuFKzhbLRdNFrnu7Sf*K07Xdch-ojoCmx6GN8tAjYL-Kn9tj14QULL29FVGH1Qa7l-fm9kEjVFNIenVnitOsX5eMY2D0CTOp5MlsphKhxuacQCf18-vgyOxUDScSCKEMdMvS52N9qkgoKf-1hD4vkWcTXacFlDKXqB4E4hNv4JMr6Mb*XyXRg_";

    // 获取usersig的业务服务器基本URL
    public static final String BUSIZ_SERVER_BASE_URL = "您自己获取userSig的业务服务器地址";

    // bugly上报
    public static final String BUGLY_APPID = "e3afc505b6";

    /****** 华为离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long HW_PUSH_BUZID = 6211;
    // 华为开发者联盟给应用分配的应用APPID
    public static final String HW_PUSH_APPID = "100844511"; // 见清单文件
    /****** 华为离线推送参数end ******/

    /****** 小米离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long XM_PUSH_BUZID = 6217;
    // 小米开放平台分配的应用APPID及APPKEY
    public static final String XM_PUSH_APPID = "2882303761518036313";
    public static final String XM_PUSH_APPKEY = "5461803667313";
    /****** 小米离线推送参数end ******/

    /****** 魅族离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long MZ_PUSH_BUZID = 6218;
    // 魅族开放平台分配的应用APPID及APPKEY
    public static final String MZ_PUSH_APPID = "121586";
    public static final String MZ_PUSH_APPKEY = "47e8f4a84ddb440d9f34e8403c92a50b";
    /****** 魅族离线推送参数end ******/

    /****** vivo离线推送参数start ******/
    public static final long VIVO_PUSH_BUZID = 6216;
    // vivo开放平台分配的应用APPID及APPKEY
    public static final String VIVO_PUSH_APPID = "13158"; // 见清单文件
    public static final String VIVO_PUSH_APPKEY = "43a203c1-a9b8-4943-9dcc-ad563f97aa62"; // 见清单文件
    /****** vivo离线推送参数end ******/

    // 存储
    public static final String USERINFO = "userInfo";
    public static final String ACCOUNT = "account";
    public static final String PWD = "password";
    public static final String ROOM = "room";
    public static final String AUTO_LOGIN = "auto_login";
    public static final String LOGOUT = "logout";

    public static final String CHAT_INFO = "chatInfo";

    public static final String OFFLINE_PUSH_KEY = "userId";
    public static final String SYSTEM_MESSAGE_CHAT_ID = "0";
}
