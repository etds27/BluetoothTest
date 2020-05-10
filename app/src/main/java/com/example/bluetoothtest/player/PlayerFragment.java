package com.example.bluetoothtest.player;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.bluetoothtest.R;

public class PlayerFragment extends Fragment {

    Player player;

    public PlayerFragment(Player player) {
        this.player = player;
    }


    public PlayerFragment newInstance(Player player) {
        return new PlayerFragment(player);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_layout, container, false);
        if (player == null) return view;
        TextView playerNameView = view.findViewById(R.id.turn_name_view);

        String text = player.getName() + "'s turn";
        playerNameView.setText(text);
        playerNameView.setTextColor(Color.parseColor(player.getSecondaryColor()));

        LinearLayout layout = view.findViewById(R.id.player_fragment_layout);
        layout.setBackgroundColor(Color.parseColor(player.getPrimaryColor()));


        return view;
    }
}
