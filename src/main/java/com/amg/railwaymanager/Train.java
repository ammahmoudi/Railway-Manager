package com.amg.railwaymanager;

import javafx.concurrent.Task;

import java.util.concurrent.atomic.AtomicBoolean;

public class Train extends Thread {
    int id;
    boolean inWay = false;
    RailLine railLine;
    RailwaySystem railwaySystem;
    boolean hasTask = false;
    Task task;


    public Train(int id, RailwaySystem railwaySystem) {


        this.id = id;
        setName("Train-" + id);
        this.railwaySystem = railwaySystem;
    }


    @Override
    public void run() {

    }

    public int getTerrainId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    synchronized public boolean isInWay() {
        return inWay;
    }

    synchronized public void setInWay(boolean inWay) {
        this.inWay = inWay;
    }

    synchronized public RailLine getRailLine() {
        return railLine;
    }

    synchronized public void setRailLine(RailLine railLine) {
        this.railLine = railLine;
    }

    public RailwaySystem getRailwaySystem() {
        return railwaySystem;
    }

    public void setRailwaySystem(RailwaySystem railwaySystem) {
        this.railwaySystem = railwaySystem;
    }
}
