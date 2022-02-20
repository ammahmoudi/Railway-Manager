package com.amg.railwaymanager;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class RailLineView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label name_label;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label status_label;
    private RailLine railLine;
    private Circle circle;
    private Timeline timeline;

    @FXML
    void initialize() {
        assert name_label != null : "fx:id=\"name_label\" was not injected: check your FXML file 'railLine-view.fxml'.";
        assert progressIndicator != null : "fx:id=\"progressIndicator\" was not injected: check your FXML file 'railLine-view.fxml'.";
        assert status_label != null : "fx:id=\"status_label\" was not injected: check your FXML file 'railLine-view.fxml'.";
      circle=new Circle();
        circle.setRadius(32);
        circle.setFill(Color.GREEN);
        circle.setStroke(null);

    }

    public RailLine getRailLine() {
        return railLine;
    }

    public void setRailLine(RailLine railLine) {
        this.railLine = railLine;
    }

    public void update() {
        if (railLine != null) {

            if (railLine.isOneDirection()) {
                mainBorderPane.setStyle("-fx-background-color: #42a82b");
                name_label.setText("1D Rail Line " + railLine.id);
                mainBorderPane.setBottom(null);
                mainBorderPane.setCenter(status_label);

                if(railLine.isInUse()){
                    String string="";
                    for(Train train:railLine.getTrains()){
                        string=string.concat(train.getName()+"\n");
                    }
                    status_label.setText(string + "is on Line");

                }else{
                    status_label.setText("Free");
                }

            } else {
                mainBorderPane.setStyle("-fx-background-color: #e1a04a");
                name_label.setText("2D Rail Line " + railLine.id);
                if (railLine.isInUse() && railLine.getTrain() != null) {
                    status_label.setText(railLine.train.getName() + "is on Line");
                    mainBorderPane.setCenter(progressIndicator);
                    progressIndicator.setVisible(true);
                } else {
                    status_label.setText("Free");
                    progressIndicator.setVisible(false);
                    mainBorderPane.setCenter(circle);
                }
            }
        }
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }


    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
}
