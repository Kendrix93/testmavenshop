package com.example.shopfx.controller;

import com.example.shopfx.persistence.DataHolder;
import com.example.shopfx.utility.SceneService;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {

    @FXML
    private Circle circleBig;

    @FXML
    private Circle circleMiddle;

    @FXML
    private Circle circleSmall;

    @FXML
    private AnchorPane anchorPane;

    private String sceneName;


    @Override public void initialize(URL location, ResourceBundle resources) {
        sceneName = DataHolder.getLoadingScene();
        setRotate(circleBig, 5000, 360);
        setRotate(circleMiddle, 10000, - 360);
        setRotate(circleSmall, 16000, 360);
        new LoadingScreen().start();
    }

    private void setRotate(Node node, double durationInMillis, double byAngle){
        RotateTransition transitionBig = new RotateTransition();
        transitionBig.setNode(node);
        transitionBig.setDuration(Duration.millis(durationInMillis));
        transitionBig.setCycleCount(Animation.INDEFINITE);
        transitionBig.setInterpolator(Interpolator.LINEAR);
        transitionBig.setByAngle(byAngle);
        transitionBig.setAxis(Rotate.Z_AXIS);
        transitionBig.play();
    }


    class LoadingScreen extends Thread{
        @Override
        public void run(){
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource(sceneName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SceneService sceneService = new SceneService();
                    Scene mainScene = new Scene(root);
                    mainScene.setFill(Color.TRANSPARENT);
                    sceneService.setStyleSheet(mainScene, sceneName);
                    Stage mainStage = (Stage )anchorPane.getScene().getWindow();
                    mainStage.setScene(mainScene);
                    mainStage.show();
                });


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
