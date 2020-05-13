package com.example.bluetoothtest.lobby_creator;

import androidx.fragment.app.Fragment;
import com.example.bluetoothtest.MainActivity;
import com.example.bluetoothtest.Room;
import com.example.bluetoothtest.game.GameFragment;
import com.example.bluetoothtest.game.GameManager;

public abstract class LobbyFragment extends Fragment {
    protected Room room;
    protected GameManager gameManager;
    protected GameFragment gameFragment;

    public LobbyFragment() {

    }

    public void createGame() {
        gameFragment = (GameFragment) ((MainActivity) getActivity()).getFragment("CURRENT_GAME");
        gameManager = createGameManager();
    }

    protected abstract GameManager createGameManager();
}
