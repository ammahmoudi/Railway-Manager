package com.amg.railwaymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 740);
        stage.setTitle("Railway Manager");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
            launch();
//        RailwaySystem railwaySystem=new RailwaySystem(5,5);
//        railwaySystem.start();
//       railwaySystem.request(0,1,10000);
//        railwaySystem.request(1,0,4000);
//        railwaySystem.request(2,4,4000);
//        railwaySystem.request(3,2,4000);
//        railwaySystem.request(4,2,4000);
////        railwaySystem.request(1,1,4000);
////        railwaySystem.request(1,1,4000);
//        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//           //     railwaySystem.request(1,1,4000);
//            }
//        },0,1000, TimeUnit.MILLISECONDS);
    }
}