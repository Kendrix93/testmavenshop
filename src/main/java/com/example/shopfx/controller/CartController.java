package com.example.shopfx.controller;


import com.example.shopfx.constants.ViewConstants;
import com.example.shopfx.datamodel.Item;
import com.example.shopfx.exceptions.ObjectExistException;
import com.example.shopfx.persistence.DataHolder;
import com.example.shopfx.persistence.FileWriter;
import com.example.shopfx.utility.SceneService;
import com.example.shopfx.utility.SimpleAlarm;
import com.example.shopfx.utility.TableUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class CartController extends WindowController implements Initializable {

    public static final String NOTHING_IS_SELECTED = "Nothing is selected";
    @FXML
    private Button goToShopButton;

    @FXML
    private Button deleteButton;

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
    private Label errorLabel;

    @FXML
    private Pane topPane;

    @FXML
    private Button buyButton;

    @FXML
    private Button minusButton;

    @FXML
    private Button plusButton;

    @FXML
    private Label priceLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button backButton;

    private List<Item> itemList;

    private final SceneService sceneService = new SceneService();

    @Override public void initialize(URL location, ResourceBundle resources) {
        setWindow(anchorPane, topPane, null);
        loadTable();

        plusButton.setOnAction(event -> plusButtonAction());
        minusButton.setOnAction(event -> minusButtonAction());
        deleteButton.setOnAction(event -> deleteItem());
        buyButton.setOnAction(event -> buyItemsAction());

        sceneService.setButtonToChangeScene(goToShopButton, ViewConstants.BUY_ITEM_VIEW);
        sceneService.setButtonToChangeScene(backButton, ViewConstants.MAIN_MENU_VIEW);

        updatePrice();
    }

    private void loadTable() {
        Map<String, List<Item>> itemsCartMap = DataHolder.getCartItemsMap();
        Optional<List<Item>> items = Optional.ofNullable(itemsCartMap.get(DataHolder.getLoggedUserLogin()));
        itemList = items.orElseGet(ArrayList::new);
        new TableUtility().fillTable(itemName, itemCode, itemPrice, itemType, itemQuantity, itemList, itemTable);
    }


    private void plusButtonAction() {
        Item selectedItem = itemTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            errorLabel.setText(NOTHING_IS_SELECTED);
        } else {
            errorLabel.setText("");
            Map<String, Item> itemsMap = DataHolder.getItemsMap();
            Item item = itemsMap.get(selectedItem.getItemCode());

            int storageItemQuantity = item.getQuantity();

            if (storageItemQuantity > 0) {
                item.setQuantity(storageItemQuantity - 1);
                selectedItem.setQuantity(selectedItem.getQuantity() + 1);
            } else {
                errorLabel.setText("Out of stock");
            }
            refreshWindow();
        }
    }

    private void minusButtonAction() {
        Item selectedItem = itemTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            errorLabel.setText(NOTHING_IS_SELECTED);
        } else {
            errorLabel.setText("");
            Map<String, Item> itemsMap = DataHolder.getItemsMap();
            Item item = itemsMap.get(selectedItem.getItemCode());

            int storageItemQuantity = item.getQuantity();
            int selectedItemQuantity = selectedItem.getQuantity();
            if (selectedItemQuantity > 1) {
                item.setQuantity(storageItemQuantity + 1);
                selectedItem.setQuantity(selectedItemQuantity - 1);
            } else if (selectedItemQuantity == 1) {
                deleteItem();
            }
            refreshWindow();
        }
    }


    private void deleteItem() {
        Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
        String userLogin = DataHolder.getLoggedUserLogin();

        if (selectedItem != null) {
            Map<String, Item> itemsMap = DataHolder.getItemsMap();
            Item item = itemsMap.get(selectedItem.getItemCode());
            int newQuantity = item.getQuantity() + selectedItem.getQuantity();
            item.setQuantity(newQuantity);
            itemsMap.put(item.getItemCode(), item);
            DataHolder.setItemsMap(itemsMap);

            try {
                new FileWriter().saveItemsToFile();
            } catch (ObjectExistException e) {
                errorLabel.setText("Something went wrong!!!!");
            }

            itemList.remove(selectedItem);
            Map<String, List<Item>> cartItemsMap = DataHolder.getCartItemsMap();
            cartItemsMap.put(userLogin, itemList);
            DataHolder.setCartItemsMap(cartItemsMap);
            itemTable.refresh();
            refreshWindow();
        } else {
            errorLabel.setText(NOTHING_IS_SELECTED);
        }
    }

    private void buyItemsAction() {
        updatePrice();
        String finalPrice = priceLabel.getText();
        if (!finalPrice.equals("0.0")) {
            FileWriter fileWriter = new FileWriter();
            fileWriter.createRecipeDocument(finalPrice);
            DataHolder.setCartItemsMap(new HashMap<>());
            refreshWindow();
            SimpleAlarm.showAlarm(Alert.AlertType.INFORMATION, "Thank you for shopping in our store. You will find the receipt in the 'recipes' folder.");
        } else {
            errorLabel.setText("Cart is empty!!!");
        }
    }

    private void updatePrice() {
        Map<String, List<Item>> cartItemsMap = DataHolder.getCartItemsMap();
        List<Item> items = cartItemsMap.get(DataHolder.getLoggedUserLogin());
        if (items != null) {
            BigDecimal price = BigDecimal.valueOf(0);

            for (Item item : items) {
                price = price.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
            priceLabel.setText(price.toString());
        } else {
            priceLabel.setText("0.0");
        }
    }

    private void refreshWindow() {
        updatePrice();
        loadTable();
        itemTable.refresh();
    }

}
