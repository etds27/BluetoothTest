package com.example.bluetoothtest.game;

import android.content.Context;
import android.util.AttributeSet;
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



}
