package edu.luther.cs252.group1.observation;

// Based on 2021-10-21 lecture observer.java
public interface Observer {

    //
    // Tell this object that the subject it's observing has changed state
    //
    void update();

}
