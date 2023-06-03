package edu.luther.cs252.group1.observation;

// Based on 2021-10-21 lecture observable.java
public interface Observable {

    //
    // Register anotherObserver as an observer of this object
    //
    void attach(Observer anotherObserver);

    //
    // Unregister currentObserver as an observer of this object
    //
    void detach(Observer currentObserver);

    //
    // Announce to all observers of this object that this object has changed state
    //
    void announceChange();
}
