package com.example.shopfx.controller;

import com.example.shopfx.datamodel.ItemType;
import com.example.shopfx.exceptions.InvalidValueException;
import com.example.shopfx.exceptions.ObjectExistException;
import com.example.shopfx.persistence.DataPersistenceService;
import com.example.shopfx.validation.ItemValidation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController extends WindowController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField codeField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private ChoiceBox<ItemType> itemTypeChoiceBox;

    @FXML
    private Label successLabel;

    @FXML
    private Label failLabel;

    @FXML
    private Pane pane;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWindow(anchorPane, pane, cancelButton);
        itemTypeChoiceBox.getItems().addAll(ItemType.getAllItemType());
    }

    @FXML
    public void addItem(){
        String itemName = nameField.getText();
        String code = codeField.getText();
        String priceString = priceField.getText();
        ItemType itemTypeValue = itemTypeChoiceBox.getValue();
        String quantityFieldText = quantityField.getText();
        int quantity = Integer.parseInt(quantityFieldText);
        String messageSuccess = "Item created!";
        String messageFail = "";
        try {
            new ItemValidation().validateItem(itemName, code, priceString, itemTypeValue);
            long priceLong = Long.parseLong(priceString);
            BigDecimal bigDecimalPrice = BigDecimal.valueOf(priceLong);
            new DataPersistenceService().createItem(code, itemName, bigDecimalPrice, itemTypeValue, quantity);
        } catch (ObjectExistException | InvalidValueException e) {
            messageFail = e.getMessage();
            messageSuccess = "";
        }
        failLabel.setText(messageFail);
        successLabel.setText(messageSuccess);
    }

    @FXML
    private void clean(){
        nameField.setText("");
        codeField.setText("");
        priceField.setText("");
        quantityField.setText("");
        itemTypeChoiceBox.setValue(null);
    }

}
