package com.example.bluetoothtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Class is responsible for the host button, along with the host frame
 */
public class DesignateHostButton {
    private Button designateHostButton;
    private FrameLayout frameLayout;
    private Context mContext;
    private boolean state = true;


    public DesignateHostButton(Context context, Button button, FrameLayout frameLayout) {
        mContext = context;
        designateHostButton = button;

        // On click listener for button to perform selection processing


    }


    private void onClick() {

    }


}
