package com.example.shopfx.controller;

import com.example.shopfx.constants.ImageConstants;
import com.example.shopfx.constants.ViewConstants;
import com.example.shopfx.persistence.DataHolder;
import com.example.shopfx.utility.SceneService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends WindowController implements Initializable {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane mainPane;

    @FXML
    private Circle imageCircle;

    @FXML
    private Label loginLabel;

    @FXML
    private Pane topPane;

    private final SceneService sceneService = new SceneService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWindow(anchorPane, topPane, null);
        sceneService.setPane(mainPane, ViewConstants.LOGO_PANE_VIEW_FXML);
        setUserCircle();
    }

    private void setUserCircle(){
        String loggedUserLogin = DataHolder.getLoggedUserLogin();
        loginLabel.setText(loggedUserLogin);
        loginLabel.setOnMouseClicked(e -> sceneService.openInNewWindow(ViewConstants.USER_VIEW_FXML));

        Image image = new Image(ImageConstants.HELMET_OF_AWE_PNG, false);
        imageCircle.setStroke(Color.DARKRED);
        imageCircle.setRadius(70d);
        imageCircle.setFill(new ImagePattern(image));
        imageCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.PALEVIOLETRED));
        imageCircle.setOnMouseClicked(e -> sceneService.openInNewWindow(ViewConstants.USER_VIEW_FXML));
        imageCircle.setOnMouseEntered(event -> imageCircle.setRadius(72d));
        imageCircle.setOnMouseExited(event -> imageCircle.setRadius(70d));

        loginLabel.setOnMouseEntered(event -> imageCircle.setRadius(72d));
        loginLabel.setOnMouseExited(event -> imageCircle.setRadius(70d));
    }

    @FXML
    private void goToCardView(ActionEvent actionEvent) {
        sceneService.changeToScene(actionEvent, ViewConstants.CART_VIEW);
    }

    @FXML
    private void goToBuyView(ActionEvent actionEvent) {
        sceneService.changeToScene(actionEvent, ViewConstants.BUY_ITEM_VIEW);
    }

    @FXML
    private void goToEditItemView(ActionEvent actionEvent) {
        sceneService.setPane(mainPane, ViewConstants.ITEM_MENU_VIEW_FXML);
    }

    @FXML
    private void goToEditUserView(ActionEvent actionEvent) {
        sceneService.setPane(mainPane, ViewConstants.USER_MENU_VIEW_FXML);
    }

    @FXML
    private void goToLoginView(ActionEvent actionEvent) {
        DataHolder.setLoggedUserLogin("");
        sceneService.changeToScene(actionEvent, ViewConstants.LOGIN_VIEW);
    }

}
