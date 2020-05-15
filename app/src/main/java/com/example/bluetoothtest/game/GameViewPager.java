package com.example.bluetoothtest.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.bluetoothtest.SwipeViewPager;

public class GameViewPager extends SwipeViewPager {

    public GameViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean wasSwipeToRight = this.wasSwipeToRightEvent(event);
        // Do what you want here with left/right swipe

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean wasSwipeToRight = this.wasSwipeToRightEvent(event);
        // Do what you want here with left/right swipe

        return super.onTouchEvent(event);
    }

    // Stores the starting X position of the ACTION_DOWN event
    float downX;

    /**
     * Checks the X position value of the event and compares it to
     * previous MotionEvents. Returns a true/false value based on if the
     * event was an swipe to the right or a swipe to the left.
     *
     * @param event -   Motion Event triggered by the ViewPager
     * @return      -   True if the swipe was from left to right. False otherwise
     */
    private boolean wasSwipeToRightEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                return false;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                return downX - event.getX() > 0;

            default:
                return false;
        }
    }
}
