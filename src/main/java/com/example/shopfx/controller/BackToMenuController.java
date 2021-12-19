package com.example.shopfx.controller;

import com.example.shopfx.utility.SceneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.shopfx.constants.ImageConstants.X_ICON_PNG;

public class BackToMenuController implements Initializable {


    @FXML
    private Button closeButton;

    private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image closeImage = new Image(X_ICON_PNG);
        imageView = new ImageView(closeImage);
        imageView.setFitHeight(10);
        imageView.setFitWidth(10);
        closeButton.setGraphic(imageView);

        closeButton.setOnAction(e -> new SceneService().closeApplication(e));
        closeButton.setOnMouseEntered(event -> resizeBigger());
        closeButton.setOnMouseExited(event -> resizeNormal());
    }

    @FXML
    private void resizeBigger(){
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
    }

    @FXML
    private void resizeNormal(){
        imageView.setFitHeight(10);
        imageView.setFitWidth(10);
    }
}
