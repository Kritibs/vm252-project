package edu.luther.cs252.group1.observation;

import java.util.ArrayList;

// Based on 2021-10-21 lecture simple-observable.java
public class BasicObservable implements Observable {

    private final ArrayList< Observer > myObservers;

    public BasicObservable()
    {

        myObservers = new ArrayList<>();

    }

    @Override
    public void attach(Observer anotherObserver)
    {

        if (anotherObserver != null && ! myObservers.contains(anotherObserver))
            myObservers.add(anotherObserver);

    }

    @Override
    public void detach(Observer currentObserver)
    {

        myObservers.remove(currentObserver);

    }

    @Override
    public void announceChange()
    {
        for (Observer currentObserver : myObservers)
            currentObserver.update();

    }
}
