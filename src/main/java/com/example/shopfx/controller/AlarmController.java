package com.example.shopfx.controller;

import com.example.shopfx.persistence.DataHolder;
import com.example.shopfx.utility.SceneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AlarmController implements Initializable {

    @FXML
    private Label infoLabel;

    @FXML
    private Button okButton;

    @Override public void initialize(URL location, ResourceBundle resources) {
        String alarmMessage = DataHolder.getAlarmMessage();
        infoLabel.setText(alarmMessage);
        okButton.setOnAction(e-> new SceneService().closeApplication(e));
    }
}
