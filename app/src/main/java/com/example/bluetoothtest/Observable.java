package com.example.bluetoothtest;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    public final List<Observer> observers = new ArrayList<Observer>();

    public void notifyObservers() {
        for (Observer o : observers) {o.update();}
    }


}
