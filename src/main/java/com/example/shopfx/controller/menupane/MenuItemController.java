package com.example.shopfx.controller.menupane;

import com.example.shopfx.constants.ImageConstants;
import com.example.shopfx.utility.SceneService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuItemController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Button manageItemButton;

    @FXML
    private Button addItemButton;

    @Override public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(ImageConstants.BAUSCH_LOMB_VECTOR_LOGO_PNG);
        imageView.setImage(image);

        SceneService sceneService = new SceneService();
        manageItemButton.setOnAction(e-> sceneService.changeToSceneWithLoading("item-actions-view.fxml"));
        addItemButton.setOnAction(e-> sceneService.openInNewWindow("add-new-item.fxml"));
    }
}
