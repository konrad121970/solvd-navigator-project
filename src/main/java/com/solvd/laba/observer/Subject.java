package com.solvd.laba.observer;

public interface Subject {

    void attach(Observer observer);
    void notifyObservers();

}
