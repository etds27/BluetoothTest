package com.example.bluetoothtest.lobby_creator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.bluetoothtest.MainActivity;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.Room;
import com.example.bluetoothtest.game.GameFragment;
import com.example.bluetoothtest.game.GameManager;
import com.example.bluetoothtest.game.LocalGameManager;
import com.example.bluetoothtest.player.Player;
import com.example.bluetoothtest.player.list.PlayerList;
import com.example.bluetoothtest.player.list.PlayerListAdapter;

public abstract class LobbyFragment extends Fragment {
    protected Room room;
    protected GameManager gameManager;
    protected GameFragment gameFragment;
    protected PlayerList mPlayerList;
    protected PlayerListAdapter mPlayerListAdapter;
    protected FrameLayout mFrameLayout;
    protected View mVariableView;
    protected Button mStartGameButton;

    public LobbyFragment() {
        room = Room.getInstance();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.lobby_layout, container, false);

        mPlayerList = view.findViewById(R.id.lobby_player_list);
        mFrameLayout = view.findViewById(R.id.lobby_variable_frame);
        mVariableView = inflateVariableFrame();

        room.addList(mPlayerList);
        mPlayerListAdapter = (PlayerListAdapter) mPlayerList.getAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mPlayerList.setLayoutManager(llm);


        mStartGameButton = view.findViewById(R.id.start_game_button);

        mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });




        return view;
    }



    protected abstract View inflateVariableFrame();

    protected abstract GameManager createGameManager();

    protected boolean playerNameExists(String name) {

        for (Player player : room) {
            if (player.getName().toLowerCase().equals(name.toLowerCase())) return true;
        }

        return false;
    }

    /**
     * Starts the game. Changes view to Game Fragment and generates Game Manager object
     */
    private void startGame() {
        ((MainActivity) getActivity()).setViewPage("CURRENT_GAME");
        gameFragment = (GameFragment) ((MainActivity) getActivity()).getFragment("CURRENT_GAME");
        gameManager = new LocalGameManager(getContext(), room, gameFragment);
    }
}
