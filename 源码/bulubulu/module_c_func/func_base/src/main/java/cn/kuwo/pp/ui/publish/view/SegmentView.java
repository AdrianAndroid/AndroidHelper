package cn.kuwo.pp.ui.publish.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.kuwo.pp.R;

public class SegmentView extends LinearLayout {
    private TextView leftTextView;
    private TextView rightTextView;
    private onSegmentViewClickListener segmentListener;

    // 这是代码加载ui必须重写的方法
    public SegmentView(Context context) {
        super(context);
        initView();
    }

    // 这是在xml布局使用必须重写的方法
    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        leftTextView = new TextView(getContext());
        rightTextView = new TextView(getContext());

        // 设置textview的布局宽高并设置为weight属性都为1
        leftTextView.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        rightTextView.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

        // 初始化的默认文字
        leftTextView.setText("消息");
        rightTextView.setText("电话");

        // 实现不同的按钮状态，不同的颜色
        ColorStateList csl = getResources().getColorStateList(R.color.segment_text_color_selector);
        leftTextView.setTextColor(csl);
        rightTextView.setTextColor(csl);

        // 设置textview的内容位置居中
        leftTextView.setGravity(Gravity.CENTER);
        rightTextView.setGravity(Gravity.CENTER);

        // 设置textview的内边距
        leftTextView.setPadding(5, 6, 5, 6);
        rightTextView.setPadding(5, 6, 5, 6);

        // 设置文字大小
        setSegmentTextSize(16);

        // 设置背景资源
        leftTextView.setBackgroundResource(R.drawable.segment_left_background);
        rightTextView.setBackgroundResource(R.drawable.segment_right_background);

        // 默认左侧textview为选中状态
        leftTextView.setSelected(true);

        // 加入textview
        this.removeAllViews();
        this.addView(leftTextView);
        this.addView(rightTextView);
        this.invalidate();//重新draw()

        leftTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftTextView.isSelected()) {
                    return;
                }
                leftTextView.setSelected(true);
                rightTextView.setSelected(false);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(leftTextView, 0);
                }
            }
        });

        rightTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightTextView.isSelected()) {
                    return;
                }
                rightTextView.setSelected(true);
                leftTextView.setSelected(false);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(rightTextView, 1);
                }
            }
        });

    }

    /**
     * 设置字体大小
     *
     * @param dp
     */
    private void setSegmentTextSize(int dp) {
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    /**
     * 手动设置选中的状态
     *
     * @param i
     */
    public void setSelect(int i) {
        if (i == 0) {
            leftTextView.setSelected(true);
            rightTextView.setSelected(false);
        } else {
            leftTextView.setSelected(false);
            rightTextView.setSelected(true);
        }
    }

    /**
     * 设置控件显示的文字
     *
     * @param text
     * @param position
     */
    public void setSegmentText(CharSequence text, int position) {
        if (position == 0) {
            leftTextView.setText(text);
        }
        if (position == 1) {
            rightTextView.setText(text);
        }
    }

    // 定义一个接口接收点击事件
    public interface onSegmentViewClickListener {
        public void onSegmentViewClick(View view, int position);
    }

    public void setOnSegmentViewClickListener(onSegmentViewClickListener segmentListener) {
        this.segmentListener = segmentListener;
    }
}
