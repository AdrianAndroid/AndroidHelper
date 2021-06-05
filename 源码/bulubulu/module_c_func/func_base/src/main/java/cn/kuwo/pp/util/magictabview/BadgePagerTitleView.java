package cn.kuwo.pp.util.magictabview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;

import cn.kuwo.common.util.L;
import cn.kuwo.pp.R;

import static cn.kuwo.pp.util.danmuku.model.utils.PaintUtils.getPaint;

public class BadgePagerTitleView extends FrameLayout implements IMeasurablePagerTitleView {
    protected int mSelectedColor;
    protected int mNormalColor;
    protected TextView mBadgeView;
    protected TextView mTextView;

    public BadgePagerTitleView(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_layout_title, this, false);
        addView(rootView);

        mBadgeView = rootView.findViewById(R.id.tvBadgeView);

        mTextView = rootView.findViewById(R.id.tvTitle);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setSingleLine();
        //mTextView.setBackgroundColor(Color.RED); //测试用
    }

    public void setText(String text) {
        mTextView.setText(text);
        L.m3(text);
    }

    public void setBadgeNumber(int number) {









        L.m3(number);
        mBadgeView.setVisibility(number > 0 ? VISIBLE : GONE);
        String text = number + "";
        if (number > 99) {
            text = "99+";
        }

        mBadgeView.setText(text);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        L.m2();
        mTextView.setTextColor(mSelectedColor);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        L.m2();
        mTextView.setTextColor(mNormalColor);
    }

    private float mMinScale = 0.75f;

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        //L.m2();
        int color = ArgbEvaluatorHolder.eval(leavePercent, mSelectedColor, mNormalColor);
        mTextView.setTextColor(color);
        //mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        setScaleX(1.0f + (mMinScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (mMinScale - 1.0f) * leavePercent);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        //L.m2();
        int color = ArgbEvaluatorHolder.eval(enterPercent, mNormalColor, mSelectedColor);
        mTextView.setTextColor(color);
        //mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        setScaleX(mMinScale + (1.0f - mMinScale) * enterPercent);
        setScaleY(mMinScale + (1.0f - mMinScale) * enterPercent);
    }

    @Override
    public int getContentLeft() {
        Rect bound = new Rect();
        getPaint().getTextBounds(mTextView.getText().toString(), 0, mTextView.getText().length(), bound);
        int contentWidth = bound.width();
        int i = getLeft() + getWidth() / 2 - contentWidth / 2;
        L.m3(mTextView.getText(), "bound", bound, "getLeft=", getLeft(), "getWidth=", getWidth(), "getMesureWidth()", getMeasuredWidth());
        return i;
    }

    @Override
    public int getContentTop() {
        Paint.FontMetrics metrics = getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        int i = (int) (getHeight() / 2 - contentHeight / 2);
        L.m3(i);
        return i;
    }

    @Override
    public int getContentRight() {
        Rect bound = new Rect();
        getPaint().getTextBounds(mTextView.getText().toString(), 0, mTextView.getText().length(), bound);
        int contentWidth = bound.width();
        int i = getLeft() + getWidth() / 2 + contentWidth / 2;
        L.m3(mTextView.getText(), "bound=", bound, "getLeft()", getLeft(), "getWidth=", getWidth(), "getMeasureWidth=", getMeasuredWidth());
        return i; // getLeft-相对于父布局的位置
    }

    @Override
    public int getContentBottom() {
        Paint.FontMetrics metrics = getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        int i = (int) (getHeight() / 2 + contentHeight / 2);
        L.m3(i);
        return i;
    }

    public int getSelectedColor() {
        L.m2();
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        L.m2();
        mSelectedColor = selectedColor;
    }

    public int getNormalColor() {
        L.m2();
        return mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        L.m3("normalColor=", normalColor);
        mNormalColor = normalColor;
    }
}
