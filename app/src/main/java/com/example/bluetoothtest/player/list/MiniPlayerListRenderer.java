package com.example.bluetoothtest.player.list;

import android.util.DisplayMetrics;
import android.view.View;
import com.example.bluetoothtest.player.Player;
import com.example.bluetoothtest.player.list.PlayerListAdapter;
import com.example.bluetoothtest.player.list.PlayerListRenderer;

public class MiniPlayerListRenderer extends PlayerListRenderer {

    @Override
    public void displayItems(PlayerListAdapter.PlayerListViewHolder holder, Player player) {
        holder.playerName.setVisibility(View.VISIBLE);
        holder.playerDevice.setVisibility(View.INVISIBLE);
        holder.movePlayerButton.setVisibility(View.INVISIBLE);
        holder.removePlayerButton.setVisibility(View.INVISIBLE);
        holder.hostIndicator.setVisibility(View.INVISIBLE);

        holder.playerName.setTextSize(14);

        DisplayMetrics dm = new DisplayMetrics();
        holder.itemLayout.getLayoutParams().height = 40;
        holder.itemLayout.getLayoutParams().width = 100;
        displayColors(holder, player);
    }
}
