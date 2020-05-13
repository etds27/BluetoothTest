package com.example.bluetoothtest.FragmentPagerAdapters;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.bluetoothtest.Room;
import com.example.bluetoothtest.player.Player;
import com.example.bluetoothtest.player.PlayerFragment;

public class GameFragmentPagerAdapter extends MasterFragmentStatePagerAdapter {
    private static final String TAG = "GameFragmentPagerAdapte";
    private Context mContext;
    public static int LOOPS_COUNT = 1000;
    public static int startIndex = 0;
    private Room currentRoom;

    public GameFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Log.d(TAG, "getItem: total position: " + position);
        if (keyList != null && keyList.size() > 0) {
            position %= keyList.size(); // use modulo for infinite cycling
            Log.d(TAG, "getItem: position: " + position);
            //return fragments.get(keyList.get(position));
            Player player = ((PlayerFragment) fragments.get(keyList.get(position))).getPlayer();
            return PlayerFragment.newInstance(player);
        } else {
            return PlayerFragment.newNullInstance();
        }
    }



    @Override
    public int getCount() {
        if (keyList != null && keyList.size() > 0) {
            return keyList.size() * LOOPS_COUNT; // simulate infinite by big number of products
        } else {
            return 1;
        }
    }
}
