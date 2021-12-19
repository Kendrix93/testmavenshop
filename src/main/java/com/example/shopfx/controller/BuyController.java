package com.example.shopfx.controller;

import com.example.shopfx.constants.ViewConstants;
import com.example.shopfx.datamodel.Item;
import com.example.shopfx.exceptions.ObjectExistException;
import com.example.shopfx.persistence.DataFetcher;
import com.example.shopfx.persistence.DataHolder;
import com.example.shopfx.persistence.DataPersistenceService;
import com.example.shopfx.utility.SceneService;
import com.example.shopfx.utility.SimpleAlarm;
import com.example.shopfx.utility.TableUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BuyController extends WindowController implements Initializable {


    @FXML
    private Button addToCartButton;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Item, String> itemName;

    @FXML
    private TableColumn<Item, String> itemCode;

    @FXML
    private TableColumn<Item, BigDecimal> itemPrice;

    @FXML
    private TableColumn<Item, String> itemType;

    @FXML
    private TableColumn<Item, String> itemQuantity;

    @FXML
    private Pane topPane;

    @FXML
    private TextField quantityBuyTextField;

    @FXML
    private Label infoLabel;

    @FXML
    private Button cartButton;

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane anchorPane;

    private final SceneService sceneService = new SceneService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWindow(anchorPane, topPane, null);

        Map<String, Item> itemsMap = DataHolder.getItemsMap();
        List<Item> values = new ArrayList<>(itemsMap.values());
        new TableUtility().fillTable(itemName, itemCode, itemPrice, itemType, itemQuantity, values, itemTable);

        sceneService.setButtonToChangeScene(cartButton, ViewConstants.CART_VIEW);
        sceneService.setButtonToChangeScene(backButton, ViewConstants.MAIN_MENU_VIEW);
        addToCartButton.setOnAction(this::addItemToCart);
    }

    @FXML
    private void addItemToCart(ActionEvent actionEvent) {
        Item itemFromTable = itemTable.getSelectionModel().getSelectedItem();

        if (itemFromTable == null) {
            infoLabel.setText("Nothing is selected!");
        } else {
            infoLabel.setText("");
            String quantityToBuyString = quantityBuyTextField.getText();
            int quantityToBuy = Integer.parseInt(quantityToBuyString);
            DataFetcher dataFetcher = new DataFetcher();
            try {
                Item item = dataFetcher.getItem(itemFromTable.getItemCode());

                int quantity = item.getQuantity();

                if (quantity < quantityToBuy) {
                    throw new ObjectExistException("It is not possible to add more items to the cart than are available!");
                }
                item.setQuantity(quantityToBuy);
                String message = new DataPersistenceService().addItemToCart(item);
                infoLabel.setText(message);

                int newQuantity = quantity - quantityToBuy;
                item.setQuantity(newQuantity);

                DataPersistenceService dataPersistenceService = new DataPersistenceService();
                dataPersistenceService.editItem(item.getItemCode(), item);
            } catch (ObjectExistException e) {
                SimpleAlarm.showAlarm(Alert.AlertType.ERROR, e.getMessage());
            }
        }
    }


}
