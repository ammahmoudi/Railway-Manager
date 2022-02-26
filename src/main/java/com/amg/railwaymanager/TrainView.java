package com.amg.railwaymanager;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class TrainView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox driver_VBox;

    @FXML
    private TextField duration_input;

    @FXML
    private HBox mainHbox;

    @FXML
    private VBox name_VBox;

    @FXML
    private Label name_label;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private TextField railId_input;

    @FXML
    private Button startButton;

    @FXML
    private Label status_label;
    private Train train;
    Timeline timeline;

    @FXML
    void onStartButtonAction(ActionEvent event) {

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        update();
                        MainController.getRailLineViews()[Integer.parseInt(railId_input.getText())].update();
                    }
                });

                return null;
            }
        };
        boolean hasGone=train.railwaySystem.request(train.id, Integer.parseInt(railId_input.getText()), Integer.parseInt(duration_input.getText()), task);

        RailLineView railLineView = MainController.getRailLineViews()[Integer.parseInt(railId_input.getText())];
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressIndicator.progressProperty(), 0)),
                new KeyFrame(Duration.millis(Double.parseDouble(duration_input.getText())), e -> {
                    // do anything you need here on completion...
                }, new KeyValue(progressIndicator.progressProperty(), 1))
        );
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                railId_input.setDisable(false);
            }
        });
        railLineView.setTimeline(new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(railLineView.getProgressIndicator().progressProperty(), 0)),
                new KeyFrame(Duration.millis(Double.parseDouble(duration_input.getText())), e -> {
                    // do anything you need here on completion...
                }, new KeyValue(railLineView.getProgressIndicator().progressProperty(), 1))
        ));

        timeline.setCycleCount(1);
        railLineView.getTimeline().setCycleCount(1);
        if(hasGone){
        timeline.play();
        railLineView.getTimeline().play();}else {
            railId_input.setDisable(true);
        }
        update();
        MainController.getRailLineViews()[Integer.parseInt(railId_input.getText())].update();
    }



    @FXML
    void initialize() {
        assert driver_VBox != null : "fx:id=\"driver_VBox\" was not injected: check your FXML file 'train-view.fxml'.";
        assert duration_input != null : "fx:id=\"duration_input\" was not injected: check your FXML file 'train-view.fxml'.";
        assert mainHbox != null : "fx:id=\"mainHbox\" was not injected: check your FXML file 'train-view.fxml'.";
        assert name_VBox != null : "fx:id=\"name_VBox\" was not injected: check your FXML file 'train-view.fxml'.";
        assert name_label != null : "fx:id=\"name_label\" was not injected: check your FXML file 'train-view.fxml'.";
        assert progressIndicator != null : "fx:id=\"progressIndicator\" was not injected: check your FXML file 'train-view.fxml'.";
        assert railId_input != null : "fx:id=\"railId_input\" was not injected: check your FXML file 'train-view.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'train-view.fxml'.";
        assert status_label != null : "fx:id=\"status_label\" was not injected: check your FXML file 'train-view.fxml'.";

    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void update() {

        name_label.setText(train.getName());
        if (train.isInWay() && train.getRailLine() != null) {

            status_label.setText("in Line " + train.getRailLine().id);
            if (mainHbox.getChildren().contains(driver_VBox))
                mainHbox.getChildren().remove(driver_VBox);
            if (!mainHbox.getChildren().contains(progressIndicator))
                mainHbox.getChildren().add(progressIndicator);
        } else {

            if (!mainHbox.getChildren().contains(driver_VBox))
                mainHbox.getChildren().add(driver_VBox);
            if (mainHbox.getChildren().contains(progressIndicator))
                mainHbox.getChildren().remove(progressIndicator);
            status_label.setText("Waiting");
        }
    }

    public TextField getDuration_input() {
        return duration_input;
    }

    public TextField getRailId_input() {
        return railId_input;
    }

    public Button getStartButton() {
        return startButton;
    }
}
