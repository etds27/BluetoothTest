package com.example.bluetoothtest.FragmentPagerAdapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class MasterFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    protected Map<String, Fragment> fragments = new HashMap<>();
    protected ArrayList<String> keyList = new ArrayList<>();

    public MasterFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public abstract Fragment getItem(int position);

    public abstract int getCount();

    public int getFragmentIndex(String key) {
        return keyList.indexOf(key);
    }

    public void addFragment(Fragment fragment, String key) {
        if (! fragments.containsKey(key)) {
            fragments.put(key, fragment);
            keyList.add(key);
        }
    }

    public void deleteFragment(Fragment fragment) {
        for (String key : fragments.keySet()) {
            if (fragments.get(key).equals(fragment)) {
                deleteFragment(key);
            }
        }
    }

    public void deleteFragment(String key) {
        if (fragments.containsKey(key)) {
            keyList.remove(key);
            fragments.remove(key);
        }
    }
}
