package cn.kuwo.daynight;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by shihc on 2016/11/28.
 */

public class TypefaceUtil {

    public static final String DIN_Condensed_Bold_2 = "fonts/DIN-Condensed-Bold-2.ttf";
    public static final String iconfont = "fonts/iconfont.ttf";
    public static final String Akrobat_kuwo_Bold = "fonts/Akrobat-kuwo-Bold.ttf";
    public static final String Akrobat_kuwo_Regular = "fonts/Akrobat-kuwo-Regular.ttf";


//    private static Typeface typeface;

//    public static Typeface getTypeface() {
//        try {
//            return Typeface.createFromAsset(App.getInstance().getAssets(), iconfont);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    // 设置自定义字体
    public static void setTypeface(TextView textView, String font) {
        if (null != textView && !TextUtils.isEmpty(font)) {
            Typeface fromAsset = Typeface.createFromAsset(textView.getContext().getAssets(), font);
            textView.setTypeface(fromAsset);
        }
    }

//    public static Typeface getTypeface(Context context) {
//        if (typeface != null) {
//            return typeface;
//        }
//        try {
//            typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), iconfont);
//            return typeface;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
