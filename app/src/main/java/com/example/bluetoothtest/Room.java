package com.example.bluetoothtest;

import java.util.Random;

public class Room {
    private String hash;
    private static final String ALPHANUMERIC = "ABCDEF0123456789"; // used to generate hash
    private static final int ROOM_HASH_SIZE = 4;

    public void generateHash() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < ROOM_HASH_SIZE; i++) {
            char alphanum = randomAlphanumeric();
            sb.append(alphanum);
        }

        hash = sb.toString();
    }

    private char randomAlphanumeric() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(ALPHANUMERIC.length());
        return ALPHANUMERIC.charAt(randomIndex);

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
