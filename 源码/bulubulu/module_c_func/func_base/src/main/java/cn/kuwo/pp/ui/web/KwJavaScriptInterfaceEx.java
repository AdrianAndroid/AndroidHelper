package cn.kuwo.pp.ui.web;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kuwo.common.app.App;
import cn.kuwo.common.util.RxUtilKt;
import cn.kuwo.common.util.SP;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.pp.http.bean.WebQuestionModelBean;
import cn.kuwo.pp.http.bean.user.UserInfo;
import cn.kuwo.pp.manager.UserInfoManager;

public class KwJavaScriptInterfaceEx {
    private final static String TAG = "KwJavaScriptInterfaceEx";
    public static final String httpStatusCodeJS = "javascript:try{window.KuwoInterface.webSource(document.getElementsByTagName('h1')[0].innerHTML);}catch(e){}";

    final KwWebWindowInterface mWindowInterface;
    private String mPsrc;
    private boolean isNotifyPlayState;
    private String devicecInfoCallBack;
    //<actionName,callBackName>
    public Map<String, String> actionCallBackMap = new HashMap<>();

    public KwJavaScriptInterfaceEx(KwWebWindowInterface mWindowInterface) {
        this.mWindowInterface = mWindowInterface;
//        MsgMgr.attachMessage(MsgID.OBSERVER_WEB_HEADER, webPicMgrObserver);
    }

    public void release() {
//        MsgMgr.detachMessage(MsgID.OBSERVER_WEB_HEADER, webPicMgrObserver);
    }

    public void setPsrc(String psrc) {
        mPsrc = psrc;
    }

    @JavascriptInterface
    public void jsCallNative(String json) {
        JSONObject job = null;
        try {
            job = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

        if (job != null) {
            processJsonOnUIThread(job);
        }
    }

    private void processJsonOnUIThread(JSONObject json) {
        final JSONObject objson = json;
        RxUtilKt.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                final String action = objson.optString("action");
                if (JsConstant.JS_ACTION_IMPORT_SONG_LIST.equalsIgnoreCase(action)) {
                    //网易云音乐导入
//                    if (UserInfoManager.getInstance().isLogin()==false) {
////                        LoginJumperUtils.jumpToLoginWithToast(LoginStatisticsUtils.LOGIN_FROM_DOWN_ALL, cn.kuwo.player.R.string.login_to_opt);
//                        return;
//                    }
                    parseImportSongList(json);
                } else if (JsConstant.JS_ACTION_PAY_GETUESRINFO.equals(action)) {
                    try {
                        String callBack = objson.optString("callback");
                        nativeCallJavascript(callBack, getUserInfo());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (JsConstant.JS_ACTION_GET_CLIPBOARD_CONTENT.equalsIgnoreCase(action)) {
                    //获取粘贴板信息
                    String callback = json.optString("callback");
                    if (!TextUtils.isEmpty(callback)) {
                        ClipboardManager cbm = null;
                        try {
                            cbm = (ClipboardManager) mWindowInterface.getActivity_WebWindow().
                                    getSystemService(Context.CLIPBOARD_SERVICE);
                        } catch (Exception e) {
                            return;
                        }
                        if (cbm != null) {
                            CharSequence text = cbm.getText();
                            if (!TextUtils.isEmpty(text)) {
                                nativeCallJavascript(callback, text.toString());
                            }
                        }
                    }
                } else if (JsConstant.JS_ACTION_SEND_PLAYLIST_LINK.equalsIgnoreCase(action)) {
                    //获取到网易云歌单实际链接，然后拿到html源文件，发给服务端
                    String url = json.optString("url");//.replace("https","").replace("http","");
                    String callback = json.optString("callback");
                    if (!TextUtils.isEmpty(url)) {
                        getHtmlSource(url, callback);
                    }
                } else if (JsConstant.JS_ACTION_GOTO_LOGIN_PAGE.equals(action)) {
                    UtilsCode.INSTANCE.showShort("登录后才能导入哦");
                } else if (JsConstant.JS_ACTION_IDENTIFY_DIMENSIONAL_CODE.equalsIgnoreCase(action)) {
                    ChooseImageUtil uploadIcon = new ChooseImageUtil(ChooseImageUtil.QRCODE);
                    actionCallBackMap.put(action, objson.optString("callback"));
                    uploadIcon.openGallery();
                } else if (JsConstant.JS_ACTION_PASS_SPECIAL_HEADERS.equalsIgnoreCase(action)) {
//                    ImportSongListJsInterface.callbackHttpRequest(json, new ImportSongListCallback() {
//                        @Override
//                        public void onSuccess(String scriptName, String data) {
//                            nativeCallJavascript(scriptName, data);
//                        }
//                    }, mWindowInterface);
                } else if (JsConstant.JS_ACTION_SEND_TASK_FINISH_LINK.equals(action)) {
//                    EventBus.getDefault().post(new WebCallBackEvent("task_Finish"));
                } else if (JsConstant.JS_ACTION_CONTROL_INAPP_URL.equals(action)) {
                    openNewWebPage(objson);
                } else if (JsConstant.JS_ACTION_CONTROL_GET_DEVICEINFO.equals(action)) {
                    String strret = get_dev_info();
                    try {
                        isNotifyPlayState = objson.optBoolean("notify_play_state");
                        devicecInfoCallBack = objson.optString("callback");
                        if (!TextUtils.isEmpty(devicecInfoCallBack)) {
                            nativeCallJavascript(devicecInfoCallBack, strret);
                        } else {
                            nativeCallJavascript("feedback_ardeviceinfo", strret);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (JsConstant.JS_ACTION_CHALLENGE_RESULT.equals(action)) {
                    try {
                        String callBack = objson.optString("callback");
                        nativeCallJavascript(callBack, getChallengeResult());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (JsConstant.JS_ACTION_WEB_CMD.equals(action)) {
                    webCommand(objson);
                } else if (JsConstant.JS_ACTION_PAY_GETCLIENTINFO.equals(action)) {
                    try {
                        String callBack = objson.optString("callback");
                        nativeCallJavascript(callBack, getClientInfo());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String getUserInfo() {
        UserInfo userInfo = UserInfoManager.INSTANCE.getUserInfo();
        if (userInfo == null) {
            return "";
        }
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("username", userInfo.getName());
            String name = userInfo.getName();
            if (name != null) {
                name = name.replace("\"", "\\\"");
            }
            jsonObj.put("nikename", name);
            jsonObj.put("pic", userInfo.getHeadImg());
            jsonObj.put("uid", userInfo.getUid());
            jsonObj.put("sid", UserInfoManager.INSTANCE.getToken());
            jsonObj.put("platform", "ar");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return jsonObj.toString();
    }

    // 可以在创建的时候创建
    public String getClientInfo() throws JSONException {
//        Activity activity = mWindowInterface.getActivity_WebWindow();
//        View view = mWindowInterface.getWebView_WebWindow();
//        ViewGroup.MarginLayoutParams viewLayout = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
//        int topMargin = ImmersionBar.getActionBarHeight(activity);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("keyStatusBarOffset", "50");
        return jsonObj.toString();
    }

    private String getChallengeResult() {
        Integer score = 0;
        if (SP.contains("challengeResult")) {
            String result = SP.decodeString("challengeResult");
            Gson gson = new Gson();
            Type jsonType = new TypeToken<List<WebQuestionModelBean>>() {
            }.getType();
            List<WebQuestionModelBean> challengeResultList = gson.fromJson(result, jsonType);
            for (WebQuestionModelBean item : challengeResultList) {
                if (item.getA().isChoosed() && item.getA().getPercent() > item.getB().getPercent()) {
                    score++;
                } else if (item.getB().isChoosed() && item.getB().getPercent() > item.getA().getPercent()) {
                    score++;
                }
            }
            HashMap<String, Object> resultList = new HashMap<>();
            resultList.put("list", challengeResultList);
            resultList.put("score", score);
            return gson.toJson(resultList);
        } else {
            return "";
        }
    }

    void webCommand(JSONObject json) {
        String cmd = json.optString("cmd");
        mWindowInterface.webCommand_WebWindow(cmd);
    }

    void openNewWebPage(JSONObject json) {
        final String url = json.optString("url");
        final String title = json.optString("title");
        final String openpagetype = json.optString("pagetype");
        String page_function = "";
        if (json.has("page_function")) {
            page_function = json.optString("page_function");
        }
        if (openpagetype != null && openpagetype.equals("no_title")) {
            mWindowInterface.openNewWebFragment(url, "", mPsrc);
        } else {
            mWindowInterface.openNewWebFragment(url, title, mPsrc);
        }

    }

    // 网页中脚本调用：window.KuwoInterface.jsCallNative(json);
    @JavascriptInterface
    public String get_dev_info() {
        final HashMap<String, String> deviceInfo = new HashMap<String, String>();
        deviceInfo.put("dev_id", UserInfoManager.INSTANCE.getUuid());
        deviceInfo.put("version", UtilsCode.INSTANCE.getAppVersionCode() + "");
        deviceInfo.put("supportSuper", "2");//支持豪华vip
        deviceInfo.put("version_name", UtilsCode.INSTANCE.getAppVersionName());
        deviceInfo.put("source", App.getInstance().appGetChannel());
        deviceInfo.put("dev_name", Build.MODEL);
        deviceInfo.put("sys_version", String.valueOf(Build.VERSION.SDK_INT));
        deviceInfo.put("device_uid", UserInfoManager.INSTANCE.getUuid());
        String nName = UserInfoManager.INSTANCE.getUserInfo() == null ? "" : UserInfoManager.INSTANCE.getUserInfo().getName();
        String disName = nName;
        if (TextUtils.isEmpty(disName)) {
            disName = "";
        }
        //disName 是酷我账号和三方登录账号优化后的值
        disName = disName.replace("\"", "\\\"");
        deviceInfo.put("uname", disName);
        deviceInfo.put("uid", UserInfoManager.INSTANCE.getUid());
        deviceInfo.put("sid", UserInfoManager.INSTANCE.getUuid());
        try {
            JSONObject info = new JSONObject(deviceInfo);
            return info.toString();
        } catch (Exception e) {
            return "{}";
        }
    }

    private void getHtmlSource(final String url, final String callback) {
//        RetrofitClient.getInstance().executeWithoutBaseBean(RetrofitClient.getApiService().getCloudMusicHtml(url),
//                new CustomObserver<ResponseBody>() {
//                    @Override
//                    public void onNext(ResponseBody body) {
//                        try {
//                            final String htmlStr = new String(body.bytes(),"utf-8");
//                            if(TextUtils.isEmpty(htmlStr)){
//                                return;
//                            }
//                            RequestBody pageBody = RequestBody.create(MediaType.parse("application/octet-stream"),htmlStr);
//                            RetrofitClient.getInstance().executeWithoutBaseBean(RetrofitClient.getApiService().uploadCloudMusicHtmlToServer(pageBody),
//                                    new CustomObserver<ResponseBody>() {
//                                        @Override
//                                        public void onNext(ResponseBody body1) {
//                                            try {
//                                                final String retStr = new String(body1.bytes(),"utf-8");
//                                                JSONObject json = new JSONObject(retStr);
//                                                String result = json.optString("result");
//                                                if ("ok".equals(result)) {
//                                                    String sheetname = json.optString("title");
//                                                    JSONArray sheetarr = json.getJSONArray("songs");
//                                                    if (sheetarr != null && sheetarr.length() > 0) {
//                                                        ArrayList<Music> musicList = new ArrayList<>();
//                                                        for (int i = 0; i < sheetarr.length(); i++) {
//                                                            JSONObject jsonObject = sheetarr.getJSONObject(i);
//                                                            Music music = new Music();
//                                                            music.setMid(jsonObject.optLong("id"));
//                                                            music.setName(jsonObject.optString("name"));
//                                                            music.setAlbum(jsonObject.optString("album"));
//                                                            music.setArtist(jsonObject.optString("artist"));
//                                                            music.setMinfo(jsonObject.optString("minfo"));
//                                                            musicList.add(music);
//                                                        }
//                                                        mWindowInterface.openMusicListFragment(sheetname, musicList);
//                                                    }
//                                                } else {
//                                                    nativeCallJavascript(callback, retStr);
//                                                }
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void _onError(ApiException e) {
//
//                                        }
//                                    });
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return;
//                        }
//                    }
//
//                    @Override
//                    public void _onError(ApiException e) {
//                        e.printStackTrace();
//                        mWindowInterface.onWebError_WebWindow();
//                    }
//                });

//        KwThreadPool.runThread(KwThreadPool.JobType.NET, new Runner() {
//            @Override
//            public void call() {
//                HttpSession httpSession = new HttpSession();
//                httpSession.setRequestHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
//                String html = httpSession.getString(url, "utf-8");
//                if (TextUtils.isEmpty(html)) {
//                    return;
//                }
//                String postParams = "page=" + html;
//                HttpSession getMusicSession = new HttpSession();
//                HttpResult postResult = getMusicSession.post(cloudMusicListUrl, postParams.getBytes());
//                if (postResult != null && postResult.isOk() && postResult.data != null) {
//                    String s = postResult.dataToString();
//                    nativeCallJavascript(callback, s);
//                }
//            }
//
//        });
    }

    private void parseImportSongList(JSONObject json) {

    }

    //native 调用网页
    public void nativeCallJavascript(String scriptName, String parameter) {
        if (TextUtils.isEmpty(scriptName)) {
            return;
        }

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("javascript:").append(scriptName);

            if (parameter == null) {
                sb.append("()");
            } else if ("".equals(parameter)) {
                sb.append("('')");
            } else {
                parameter = parameter.replaceAll("'", "&#39;");
                sb.append("('").append(parameter).append("')");
            }

            if (mWindowInterface != null && mWindowInterface.getWebView_WebWindow() != null) {
                mWindowInterface.getWebView_WebWindow().loadUrl(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //native 调用网页
    public void nativeCallJavascriptEvent(String scriptName, String data) {
        if (TextUtils.isEmpty(scriptName)) {
            return;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String jsCode = "var event = document.createEvent('CustomEvent');";
            jsCode += "event.data = '" + data + "';";
            jsCode += "event.initEvent('" + scriptName + "', false, false);";
            jsCode += "document.dispatchEvent(event);";
            sb.append("javascript:").append(jsCode);
            if (mWindowInterface != null && mWindowInterface.getWebView_WebWindow() != null) {
                mWindowInterface.getWebView_WebWindow().loadUrl(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    IWebPicMgrObserver webPicMgrObserver = (success, map, from) -> {
//        if (from == ChooseImageUtil.WEB_QRCODE) {
//            String callback = actionCallBackMap.get(JsConstant.JS_ACTION_IDENTIFY_DIMENSIONAL_CODE);
//            if (!TextUtils.isEmpty(callback)) {
//                ImportSongListJsInterface.callbackIdentifyQRCode(callback, map, new ImportSongListCallback() {
//                    @Override
//                    public void onSuccess(String scriptName, String data) {
//                        nativeCallJavascript(scriptName, data);
//                    }
//                });
//            }
//        }
//    };
}
