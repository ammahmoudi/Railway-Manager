package com.amg.railwaymanager;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea console_textArea;

    static private TextArea universalConsole_textArea;

    @FXML
    private TextField oneD_railLineNumbers_input;

    @FXML
    private TextField twoD_railLineNumbers_input;


    @FXML
    private TilePane railLines_tilePane;

    @FXML
    private Button startButton;

    @FXML
    private TextField trainNumbers_input;

    @FXML
    private VBox trains_Vbox;
    @FXML
    private Button randomFillButton;

    @FXML
    private Button startAllButton;
    private static RailwaySystem railwaySystem;
    private static RailLineView[] railLineViews;
    private static TrainView[] trainViews;

    @FXML
    void onRandomFillButtonAction(ActionEvent event) {
randomFill();
    }

    @FXML
    void onStartAllButtonAction(ActionEvent event) {
startAll();
    }
    @FXML
    void onStartButtonAction(ActionEvent event) {
        if(!oneD_railLineNumbers_input.getText().equals("")&&!twoD_railLineNumbers_input.getText().equals("")&&!trainNumbers_input.getText().equals("")){
        int oneD_num=Integer.parseInt(oneD_railLineNumbers_input.getText());
        int twoD_num=Integer.parseInt(oneD_railLineNumbers_input.getText());
        int train_num=Integer.parseInt(trainNumbers_input.getText());
        String s;

        railwaySystem = new RailwaySystem(oneD_num,twoD_num, train_num);
        railLineViews = new RailLineView[railwaySystem.railLines.length];
        trainViews = new TrainView[railwaySystem.trains.length];
        railwaySystem.start();
        initializeRailLineViews(railwaySystem.railLines);
        initializeTrainViews(railwaySystem.trains);
        startAllButton.setDisable(false);
        startButton.setDisable(true);
        randomFillButton.setDisable(false);
        }else{
            MainController.consoleLog("Bad input");
        }

    }

    @FXML
    void initialize() {
        assert console_textArea != null : "fx:id=\"console_textArea\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert railLines_tilePane != null : "fx:id=\"railLines_tilePane\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert trainNumbers_input != null : "fx:id=\"trainNumbers_input\" was not injected: check your FXML file 'hello-view.fxml'.";
        universalConsole_textArea = console_textArea;
       // console_textArea.setStyle("text-area-background:black;");

    }

    public void randomFill() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < trainViews.length; i++) {
            int randId = random.nextInt(railwaySystem.railLines.length);
            int randDuration = random.nextInt(10000);
            trainViews[i].getDuration_input().setText(String.valueOf(randDuration));
            trainViews[i].getRailId_input().setText(String.valueOf(randId));
        }
    }
    public void startAll() {

        for (int i = 0; i < trainViews.length; i++) {
           trainViews[i].getStartButton().fire();
        }
    }

    public void initializeRailLineViews(RailLine[] railLines) {
        System.out.println(railLines.length);
        railLines_tilePane.getChildren().clear();
        for (int i = 0; i < railLines.length; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("railLine-view.fxml"));
                Node node = fxmlLoader.load();
                RailLineView railLineView = fxmlLoader.getController();
                railLineViews[i] = railLineView;
                railLineView.setRailLine(railLines[i]);
                railLineView.update();
                railLines_tilePane.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void initializeTrainViews(Train[] trains) {
        trains_Vbox.getChildren().clear();
        for (int i = 0; i < trains.length; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("train-view.fxml"));
                Node node = fxmlLoader.load();
                TrainView trainView = fxmlLoader.getController();
                trainViews[i] = trainView;
                trainView.setTrain(trains[i]);
                trainView.update();
                trains_Vbox.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static RailwaySystem getRailwaySystem() {
        return railwaySystem;
    }

    public static RailLineView[] getRailLineViews() {
        return railLineViews;
    }

    public static TrainView[] getTrainViews() {
        return trainViews;
    }

    public static TextArea getConsole_textArea() {
        return universalConsole_textArea;
    }

    public static void consoleLog(String s) {
        universalConsole_textArea.appendText( "\n" + s);
    }
}
