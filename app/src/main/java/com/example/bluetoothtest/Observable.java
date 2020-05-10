package com.example.bluetoothtest;

import java.util.ArrayList;
import java.util.List;

public interface Observable {

    public final List<Observer> observers = new ArrayList<Observer>();

    public void notifyObservers();


}
