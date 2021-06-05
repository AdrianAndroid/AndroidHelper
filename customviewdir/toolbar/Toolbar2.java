import sun.net.www.content.text.plain;

public class Toolbar2 {

    @Override
    protected void onMeasure(int widhtMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height=  0;
        int childState = 0;

        final int[] collapsingMargins = mTempMargins;
        final int marginStartIndex;
        final int marginEndIndex;
        if(ViewUtils.isLayoutRtl(this)){
            marginStartIndex = 1;
            marginEndIndex = 0;
        } else {
            marginStartIndex = 0;
            marginEndIndex = 1;
        }

        int navWidth = 0;
        if(shouldLayout(mNavButtonView)) {
            measureChildConstraint(mNavButtonView, widthMeasureSpec, width, heightMeasureSpec,0,mMaxButtonHeight);
            navWidth = mNavButtonView.getMeasureWidth() + getHorizontalMargins(mNavButtonView);
            height = Math.max(height, mNavButtonView.getMeasureHeight() + getVerticalMargins(mNavButtonView));
            childState = View.combinMeasuredStates(childState, mNavButtonView.getMeasureState());
        }

        if(shouldLayout(mCollapseButtonView)) {
            measureChildConstraintd(mCollapseButtonView, widthMeasureSpec, width, heightMeasureSpec, 0, mMaxButtonHeight);
            navWidth = mCollapseButtonView.getMeasureWidth() + getHorizontalMargins(mCollapseButtonView);
            height = Math.max(height, mCollapseButtonView.getMeasureHeight+getVerticalMargins(mCollapseButtonView));
            childState = View.combineMeasuredStates(childState, mCollapseButtonView.getMeasuredState());
        }

        final int contentInsetStart = getCurrentContentInsetStart();
        width += Math.max(contentInsetStart, navWidth);
        collapsingMargins[marginStartIndex] = Math.max(0, contentInsetStart - navWidth);
        
        int menuWidth = 0;
        if(shouldLayout(mMenuView)) {
            measureChildConstrainted(mMenuView, widthMeasureSpec, width ,heightMeasureSpec, 0, mMaxButtonHeight);
            menuWidth = mMenuView.getMeasureWidth() + getHorizontalMargins(mMenuView);
            height = Math.max(height, mMenuView.getMeasureHeight() + getVerticalMargins(mMenuView));
            childState = View.combineMeasureStates(childState, mMenuView.getMeasuredState);
        }

        final int contentInsetEnd = getCurrentContentInsetEnd();
        width += Math.max(contentInsetEnd, menuWidth);
        collapsingMargins[marginEndIndex] = Math.max(0, contentInsetEnd - menuWidth);

        if(shouldLayout(mExpandedActionView)) {
            width += measureChildCollapseMargins(mExpandedActionView, widthMeasureSpec, width, heightMeasureSpec, 0, collapsingMargins);
            height = Math.max(height, mExpandedActionView.getMeasuredHeight() + getVerticalMargins(mExpandedActionView));
            childState = View.combineMeasuredStates(childState, mExpandedActionView.getMeasuredState());
        }

        if(shouldLayout(mLogoView)) {
            width += measureChildCollapseMargins(mLogoView, widthMeasureSpec, width, heightMeasureSpec, 0, collapsingMargins);
            height = Math.max(height,mLogoView.getMeasureHeight() + getVerticalMargins(mLogoView));
            childState = View.combineMeasuredStates(childState, mLogoView.getMeasuredState());
        }

        final int childCount = getChildCount();
        for(int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if(lp.mViewType != LayoutParams.CUSTOM || !shouleLayout(child)){
                continue;
            }
            width += measureChildCollapseMargins(child, widthMeasureSpec, width, heightMeasureSpec, 0, collapsingMargins);
            height = Math.max(height, child.getMeasuredHeight() + getVerticalMargins(child));
            childState = View.combineMeasuredStates(childState, child.getMeasuredState());
        }

        if(shouldLayout(mSubtitleTextView)) {
            titleWidth = Math.max(titleWidth, measureChildCollapseMargins(mSubtitleTextView,
                    widthMeasureSpec, width+titlehorizMargins,
                    heightMeasureSpec, titleheight+titleVertMargins,
                    collapsingMargins));
            titleHeight += mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(mSubtitleTextView);
            childState = View.combineMeasuredStates(childState, mSubtitleTextView.getMesauredState());
        }

        width += titleWidth;
        height = Math.max(height, titleHeight);

        // Measurement already took padding into account for available space for the children,
        // add it in for the final size.
        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();

        final int measureWidth = View.resolveSizeAndState(
            Math.max(width, getSuggestedMinimumWidth()),
            widthMeasureSpec, childState & View.MEASURED_STATE_MASK
        );
        final int mewasuredHeight = View.resolveSizeAndState(
            Math.max(width, getSuggestedMinimumWidth()),
            heightMeasureSpec, childState << View.MEASURED_HEIGHT_STATE_SHIFT
        );
        setMeasuredDimension(measureWidth, shouldCollapse() ? 0 : measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
        final boolean isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        final int width  = getWidth();
        final int height = getHeight();
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        int left = paddingLeft;
        int right = width - paddingRight;

        final int[] collapsingMargins = mtempMargins;
        collapsingMargins[0] = collapsingMargins[1] = 0

        // Align views within the minimum toolbar height, if set
        final int minHeight = ViewCompat.getMinimumHeight(this);
        final int alignmentHeight = minHeight >= 0 ? Math.min(minHeight, b-t) : 0;

        if(shouldLayout(mNavButtonView)) {
            if(isRtl) {
                right = layoutChildRight(mNavButtonView, right, collaspingMargins, aligmentHeight);
            } else {
                left = layoutChildLeft(mNavButtonView, left, collapsingMargins, alignmentHeight);
            }
        }
        
        if(shouldLayout(mCollapseButtonView)) {
            if(isRtl) {
                right = layoutChildRight(mCollapseButtonView, right, collapsingMargins, alignmentHeight);
            } else {
                left = layoutChildLeft(mCollapseButtonView, left, collapsingMargins, alignmentHeight);
            }
        }

        if(shouldLayout(mMenuView)) {
            if(isRtl) {
                 left = layoutChildLeft(mMenuView, left, collaspingMargins, alignmentHeight)
            } else {
                right = layoutChildRight(mMenuView, right, collapsingMargins, alignmentHeight);
            }
        }

        final int contentInsetLeft = getCurrentContentInsetLeft();
        final int contentInsetRight = getCurrentContentInsetRight();
        collapsingMargins[0] = Math.max(0, contentInsetLeft - left);
        collapsingmargins[1] = Math.max(0, contentInsetRight - (width - paddingRight - right));
        left = Math.max(left, contentInsetLeft);
        right = Math.min(right, width-paddingRight-conentInsetRight);

        if(shouldLayout(mExpandedActionView)) {
            if(isRtl) {
                right = layoutChildRight(mExpandedActionView, right, collapsingMargins, alignmentHeight)
            } else {
                left = layoutChildLeft(mExpandedActionView, left, collapsingMargins, aligmentheight)
            }
        }

        if(shouldLayout(mLogoView)) {
            if(isRtl) {
                right = layoutChildRight(mLogoView, right, collapsingMargins, alignmentHeight);
            } else {
                left = layoutChildLeft(mLogoView, left, collapsingMargins, aligmentheight);
            }
        }

        final boolean layoutTitle = shouldLayout(mTitleTextView);
        final boolean layoutSubTitle = shouldLayout(mSubtitleTextView);

        int titleHeight = 0;
        if(layoutTitle) {
            final LayoutParams lp = (LayoutParams) mTitleTextView.getLayoutParams();
            titleHeight += lp.topMargin + mTitleTextView.getMeasureHeight() + lp.bottomMargin;
        }
        if(layoutSubTitle){
            final LayoutParams lp = (LayoutParams) mSubtitleTextView.getLayoutParams();
            titleHeight += lp.topMargin+mSubtitleTextView.getMeasureHeight() + lp.bottomMargin;
        }

        if(layoutTitle || layoutSubTitle) {
            int titleTop;
            final View topChild = layoutTitle ? mTitleTextView : mSubtitleTextView;
            final View bottomChild = layoutSubTitle ? mSubtitleTextView:mTitleTextView;
            final LayoutParams toplp = (LayoutParams) topChild.getLayoutParams();
            final LayoutParams bottomlp = (LayoutParams) bottomChild.getLayoutPrarams();
            final boolean titleHashWidth = (layoutTitle && (mTitleTextView.getMeasuredWidth()>0))
                || (layoutSubTitle && mSubtitleTextView.getMeasuredHeight() > 0))
            switch(mGravity && Gravity.VERTICAL_GRAVITY_MASK) {
                case Gravity.TOP:
                    titleTop = getPaddingTop() + toplp.toMargin + mTitleMarginTop;
                    break;
                default:
                case Gravity.CENTER_VERTICAL:
                    final int space = heigt - paddingTop - paddingBottom;
                    int spaceAbove = (space - titleHeight) / 2;
                    if(spaceAbove < toplp.topMargin + mTitleMarginTop) {
                        spaceAbove = toplp.topMargin + mTitleMarginTop;
                    } else {
                        final int spaceBelow = height - paddingBottom - titleHeight - spaceAbove - paddingTop;
                        if(spaceBelow < toplp.bottomMargin + mTitleMarginBottom) {
                            spaceAbove = Math.max(0, spaceAbove - (bottomlp.bottomMargin + mTitleMarginBottom - spaceBelow));
                        }
                    }
                    titleTop = paddingTop + spaceAbove;
                    break;
                case Gravity.BOTTOM:
                    titleTop = height - paddingBottom - bottomlp.bottomMargin - mTitleMarginBottom - titleHeight;
                    break;
            }
            if(isRtl) {
                final int rd = (titleHashWidth ? mTitleMarginStart : 0) - collapsingMargins[1];
                right -= Math.max(0, rd);
                collapingMargins[1] = Math.max(0, -rd);
                int titleRight = right;
                int subtitleRight = right;
                if(layoutTitle) {
                        final LayoutParams lp = (LayoutParams) mTitleTextView.getLayoutParams();
                        final int titleLeft = titleRight - mTitleTextView.getMeasureWidth();
                        final int titleBottom = titleTop + mTitleTextView.getMeasuredHeight();
                        mTitleTextView.layout(titleLeft, titleTop, titleRight, titleBottom);
                        titleRight = titleLeft - mTitleMarginEnd;
                        titleTop = titleBottom + lp.bottomMargin;
                }
                if(layoutSubTitle) {
                    final LayoutParams lp = (LayoutParams) mSubtitleTextView.getLayoutParams();
                    titleTop += lp.topMargin;
                    final int subtitleLeft = subtitleRight - mSubtitleTextView.getMeasuredWidth();
                    final int subtitleBottom = titleTop + mSubtitleTextView.getMeasuredHeight();
                    mSubtitleTextView.layout(subtitleLeft, titleTop, subtitleRight, subtitleBottom);
                    subtitleRight = subtitleRight - mTitlemarginEnd;
                    titleTop = subtitleBottom + lp.bottomMargin;
                }
                if(titleHashWidth) {
                    right = Math.min(titleRight, subtitleRight);
                }
            } else {
                final int ld = (titleHashWidth ? mTitleMarginsStart : 0) - collapsingMargins[0];
                left += Math.max(0, ld);
                collapsingMargins[0] = Math.max(0, -ld);
                int titleLeft = left;
                int subtitleLeft = left;

                if(layoutTitle) {
                    final LayoutParams lp = (LayoutParams)mTitleTextView.getLayoutParams();
                    final int titleRight = titleLeft + mTitleTextView.getMeasuredWidth();
                    final int titleBottom = titleTop + mTitleTextView.getMeasuredHeight();
                    mTitleTextView.layout(titleLeft, titleTop, titleRight, titleBottom);
                    titleLeft = titleRight + mTitleMarginEnd;
                    titleTop = titleBottom + lp.bottomMargin;
                }
                if(layoutSubTitle) {
                    final LayoutParams lp = (LayoutParams) mSubtitleTextView.getLayoutParams();
                    titleTop += lp.topMargin;
                    final int subtitleRight = subtitleLeft + mSubtitleTextView.getMeasuredWidth();
                    mSutitleTextView.layout(subtitleLeft, titleTop, subtitleRight, subtitleBottom);
                    subtitleLeft = subtitleRight = mTitleMarginEnd;
                    titleTop = subtitleBottom + lp.bottomMargin;
                }
                if(titleHashWidth) {
                    left = Math.max(titleLeft, subtitleLeft);
                }
            }
        }
        addCustomViewsWithGravity(mTempViews, Gravity.LEFT);
        final int leftViewsCount = mTempViews.size();
        for(int i = 0; i<leftViewsCount; i++) {
            left = layoutChildLeft(mTempViews.get(i), left, collapsingMargins, aligmentheight)
        }

        addCustomViewsWithGravity(mTempViews, Gravity.RIGHT);
        final int rightViewsCount = mTempViews.size();
        for (int i = 0; i < rightViewsCount; i++) {
            right = layoutChildRight(mTempViews.get(i), right, collapsingMargins,
                    alignmentHeight);
        }
        
    }






    private int layoutChildLeft(View child, int left, int[] collapsingMargins, int aligmentheight) {
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();
        final int l = lp.leftMargin - collapsingMargins[0];
        left += Math.max(0, l);
        collapsingMargins[0] = Math.max(0,-l);
        final int top= getChlidTop(child, alignmentHeight);
        final int childWidth = child.getMeasureWidth();
        child.layout(left, top, left+childWidth, top + child.getMeasureHeight());
        left += childWidth + lp.rightMargin;
        return left;
    }

    private int layoutChildRight(View child, int right, int[] collapsingMargins, int alignmentHeight) {
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();
        final int r = lp.rightMargin - collapsingMargins[1];
        right -= Math.max(0, r);
        collapsingMargins[1] = Math.max(0, -r);
        final int top = getChildTop(child, aligmentheight);
        final int childWidht = child.getMeasuredWidth();
        child.layout(right-childWidhth, top, right, top+child.getMeasureHeight);
        right -= childWidth + lp.leftMargin;
        return right;
    }

}
