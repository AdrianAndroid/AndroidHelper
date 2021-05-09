package cn.kuwo.pp.ui.discover.view;

import android.content.Context;
import com.google.android.material.tabs.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import cn.kuwo.pp.R;

public class AutoTextSizeTabLayout extends TabLayout {
    public AutoTextSizeTabLayout(Context context) {
        this(context, (AttributeSet)null);
    }

    public AutoTextSizeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoTextSizeTabLayout(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public void setCustomViewTitles(String[] titles, Integer defaultCheck){
        addListener();

        for(int i=0; i<titles.length; i++){
            Tab tab = getTabAt(i);
            if(tab != null){
                final View view = getCustomTabView(getContext(), titles[i]);
                tab.setCustomView(view);

                if(i == defaultCheck){
                    CheckBox checkBox = view.findViewById(R.id.cb_name);
                    checkBox.setChecked(true);
                    checkBox.setTextSize(24);
                }
            }
        }
    }

    private void addListener(){
        this.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                for(int i=0; i<getTabCount(); i++){
                    Tab tab1 = getTabAt(i);

                    View cpTabView = tab1.getCustomView();
                    if(cpTabView == null){
                        return;
                    }

                    CheckBox checkBox = cpTabView.findViewById(R.id.cb_name);
                    if(tab == tab1){
                        checkBox.setChecked(true);
                        checkBox.setTextSize(24);
                        cpTabView.findViewById(R.id.cb_slide).setVisibility(View.VISIBLE);
                    }else{
                        checkBox.setChecked(false);
                        checkBox.setTextSize(16);
                        cpTabView.findViewById(R.id.cb_slide).setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private static View getCustomTabView(Context context, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout_friend_test_item, null);
        CheckBox tabText = (CheckBox) view.findViewById(R.id.cb_name);
        tabText.setText(text);
        return view;
    }
}
