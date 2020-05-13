package com.example.bluetoothtest.player.list;

import android.view.View;
import com.example.bluetoothtest.player.Player;
import com.example.bluetoothtest.player.list.PlayerListAdapter;
import com.example.bluetoothtest.player.list.PlayerListRenderer;

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
