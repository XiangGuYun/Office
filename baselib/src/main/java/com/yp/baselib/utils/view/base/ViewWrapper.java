package com.yp.baselib.utils.view.base;

import android.view.View;

/**
 * 用于属性动画改变View的宽高
 */
public class ViewWrapper {

    private View mTarget;

    public ViewWrapper(View mTarget) {
        this.mTarget = mTarget;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}
