package com.example.bluetoothtest.FragmentPagerAdapters;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.bluetoothtest.player.Player;
import com.example.bluetoothtest.player.PlayerFragment;

public class GameFragmentPagerAdapter extends MasterFragmentPagerAdapter {
    private static final String TAG = "GameFragmentPagerAdapte";
    private Context mContext;
    public static int LOOPS_COUNT = 1000;

    public GameFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        if (keyList != null && keyList.size() > 0)
        {
            position = position % keyList.size(); // use modulo for infinite cycling
            return fragments.get(keyList.get(position));
        }
        else
        {
            return new PlayerFragment(null);
        }
    }

    @Override
    public int getCount() {
        if (keyList != null && keyList.size() > 0)
        {
            return keyList.size()*LOOPS_COUNT; // simulate infinite by big number of products
        }
        else
        {
            return 1;
        }
    }

}
