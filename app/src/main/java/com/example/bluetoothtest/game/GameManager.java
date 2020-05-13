package com.example.bluetoothtest.game;


import android.content.Context;
import android.util.Log;
import androidx.annotation.LongDef;
import androidx.viewpager.widget.ViewPager;
import com.example.bluetoothtest.Room;
import com.example.bluetoothtest.player.Player;

import java.util.HashMap;

public abstract class GameManager {
    private int turnPosition = 0;
    private int viewPagerPosition = 0;

    private HashMap<Player, Integer> playerTurnsTaken;
    private Player currentPlayer;
    private Player startingPlayer;
    private Room room;
    private GameFragment gameFragment;
    private Context mContext;
    private GameViewPager gameViewPager;
    private static final String TAG = "GameManager";

    public GameManager(Context context, Room room, GameFragment gameFragment) {

        this.room = room;
        mContext = context;
        this.gameFragment = gameFragment;

        // Method returns starting index of view pager
        viewPagerPosition = this.gameFragment.setupGameDisplay();
        currentPlayer = room.get(0);
        startingPlayer = room.get(0);
        gameViewPager = gameFragment.getViewPager();


        addGameViewPagerSwipeListener();
    }

    private void addGameViewPagerSwipeListener() {
        gameViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                turnPosition = position - viewPagerPosition;
                Log.d(TAG, "onPageSelected: total position " + position);
                Log.d(TAG, "onPageSelected: relative position " + (position - viewPagerPosition));
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }




}
