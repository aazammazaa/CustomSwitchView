package com.aazamn.customswitch.widget.switchView;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class MyNestedScrollView extends NestedScrollView {
    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //Log.i("CustomScrollView", "onInterceptTouchEvent: DOWN super false" );
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_MOVE:
                return false; // redirect MotionEvents to ourself

            case MotionEvent.ACTION_CANCEL:
                // Log.i("CustomScrollView", "onInterceptTouchEvent: CANCEL super false" );
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_UP:
                //Log.i("CustomScrollView", "onInterceptTouchEvent: UP super false" );
                return false;

            default:
                //Log.i("CustomScrollView", "onInterceptTouchEvent: " + action );
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        //Log.i("CustomScrollView", "onTouchEvent. action: " + ev.getAction() );
        return true;
    }


}