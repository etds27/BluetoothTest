package com.example.bluetoothtest.FragmentPagerAdapters;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.*;

public class HomeScreenFragmentPagerAdapter extends MasterFragmentPagerAdapter {

    public HomeScreenFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(keyList.get(position));
    }

    @Override
    public int getCount() {
        return keyList.size();
    }
}
