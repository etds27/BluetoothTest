package com.example.bluetoothtest.player;

import android.bluetooth.BluetoothDevice;

public class Host extends Player {
    public Host(String name, BluetoothDevice btDevice) {
        super(name, btDevice);
    }
}
