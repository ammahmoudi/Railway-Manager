package com.amg.railwaymanager;

import java.util.LinkedList;
import java.util.Queue;

public class RailLine {
    int id;
    boolean inUse = false;
    Train train;
    Queue<Train> waitingQueue;
    LinkedList<Train> trains;
    RailwaySystem railwaySystem;
    boolean oneDirection;

    public RailLine(int id, RailwaySystem railwaySystem, boolean oneDirection) {

        this.id = id;
        this.railwaySystem = railwaySystem;
        this.oneDirection = oneDirection;
        trains = new LinkedList<>();
    }

    synchronized public boolean isInUse() {
        return inUse;
    }

    synchronized public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    synchronized public Train getTrain() {
        return train;
    }

    synchronized public void setTrain(Train train) {
        this.train = train;
    }

    synchronized public void set(Train train) {
        if (isOneDirection()) {
            train.railLine=this;
            train.setInWay(true);
            this.trains.add(train);
            inUse=true;
        } else {

            if (!inUse && this.train != (train)) {
                train.railLine = this;
                train.setInWay(true);
                this.train = train;
                inUse = true;
            }
        }
    }

    synchronized public void free(Train target) {
        if(isOneDirection()&&target!=null){
            target.railLine = null;
            target.setInWay(false);
            trains.remove(target);
            inUse = false;
        }else{
        if (isInUse() && train != null) {
            train.railLine = null;
            train.setInWay(false);
            this.train = null;
            setInUse(false);
        }
        }
    }

    public boolean isOneDirection() {
        return oneDirection;
    }

    public void setOneDirection(boolean oneDirection) {
        this.oneDirection = oneDirection;
    }

    public Queue<Train> getWaitingQueue() {
        return waitingQueue;
    }

    public void setWaitingQueue(Queue<Train> waitingQueue) {
        this.waitingQueue = waitingQueue;
    }

    public LinkedList<Train> getTrains() {
        return trains;
    }

    public void setTrains(LinkedList<Train> trains) {
        this.trains = trains;
    }
}
