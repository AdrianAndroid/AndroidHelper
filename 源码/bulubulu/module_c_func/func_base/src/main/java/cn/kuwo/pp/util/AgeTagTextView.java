package cn.kuwo.pp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import cn.kuwo.common.util.L;
import cn.kuwo.pp.R;

/**
 * @author shihc
 */
public class AgeTagTextView extends IconFountTextView {
    private String sex;

    public AgeTagTextView(Context context) {
        this(context, null);
    }

    public AgeTagTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AgeTagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context context) {
        super.init(context);setCompoundDrawablePadding(5);
    }

    @SuppressLint("SetTextI18n")
    public void setSexAndAge(String sex, String age) {
        L.m3("sex=", sex, "aget=", age);
        this.sex = sex;
        if (age == null || "0".equals(age)) {
            age = "";
        }
        if ("2".equals(sex)) { // 女
//            setIconText("<font color=#f68478>&#xe6d8;</font><font color=#ffffff>" + age + "</font>");
            setLeftImage(false);
        } else if ("1".equals(sex)) { // nan
//            setIconText("<font color=#08ffec>&#xe6d7;</font><font color=#ffffff>" + age + "</font>");
            setLeftImage(true);
        } else {
            setIconText(age);
            setTextColor(Color.parseColor("#FFFFFFFF"));
        }
        super.setText("" + age);
        setBackgroundResource(R.drawable.tag_sex_man_rounded_placeholder);
    }

//    public void setViewInfo(String sex, String age){
//        L.m3("sex=", sex, "aget=", age);
//        this.sex = sex;
//        if (age == null || "0".equals(age)) {
//            age = "";
//        }
//        if ("2".equals(sex)) {
//            setIconText("&#xe6d8;" + age);
//            //setTextColor(Color.parseColor("#FFFFA4A4"));
//        } else if ("1".equals(sex)) {
//            setIconText("&#xe6d7;" + age);
//            //setTextColor(Color.parseColor("#FF6ED2D5"));
//        } else {
//            setIconText("&#xe6d8;" + age);
//            //setTextColor(Color.parseColor("#FF6ED2D5"));
//        }
//        setBackgroundResource(R.drawable.tag_sex_man_rounded_placeholder);
//    }

    private void setLeftImage(boolean nan) {
        Drawable img;
        if (nan) {
            img = ContextCompat.getDrawable(getContext(), R.drawable.ic_nan);
        } else {
            img = ContextCompat.getDrawable(getContext(), R.drawable.ic_nv);
        }
        assert img != null;
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        setCompoundDrawables(img, null, null, null);
    }


}
