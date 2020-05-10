package com.example.bluetoothtest.player;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import androidx.fragment.app.Fragment;

public class Player {
    protected String name;
    protected BluetoothDevice btDevice;
    protected String primaryColor;
    protected String secondaryColor;
    protected PlayerFragment fragment;

    public Player(String name, BluetoothDevice btDevice) {
        this.name = name;
        this.btDevice = btDevice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BluetoothDevice getBtDevice() {
        return btDevice;
    }

    public void setBtDevice(BluetoothDevice btDevice) {
        this.btDevice = btDevice;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public PlayerFragment getFragment() {
        return fragment;
    }

    public void createFragment() {
        fragment = new PlayerFragment(this);
    }
}
