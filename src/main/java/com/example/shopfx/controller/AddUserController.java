package com.example.shopfx.controller;

import com.example.shopfx.exceptions.InvalidValueException;
import com.example.shopfx.exceptions.ObjectExistException;
import com.example.shopfx.persistence.DataPersistenceService;
import com.example.shopfx.validation.UserValidation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController extends WindowController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField repeatPasswordField;

    @FXML
    private Pane topPane;

    @FXML
    private Label successLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label failLabel;

    @Override public void initialize(URL location, ResourceBundle resources) {
        setWindow(anchorPane, topPane, cancelButton);
    }

    @FXML
    private void addUser() {
        String userLogin = loginField.getText();
        String passwordMain = passwordField.getText();
        String passwordRepeated = repeatPasswordField.getText();
        String messageSuccess = "User created!";
        String messageFail = "";
        try {
            UserValidation.validateUser(userLogin, passwordMain, passwordRepeated);
            new DataPersistenceService().createUser(userLogin, passwordMain);
        } catch (ObjectExistException | InvalidValueException e) {
            messageFail = e.getMessage();
            messageSuccess = "";
        }
        failLabel.setText(messageFail);
        successLabel.setText(messageSuccess);
    }

}
