package com.example.bluetoothtest.lobby_creator;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.game.GameManager;
import com.example.bluetoothtest.game.LocalGameManager;
import com.example.bluetoothtest.player.Player;

public class LocalLobbyFragment extends LobbyFragment {

    private static final String TAG = "LocalLobbyFragment";
    private EditText editPlayerName;
    private ImageButton addPlayerButton;

    @Override
    protected GameManager createGameManager() {
        return new LocalGameManager(getContext(), room, gameFragment);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        editPlayerName = mVariableView.findViewById(R.id.add_local_player_name);
        addPlayerButton = mVariableView.findViewById(R.id.add_local_player_button);

        editPlayerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String name = editPlayerName.getText().toString();
                if (playerNameExists(name))
                    editPlayerName.setTextColor(Color.RED);
                else
                    editPlayerName.setTextColor(Color.GREEN);
            }
        });

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editPlayerName.getText().toString();
                if (playerNameExists(name)) return;

                Log.d(TAG, "onClick: adding new player " + name);
                mPlayerListAdapter.addItem(new Player(name, null));
                editPlayerName.setText("");
            }
        });



        return view;
    }


    @Override
    protected View inflateVariableFrame() {
        LayoutInflater lm = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return lm.inflate(R.layout.local_lobby_frame, mFrameLayout, true);
    }
}
