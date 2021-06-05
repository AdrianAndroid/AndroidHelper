package cn.kuwo.networker;

import android.text.TextUtils;
import cn.kuwo.networker.crypt.Base64Coder;
import cn.kuwo.networker.crypt.KuwoDES;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


/**
 * Created by shihc on 16/9/13.
 */
public class AccountUrlUtil {
    static byte[] URL_KEY;

    static {
        try {
            URL_KEY = "bulutest".getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String createUrl(String host, byte[] parameters) {
        return host + encryptParameters(parameters);
    }

    //voiceUid=1291&vid=1182&like=1&uid=2635&sid=73d7aee093f6b3eb1a5ad05f069ae897&sex=1&plat=android&from=ar&dev_name=HUAWEI&devType=BND-AL10&devResolution=1080x2160&version=2&src=kuwo
    public static String encryptJsonParams(String paramPairs){
        if(TextUtils.isEmpty(paramPairs)){
            return "";
        }
        //String paramStr = "a=a1&b=b1&c=c1";
        String[] params = paramPairs.split("&");
        JSONObject obj = new JSONObject();
        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");
            if (param.length >= 2) {
                String key = param[0];
                String value = param[1];
                for (int j = 2; j < param.length; j++) {
                    value += "=" + param[j];
                }
                try {
                    obj.put(key,value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        String jsonStr = obj.toString();
        try {
            byte[] jsonBytes = jsonStr.getBytes("utf-8");
            byte[] desValue = KuwoDES.encrypt2(jsonBytes,jsonBytes.length, URL_KEY,URL_KEY.length);
            return new String(cn.kuwo.networker.crypt.Base64Coder.encode(desValue,desValue.length));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptParameters(byte[] parameters) {
        byte[] encryptParameters = KuwoDES.encryptLogin(parameters, KuwoDES.PARAMETER_SECRET_KEY);
        return new String(Base64Coder.encode(encryptParameters, encryptParameters.length));
    }


    public static String decode(String data) {
        String str = "";
        try {
            // 防止服务器秘钥错误,解密失败
            byte[] b64Data = Base64Coder.decode(data);
            str = new String(KuwoDES.decryptLogin(b64Data, getSecretKey().getBytes())).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        return str;
    }

//    public static String encodeUsername(String username) {
//        if (TextUtils.isEmpty(username)) {
//            return "";
//        }
//        try {
//            return StringUtils.encodeUrl(username, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

    public static String encodePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return "";
        }
        return Base64Coder.encode(password.getBytes());
    }

    private static String secretKey = "";//动态私钥，用于接口请求的加解密

    public static String getSecretKey() {
        if (TextUtils.isEmpty(secretKey)) {
            String millis = String.valueOf(System.currentTimeMillis()) + "12345678";
            secretKey = millis.substring(0, 8);
        }
        return secretKey;
    }
}
