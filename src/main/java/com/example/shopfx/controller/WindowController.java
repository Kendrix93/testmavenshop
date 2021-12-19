package com.example.shopfx.controller;

import com.example.shopfx.utility.SceneService;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public abstract class WindowController {

    protected void setWindow(AnchorPane anchorPane, Pane topPane, Button cancelButton){
        SceneService sceneService = new SceneService();

        if(topPane != null) {
            sceneService.setPane(topPane, "top-pane.fxml");
        }

        if(anchorPane != null) {
            sceneService.addDraggableNode(anchorPane);
        }

        if(cancelButton != null){
            cancelButton.setOnAction(sceneService::closeApplication);
        }

    }

}
