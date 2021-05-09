package cn.kuwo.pp.thirdpush;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huawei.hms.support.api.push.PushReceiver;

public class HWNotifyReceiver extends PushReceiver {
    private static final String TAG = "HWNotifyReceiver";

    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        String pushMsg = (String) extras.get("pushMsg");
        Log.i(TAG, "onEvent: "+pushMsg);
        try {
            JsonArray jsonArray = new JsonParser().parse(pushMsg).getAsJsonArray();
            if(jsonArray.size() > 0){
                JsonElement jsonElement = jsonArray.get(0);
                if(jsonElement.isJsonObject()){
                    JsonObject object = jsonElement.getAsJsonObject();
                    String ext = object.get("ext").getAsString();
                    MessageReceiverHandler.handlerMessage(context, ext);
                }
            }
        } catch(Exception e) {
            MessageReceiverHandler.handlerMessage(context, null);
        }
    }
}
