package com.example.bluetoothtest.player;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class FullPlayerListRenderer extends PlayerListRenderer {
    @Override
    public void displayItems(final PlayerListAdapter.PlayerListViewHolder holder, Player player) {


        holder.playerName.setVisibility(View.VISIBLE);
        holder.playerDevice.setVisibility(View.VISIBLE);
        holder.movePlayerButton.setVisibility(View.VISIBLE);
        holder.removePlayerButton.setVisibility(View.VISIBLE);
        holder.hostIndicator.setVisibility(View.VISIBLE);

        displayColors(holder, player);
    }
}
