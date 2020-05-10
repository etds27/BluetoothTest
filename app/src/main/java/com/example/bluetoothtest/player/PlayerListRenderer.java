package com.example.bluetoothtest.player;

import android.graphics.Color;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PlayerListRenderer {
    public abstract void displayItems(PlayerListAdapter.PlayerListViewHolder holder, Player player);

    protected void displayColors(PlayerListAdapter.PlayerListViewHolder holder, Player player) {
        // Update background of list item to players color
        holder.itemView.setBackgroundColor(Color.parseColor(player.getPrimaryColor()));

        // Update all the text colors to complement color scheme
        holder.playerName.setTextColor(Color.parseColor(player.getSecondaryColor()));
        holder.playerDevice.setTextColor(Color.parseColor(player.getSecondaryColor()));
        holder.movePlayerButton.setColorFilter(Color.parseColor(player.getSecondaryColor()));
        holder.removePlayerButton.setColorFilter(Color.parseColor(player.getSecondaryColor()));
        holder.hostIndicator.setColorFilter(Color.parseColor(player.getSecondaryColor()));
    }
}
