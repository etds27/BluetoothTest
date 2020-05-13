package com.example.bluetoothtest.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import com.example.bluetoothtest.FragmentPagerAdapters.GameFragmentPagerAdapter;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.Room;
import com.example.bluetoothtest.SwipeViewPager;
import com.example.bluetoothtest.player.*;
import com.example.bluetoothtest.player.list.MiniPlayerListRenderer;
import com.example.bluetoothtest.player.list.PlayerList;
import com.example.bluetoothtest.player.list.PlayerListAdapter;

public class GameFragment extends Fragment {

    private static final String TAG = "CurrentGameFragment";
    private GameViewPager mViewPager;
    private PlayerList mPlayerList;
    public PlayerListAdapter mPlayerListAdapter;
    private Room currentRoom;
    private GameFragmentPagerAdapter gameFragmentPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentRoom = Room.getInstance();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Create view pager. Set swipable to false. Later, set the first person to true

        mViewPager = view.findViewById(R.id.game_view_pager);
        mViewPager.setSwipable(true);

        // Create mini player list in top right of game
        mPlayerList = view.findViewById(R.id.hud_player_list);
        mPlayerList.setEnabled(false);

        // Set up hud player list adapter
        mPlayerListAdapter = (PlayerListAdapter) mPlayerList.getAdapter();
        mPlayerListAdapter.setPlayerListRenderer(new MiniPlayerListRenderer());
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mPlayerList.setLayoutManager(llm);
        currentRoom.addList(mPlayerList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(mViewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Started");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setupViewPager(ViewPager viewPager) {
        gameFragmentPagerAdapter = new GameFragmentPagerAdapter(getChildFragmentManager());
    }


    public int setupGameDisplay() {
        mPlayerListAdapter.notifyDataSetChanged();

        for (Player player : currentRoom) {
            player.createFragment();
            PlayerFragment pf = player.getFragment();
            gameFragmentPagerAdapter.addFragment(pf, player.getName());
            gameFragmentPagerAdapter.notifyDataSetChanged();
        }

        //Log.d(TAG, "setupGameDisplay: fragment " + currentRoom.get(0).getFragment().getParentFragment());
        Log.d(TAG, "setupGameDisplay: fragment: " + gameFragmentPagerAdapter.getItem(0));
        mViewPager.setAdapter(gameFragmentPagerAdapter);
        int startIndex = mViewPager.getChildCount() * GameFragmentPagerAdapter.LOOPS_COUNT / 2;
        mViewPager.setCurrentItem(startIndex);
        return startIndex;
    }

    public GameViewPager getViewPager() {
        return mViewPager;
    }
}
