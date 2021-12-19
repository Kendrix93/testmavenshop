package com.example.shopfx.controller.menupane;

import com.example.shopfx.constants.ImageConstants;
import com.example.shopfx.constants.ViewConstants;
import com.example.shopfx.utility.SceneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuUserController implements Initializable {


    @FXML
    private ImageView imageView;

    @FXML
    private Button manageUserButton;

    @Override public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(ImageConstants.BAUSCH_LOMB_VECTOR_LOGO_PNG);
        imageView.setImage(image);
        SceneService sceneService = new SceneService();
        manageUserButton.setOnAction(e-> sceneService.openInNewWindow(ViewConstants.USER_ACTIONS_VIEW));

    }
}
