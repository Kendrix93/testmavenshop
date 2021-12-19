package com.example.shopfx.controller;

import com.example.shopfx.constants.ImageConstants;
import com.example.shopfx.persistence.DataHolder;
import com.example.shopfx.utility.SceneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedUserWindowController extends WindowController implements Initializable {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane topPane;

    @FXML
    private Button cancelButton;

    @FXML
    private Label loginLabel;

    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWindow(anchorPane, topPane, cancelButton);

        String loggedUserLogin = DataHolder.getLoggedUserLogin();
        loginLabel.setText(loggedUserLogin);

        Image image = new Image(ImageConstants.HELMET_OF_AWE_PNG, false);
        imageView.setImage(image);
    }
}
