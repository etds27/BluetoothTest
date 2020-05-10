package com.example.bluetoothtest.player;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bluetoothtest.Room;

public class PlayerList extends RecyclerView implements PlayerListAdapter.OnStartDragListener, PlayerListAdapter.PlayerOnClick {

    private static final String TAG = "PlayerList";
    private ItemTouchHelper mItemTouchHelper;

    public PlayerList(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "PlayerList: Creating player list");
        setAdapter(new PlayerListAdapter(this, this, new FullPlayerListRenderer()));
        // Because the player list needs the player list adapter, use the adapter and manually cast to touch helper adapter
        PlayerListTouchHelper callback = new PlayerListTouchHelper((PlayerListTouchHelper.ItemTouchHelperAdapter) getAdapter());
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(this);
    }


    public void addToRoom() {
        Room.getInstance().addList(this);
    }

    @Override
    public void onPlayerClick(int position) {
        Log.d(TAG, "onPlayerClick: pressed item " + position);
    }

    @Override
    public void onStartDrag(ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
