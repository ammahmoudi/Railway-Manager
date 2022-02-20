package com.amg.railwaymanager;

import javafx.concurrent.Task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RailwaySystem {
    RailLine[] railLines;
    Train[] trains;


    public RailwaySystem(int oneDirectionRailLineNumbers,int twoDirectionRailLineNumbers, int trainNumbers) {
        railLines = new RailLine[oneDirectionRailLineNumbers+twoDirectionRailLineNumbers];
        trains = new Train[trainNumbers];

        for (int i = 0; i < twoDirectionRailLineNumbers; i++) railLines[i] = new RailLine(i, this,false);
        for (int i = twoDirectionRailLineNumbers; i < twoDirectionRailLineNumbers+oneDirectionRailLineNumbers; i++) railLines[i] = new RailLine(i, this,true);
        for (int i = 0; i < trainNumbers; i++) trains[i] = new Train(i, this);
    }

    public void start() {
        for (Train train : trains) {
            train.start();
        }
    }

    synchronized public boolean request(int trainId, int railLineId, int duration, Task task) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        RailLine railLine = railLines[railLineId];
        Train train = trains[trainId];
        if(railLine.isOneDirection()){
            railLine.set(train);
            MainController.consoleLog("Railway: Train " + trainId + " is on Line " + railLineId + " for " + duration + "ms.");
            System.out.println("Railway: Train " + trainId + " is on Line " + railLineId + " for " + duration + "ms.");
            Task task2 = new Task() {
                @Override
                protected Object call() throws Exception {
                    railLine.free(train);
                    MainController.consoleLog("Railway: Train " + trainId + " is now out of Line " + railLineId + ".");
                    System.out.println("Railway: Train " + trainId + " is now out of Line " + railLineId + ".");
                    task.run();
                    return null;
                }
            };

            executorService.schedule(task2, duration, TimeUnit.MILLISECONDS);

            return true;
        }else {
            if (!railLine.isInUse() && railLine.getTrain() != train) {
                railLine.set(train);
                MainController.consoleLog("Railway: Train " + trainId + " is on Line " + railLineId + " for " + duration + "ms.");
                System.out.println("Railway: Train " + trainId + " is on Line " + railLineId + " for " + duration + "ms.");
                Task task2 = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        railLine.free(null);
                        MainController.consoleLog("Railway: Train " + trainId + " is now out of Line " + railLineId + ".");
                        System.out.println("Railway: Train " + trainId + " is now out of Line " + railLineId + ".");
                        task.run();
                        return null;
                    }
                };

                executorService.schedule(task2, duration, TimeUnit.MILLISECONDS);

                return true;
            } else {
                MainController.consoleLog("RailWay: Request failed.Line " + railLineId + " is busy!");
                System.out.println("RailWay: Request failed.Line " + railLineId + " is busy!");
            }
        }
        return false;
    }


}
