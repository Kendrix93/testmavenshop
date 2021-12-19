package com.example.shopfx.controller;

import com.example.shopfx.constants.ImageConstants;
import com.example.shopfx.constants.ViewConstants;
import com.example.shopfx.exceptions.ObjectExistException;
import com.example.shopfx.persistence.DataFetcher;
import com.example.shopfx.persistence.DataHolder;
import com.example.shopfx.utility.SceneService;
import com.example.shopfx.utility.SimpleAlarm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController extends WindowController implements Initializable {

    @FXML
    private ImageView companyImage;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button createAccountButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane topPane;


    @Override public void initialize(URL location, ResourceBundle resources) {
        setWindow(anchorPane, topPane, null);
        SceneService sceneService = new SceneService();

        createAccountButton.setOnAction(e-> sceneService.openInNewWindow(ViewConstants.ADD_NEW_USER_VIEW));

        Image arrowImage = new Image(ImageConstants.BLACK_RIGHT_ARROW_PNG);
        ImageView imageArrowView = new ImageView(arrowImage);
        imageArrowView.setFitWidth(30);
        imageArrowView.setFitHeight(30);
        loginButton.setGraphic(imageArrowView);

        Image image = new Image(ImageConstants.BAUSCH_LOMB_VECTOR_LOGO_PNG);
        companyImage.setImage(image);

        loginField.setPromptText("Użytkownik");
    }

    @FXML
    protected void loginAction(ActionEvent actionEvent) {
        try {
            String login = loginField.getText();
            new DataFetcher().getUser(login, passwordField.getText());
            DataHolder.setLoggedUserLogin(login);
            new SceneService().changeToScene(actionEvent, "main-menu-view.fxml");
        } catch (ObjectExistException e) {
            SimpleAlarm.showAlarm(Alert.AlertType.INFORMATION, e.getMessage());
        }
    }
}
