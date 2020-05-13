package com.example.bluetoothtest;

import com.example.bluetoothtest.player.Player;
import com.example.bluetoothtest.player.list.PlayerList;

import java.util.*;

public class Room extends ArrayList<Player> {
    private String hash;
    private static final String ALPHANUMERIC = "ABCDEF0123456789"; // used to generate hash
    private static final int ROOM_HASH_SIZE = 4;
    private static Room instance = new Room();
    private static Set<PlayerList> associatedLists = new HashSet<>();
    private HashSet<String> usedColors = new HashSet<>();
    private Random rand = new Random();

    private Room() { }

    public static Room getInstance() {
        return instance;
    }

    public void generateHash() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < ROOM_HASH_SIZE; i++) {
            char alphanum = randomAlphanumeric();
            sb.append(alphanum);
        }

        hash = sb.toString();
    }

    private char randomAlphanumeric() {

        int randomIndex = rand.nextInt(ALPHANUMERIC.length());
        return ALPHANUMERIC.charAt(randomIndex);

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void randomizeOrder() {
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            int position = i % size();
            Player temp = get(position);
            int index = rand.nextInt(size());

            set(position, get(index));
            set(index, temp);
        }

        updateLists();
    }

    public void addList(PlayerList pl) {
        if (associatedLists.contains(pl)) return;
        associatedLists.add(pl);
    }

    public void updateLists() {
        for (PlayerList pl : associatedLists) {
            pl.getAdapter().notifyDataSetChanged();
        }
    }

    private void getRandomColor () {

    }

    @Override
    public boolean add(Player player) {
        if (size() >= 8) return false;

        ArrayList<String> copyOfColorNames = new ArrayList<>();
        for (int i = 0; i < MainActivity.playerColorNames.size(); i++) copyOfColorNames.add("");
        Collections.copy(copyOfColorNames, MainActivity.playerColorNames);
        Collections.shuffle(copyOfColorNames);
        String colorName = "";
        int i = 0;
        do {
            colorName = copyOfColorNames.get(i);
            i++;
        } while (usedColors.contains(colorName));

        usedColors.add(colorName);
        player.setPrimaryColor(MainActivity.playerColors.get(colorName)[0]);
        player.setSecondaryColor(MainActivity.playerColors.get(colorName)[1]);
        return super.add(player);
    }

}
