package cn.kuwo.pp.util.cardlib;


import cn.kuwo.pp.util.cardlib.utils.ReItemTouchHelper;

/**
 * Created by linchen on 2018/2/6.
 * mail: linchen@sogou-inc.com
 */

public class CardSetting {
    public static final int DEFAULT_SHOW_ITEM = 2;

    public static final float DEFAULT_SCALE = 0.5f;

    public static final int DEFAULT_TRANSLATE = 10;

    public static final float DEFAULT_ROTATE_DEGREE = 5f;
    private OnSwipeCardListener mListener;

    public int getShowCount() {
        return DEFAULT_SHOW_ITEM;
    }

    public float getCardScale() {
        return DEFAULT_SCALE;
    }

    public int getCardTranslateDistance() {
        return DEFAULT_TRANSLATE;
    }

    public float getCardRotateDegree() {
        return DEFAULT_ROTATE_DEGREE;
    }

    public int getSwipeDirection() {
        return ReItemTouchHelper.LEFT;
    }

    public int couldSwipeOutDirection() {
        return ReItemTouchHelper.LEFT;
    }

    public float getSwipeThreshold() {
        return 0.3f;
    }

    public boolean enableHardWare() {
        return true;
    }

    public boolean isLoopCard() {
        return false;
    }

    public int getSwipeOutAnimDuration() {
        return 500;
    }

    public int getStackDirection() {
        return ReItemTouchHelper.DOWN;
    }

    public void setSwipeListener(OnSwipeCardListener listener) {
        mListener = listener;
    }

    public OnSwipeCardListener getListener() {
        return mListener;
    }
}
