package cn.kuwo.pp.util;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;

import cn.kuwo.daynight.TypefaceUtil;


/**
 * 自动加载iconfount控件
 * @author shihc
 * @date 2016/11/2
 */

public class IconFountTextView extends androidx.appcompat.widget.AppCompatTextView {
    public IconFountTextView(Context context) {
        super(context);
        init(context);
    }

    public IconFountTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconFountTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        TypefaceUtil.setTypeface(this, TypefaceUtil.iconfont);
        if (!isEmpty(getText())) {
            setIconText(getText().toString());
        }
    }

    public void setIconText(String text) {
        if (isEmpty(text)) {
            return;
        }
        if (!text.contains("&#x")) {
            text = "&#x" + text;
        }
        super.setText(Html.fromHtml(text));
    }

    private boolean isEmpty(CharSequence str) {
        if (str != null && str.length() > 0) {
            return false;
        }
        return true;
    }
}
