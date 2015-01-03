package com.example.examplersqr;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Extended SwipeRefreshLayout (SRL) that let's you specify the scrollable view to use.
 *
 * The standard SwipeRefreshLayout, which this extends, requires that the scrollable view
 * is a --direct-- descendant (and the only descendant).  It does this in order to know which
 * child view to ask "Can you scroll up?" to determine if it should run the refresh or just allow
 * the child to scroll.
 *
 * Sometimes, however, you want to have multiple views inside the layout.  For example, you
 * might want a FrameLayout with both a toolbar and a listview/recyclerview to
 * hold the content.  Use this class to place any number of views withing the SRL, and tell this
 * class which one of them is the "scrollable" view.
 *
 * Created by David Bleicher on 1/2/15.
 *
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
