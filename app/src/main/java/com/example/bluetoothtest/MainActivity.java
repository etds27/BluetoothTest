package com.example.bluetoothtest;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bluetoothtest.FragmentPagerAdapters.HomeScreenFragmentPagerAdapter;
import com.example.bluetoothtest.bluetooth.BluetoothHostClientFragment;
import com.example.bluetoothtest.game.GameFragment;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FragmentManager fragmentManager;

    private HomeScreenFragment homeScreenFragment;

    private SwipeViewPager viewPager;

    public static HashMap<String, String[]> playerColors;
    public static List<String> playerColorNames;

    HomeScreenFragmentPagerAdapter fragmentPagerAdapter;



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.fragment_container);
        viewPager.setSwipable(false);
        //new BluetoothHostClientFragment();
        fragmentManager = getSupportFragmentManager();
        //homeScreenFragment = new HomeScreenFragment();


        fragmentPagerAdapter = new HomeScreenFragmentPagerAdapter(fragmentManager);
        fragmentPagerAdapter.addFragment(new HomeScreenFragment(), "HOME");
        //fragmentPagerAdapter.addFragment(new BluetoothHostClientFragment(), "BT_HOST_CLIENT");
        fragmentPagerAdapter.addFragment(new GameFragment(), "CURRENT_GAME");


        setPlayerColors(getResources().obtainTypedArray(R.array.player_color_arrays));


        viewPager.setAdapter(fragmentPagerAdapter);

        setViewPage(0);
    }


    public Fragment getFragment(int index) {
        return fragmentPagerAdapter.getItem(index);
    }

    public Fragment getFragment(String key) {
        return fragmentPagerAdapter.getItem(fragmentPagerAdapter.getFragmentIndex(key));
    }

    public void setViewPage(String key) {
        int index = fragmentPagerAdapter.getFragmentIndex(key);
        setViewPage(index);
    }

    public void setViewPage(int index) {
        viewPager.setCurrentItem(index);
    }

    public void enableSwipe(boolean b) {
        viewPager.setSwipable(b);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void setPlayerColors(TypedArray ta) {
        TypedArray singlePlayerColors;
        playerColors = new HashMap<>();
        playerColorNames = new ArrayList<>();
        Log.d(TAG, "setPlayerColors: Color Array " + ta);

        // Looping through each resource id that points to color array
        for (int i = 0; i < ta.length(); i++) {
            int resId = ta.getResourceId(i, -1); // Get each resources id in the typed array
            Log.d(TAG, "setPlayerColors: Resource id " + resId + " " + i);
            if (resId <= 0) continue;


            singlePlayerColors = this.getResources().obtainTypedArray(resId);
            Log.d(TAG, "setPlayerColors: Single Player Colors " + singlePlayerColors);
            String colorName = singlePlayerColors.getString(0);
            @SuppressLint("ResourceType") String primary = singlePlayerColors.getString(1);
            @SuppressLint("ResourceType") String secondary = singlePlayerColors.getString(2);
            String[] colorArray = new String[] {primary, secondary};

            playerColors.put(colorName, colorArray);
            playerColorNames.add(colorName);
        }
    }
}

