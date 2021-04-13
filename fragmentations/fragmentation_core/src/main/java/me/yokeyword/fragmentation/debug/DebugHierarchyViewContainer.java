package me.yokeyword.fragmentation.debug;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import java.util.List;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.F;
import me.yokeyword.fragmentation.R;

/**
 * Created by YoKeyword on 16/2/21.
 */
public class DebugHierarchyViewContainer extends ScrollView {
    private Context mContext;

    private LinearLayout mLinearLayout;
    private LinearLayout mTitleLayout;

    private int mItemHeight;
    private int mPadding;

    public DebugHierarchyViewContainer(Context context) {
        super(context);
        if(BuildConfig.DEBUG) F.m(getClass());
        initView(context);
    }

    public DebugHierarchyViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(BuildConfig.DEBUG) F.m(getClass());
        initView(context);
    }

    public DebugHierarchyViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(BuildConfig.DEBUG) F.m(getClass());
        initView(context);
    }

    private void initView(Context context) {
        if(BuildConfig.DEBUG) F.m(getClass());
        mContext = context;
        HorizontalScrollView hScrollView = new HorizontalScrollView(context);
        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        hScrollView.addView(mLinearLayout);
        addView(hScrollView);

        mItemHeight = dip2px(50);
        mPadding = dip2px(16);
    }

    private int dip2px(float dp) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void bindFragmentRecords(List<DebugFragmentRecord> fragmentRecords) {
        if(BuildConfig.DEBUG) F.m(getClass());
        mLinearLayout.removeAllViews();
        LinearLayout ll = getTitleLayout(); // 栈视图 ❓
        mLinearLayout.addView(ll);

        if (fragmentRecords == null) return;
        // 设置具体的值
        DebugHierarchyViewContainer.this.setView(fragmentRecords, 0, null);
    }

    @NonNull
    private LinearLayout getTitleLayout() {
        if(BuildConfig.DEBUG) F.m(getClass());
        if (mTitleLayout != null) return mTitleLayout;

        mTitleLayout = new LinearLayout(mContext);
        mTitleLayout.setPadding(dip2px(24), dip2px(24), 0, dip2px(8));
        mTitleLayout.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams flParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTitleLayout.setLayoutParams(flParams);

        TextView title = new TextView(mContext);
        title.setText(R.string.fragmentation_stack_view);
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;
        title.setLayoutParams(p);
        mTitleLayout.addView(title);

        ImageView img = new ImageView(mContext);
        img.setImageResource(R.drawable.fragmentation_help);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = dip2px(16);
        params.gravity = Gravity.CENTER_VERTICAL;
        img.setLayoutParams(params);
        mTitleLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, R.string.fragmentation_stack_help, Toast.LENGTH_LONG).show();
            }
        });
        mTitleLayout.addView(img);
        return mTitleLayout;
    }

    private void setView(final List<DebugFragmentRecord> fragmentRecordList, final int hierarchy, final TextView tvItem) {
        if(BuildConfig.DEBUG) F.m(getClass());
        for (int i = fragmentRecordList.size() - 1; i >= 0; i--) {
            DebugFragmentRecord child = fragmentRecordList.get(i);
            int tempHierarchy = hierarchy;

            final TextView childTvItem;
            childTvItem = getTextView(child, tempHierarchy); //具体的标题
            childTvItem.setTag(R.id.hierarchy, tempHierarchy); // 每一个TextView都设置一个tag，便于设置层级

            final List<DebugFragmentRecord> childFragmentRecord = child.childFragmentRecord;
            if (childFragmentRecord != null && childFragmentRecord.size() > 0) { // 有层级的情况下
                tempHierarchy++;
                childTvItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fragmentation_ic_right, 0, 0, 0);
                final int finalChilHierarchy = tempHierarchy;
                childTvItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag(R.id.isexpand) != null) {
                            boolean isExpand = (boolean) v.getTag(R.id.isexpand);
                            if (isExpand) {
                                childTvItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fragmentation_ic_right, 0, 0, 0);
                                DebugHierarchyViewContainer.this.removeView(finalChilHierarchy);
                            } else {
                                handleExpandView(childFragmentRecord, finalChilHierarchy, childTvItem);

                            }
                            v.setTag(R.id.isexpand, !isExpand);
                        } else {
                            childTvItem.setTag(R.id.isexpand, true);
                            handleExpandView(childFragmentRecord, finalChilHierarchy, childTvItem);
                        }
                    }
                });
            } else { // 没有层级的情况下
                childTvItem.setPadding(childTvItem.getPaddingLeft() + mPadding, 0, mPadding, 0);
            }

            if (tvItem == null) {
                mLinearLayout.addView(childTvItem);
            } else {
                mLinearLayout.addView(childTvItem, mLinearLayout.indexOfChild(tvItem) + 1);
            }
        }
    }

    private void handleExpandView(List<DebugFragmentRecord> childFragmentRecord, int finalChilHierarchy, TextView childTvItem) {
        if(BuildConfig.DEBUG) F.m(getClass());
        DebugHierarchyViewContainer.this.setView(childFragmentRecord, finalChilHierarchy, childTvItem);
        childTvItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.fragmentation_ic_expandable, 0, 0, 0);
    }

    private void removeView(int hierarchy) {
        if(BuildConfig.DEBUG) F.m(getClass());
        int size = mLinearLayout.getChildCount();
        for (int i = size - 1; i >= 0; i--) {
            View view = mLinearLayout.getChildAt(i);
            if (view.getTag(R.id.hierarchy) != null && (int) view.getTag(R.id.hierarchy) >= hierarchy) {
                mLinearLayout.removeView(view);
            }
        }
    }

    private TextView getTextView(DebugFragmentRecord fragmentRecord, int hierarchy) {
        if(BuildConfig.DEBUG) F.m(getClass());
        TextView tvItem = new TextView(mContext); // 需要显示的文字

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        tvItem.setLayoutParams(params);
        if (hierarchy == 0) {
            tvItem.setTextColor(Color.parseColor("#333333"));
            tvItem.setTextSize(16);
        }
        tvItem.setGravity(Gravity.CENTER_VERTICAL);
        tvItem.setPadding((int) (mPadding + hierarchy * mPadding * 1.5), 0, mPadding, 0);
        tvItem.setCompoundDrawablePadding(mPadding / 2);

        TypedArray a = mContext.obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        tvItem.setBackgroundDrawable(a.getDrawable(0));
        a.recycle();

        tvItem.setText(fragmentRecord.fragmentName);

        return tvItem;
    }
}
