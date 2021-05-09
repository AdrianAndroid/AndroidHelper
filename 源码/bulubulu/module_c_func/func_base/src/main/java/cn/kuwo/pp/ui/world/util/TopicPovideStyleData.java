package cn.kuwo.pp.ui.world.util;

import android.graphics.Typeface;

import com.koudai.styletextview.styledata.imp.TownTalkPovideStyleData;

import cn.kuwo.pp.R;


/**
 * @author shihc
 * @date 2018/10/26
 */
public class TopicPovideStyleData extends TownTalkPovideStyleData {

    @Override
    public int getHighlightColorId() {
        return R.color.white;
    }

    @Override
    public int getHighlightTextSize() {
        return 0;
    }

    @Override
    public int getTypeface() {
        return Typeface.NORMAL;
    }

    @Override
    public boolean isAddStyle() {
        return true;
    }

    @Override
    public String getRuleText() {
        return "#[^#]+#";
    }

    @Override
    public boolean isUseRule() {
        return true;
    }

    @Override
    public String getNeedHighlightText() {
        return null;
    }

    @Override
    public int getRichTextStyle() {
        return 3;
    }

}
