package com.example.bluetoothtest.game;


import android.content.Context;
import android.util.Log;
import androidx.annotation.LongDef;
import androidx.viewpager.widget.ViewPager;
import com.example.bluetoothtest.FragmentPagerAdapters.GameFragmentPagerAdapter;
import com.example.bluetoothtest.Room;
import com.example.bluetoothtest.player.Player;
import com.example.bluetoothtest.player.PlayerFragment;

import java.util.HashMap;

public abstract class GameManager {
    private int turnPosition = 0;
    private int viewPagerPosition = 0;

    private Player currentPlayer;
    private Player startingPlayer;
    PlayerFragment currentPlayerFragment;
    private Room room;
    private GameFragment gameFragment;
    private Context mContext;
    private GameViewPager gameViewPager;
    private GameFragmentPagerAdapter gameFragmentPagerAdapter;
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
        gameFragmentPagerAdapter = (GameFragmentPagerAdapter) gameViewPager.getAdapter();


        addGameViewPagerSwipeListener();
    }

    private void addGameViewPagerSwipeListener() {
        gameViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                Player previousPlayer = currentPlayer;
                //update current player to new player
                currentPlayerFragment = ((PlayerFragment) gameFragmentPagerAdapter.getItem(position));
                currentPlayer = currentPlayerFragment.getPlayer();

                // newPosition will be compared to current position to see if person moved forward or backward
                int newPosition = Math.abs(position - viewPagerPosition);

                // if new position is greater than old one, increase the prev player's turn count
                if (newPosition > turnPosition)  {
                    previousPlayer.incrementTurnCounter(true);
                    //currentPlayer.incrementTurnCounter(true);
                }  else {
                    currentPlayer.incrementTurnCounter(false);
                    //previousPlayer.incrementTurnCounter(false);
                }

                turnPosition = newPosition;

                gameFragment.updateTurnCounters(currentPlayer.getTurnsTaken(), turnPosition);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }




}
