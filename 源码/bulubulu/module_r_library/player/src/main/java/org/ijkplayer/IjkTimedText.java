package org.ijkplayer;

import android.graphics.Rect;
import java.lang.String;

public final class IjkTimedText {

    private Rect mTextBounds = null;
    private String mTextChars = null;

    public IjkTimedText(Rect bounds, String text) {
        mTextBounds = bounds;
        mTextChars = text;
    }

    public Rect getBounds() {
        return mTextBounds;
    }

    public String getText() {
        return mTextChars;
    }
}
