package com.example.bluetoothtest.player.list;

import android.graphics.Color;
import com.example.bluetoothtest.player.Player;

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
