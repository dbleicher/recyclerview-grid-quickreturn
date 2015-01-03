package com.example.examplersqr;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by davidbleicher on 1/2/15.
 */
public class TargetedSwipeRefreshLayout extends SwipeRefreshLayout {
    private View myScrollableView = null;

    public TargetedSwipeRefreshLayout(Context context) {
        super(context);
    }

    public TargetedSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Specify the target scrollable view
     * @param view
     */
    public void setTargetScrollableView(View view) {
        myScrollableView = view;
    }

    @Override
    public boolean canChildScrollUp() {

        if(myScrollableView == null) return false;

        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (myScrollableView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) myScrollableView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return myScrollableView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(myScrollableView, -1);
        }
    }
}
