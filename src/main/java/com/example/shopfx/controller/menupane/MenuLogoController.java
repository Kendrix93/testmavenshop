package com.example.shopfx.controller.menupane;

import com.example.shopfx.constants.ImageConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuLogoController implements Initializable {

    @FXML
    private ImageView imageView;


    @Override public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(ImageConstants.BAUSCH_LOMB_VECTOR_LOGO_PNG);
        imageView.setImage(image);
    }
}
