package com.example.shopfx.controller;

import com.example.shopfx.datamodel.Item;
import com.example.shopfx.datamodel.ItemType;
import com.example.shopfx.exceptions.InvalidValueException;
import com.example.shopfx.exceptions.ObjectExistException;
import com.example.shopfx.persistence.DataHolder;
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

public class EditItemController extends WindowController implements Initializable {

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
    private AnchorPane anchorPane;

    @FXML
    private Pane topPane;

    @FXML
    private Button cancelButton;

    private String editedItemCode;


    @Override public void initialize(URL location, ResourceBundle resources) {
        setWindow(anchorPane, topPane, cancelButton);
        itemTypeChoiceBox.getItems().addAll(ItemType.getAllItemType());
        Item itemToEdit = DataHolder.getItemToEdit();
        nameField.setText(itemToEdit.getItemName());
        codeField.setText(itemToEdit.getItemCode());
        priceField.setText(itemToEdit.getPrice().toString());
        itemTypeChoiceBox.setValue(itemToEdit.getItemType());
        editedItemCode = itemToEdit.getItemCode();
        quantityField.setText(String.valueOf(itemToEdit.getQuantity()));
    }


    @FXML
    private void clean(){
        nameField.setText("");
        codeField.setText("");
        priceField.setText("");
    }

    @FXML
    void editItem() {
        String itemName = nameField.getText();
        String code = codeField.getText();
        String priceString = priceField.getText();
        String quantityFieldText = quantityField.getText();
        int quantity = Integer.parseInt(quantityFieldText);
        ItemType itemTypeValue = itemTypeChoiceBox.getValue();
        String messageSuccess = "Item created!";
        String messageFail = "";
        try {
            new ItemValidation().validateItem(itemName, code, priceString, itemTypeValue);
            long priceLong = Long.parseLong(priceString);
            BigDecimal bigDecimalPrice = BigDecimal.valueOf(priceLong);
            Item newItem = new Item(itemName, itemTypeValue, code, bigDecimalPrice, quantity);
            new DataPersistenceService().editItem(editedItemCode, newItem);
        } catch (InvalidValueException | ObjectExistException e) {
            messageFail = e.getMessage();
            messageSuccess = "";
        }
        failLabel.setText(messageFail);
        successLabel.setText(messageSuccess);
    }

}
