package cn.kuwo.pp.util;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import cn.kuwo.common.app.App;
import cn.kuwo.pp.http.bean.QuestionModel;

public class UIUtil {


    public static int dip2px(float dipValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static float px2Dip(int px) {
        float density = App.getInstance().getResources().getDisplayMetrics().density;
        return (float) ((px - 0.5) / density);
    }
}
