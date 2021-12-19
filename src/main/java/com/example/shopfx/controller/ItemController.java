package com.example.shopfx.controller;

import com.example.shopfx.constants.ViewConstants;
import com.example.shopfx.custom.AutoCompleteTextField;
import com.example.shopfx.datamodel.Item;
import com.example.shopfx.exceptions.ObjectExistException;
import com.example.shopfx.persistence.DataHolder;
import com.example.shopfx.persistence.DataPersistenceService;
import com.example.shopfx.utility.SceneService;
import com.example.shopfx.utility.SimpleAlarm;
import com.example.shopfx.utility.TableUtility;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class ItemController extends WindowController implements Initializable {


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
    private AutoCompleteTextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button refreshButton;

    private final SceneService sceneService = new SceneService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Map<String, Item> itemsMap = DataHolder.getItemsMap();
        List<Item> values = new ArrayList<>(itemsMap.values());
        loadTable(values);

        setWindow(anchorPane, topPane, null);
        refreshButton.setOnAction(this::refreshTable);
        searchButton.setOnAction(this::searchItems);
        searchField.getEntries().addAll(itemsMap.keySet());
    }

    private void loadTable(List<Item> values) {
        new TableUtility().fillTable(itemName, itemCode, itemPrice, itemType, itemQuantity, values, itemTable);
        setRowHighlighted(null);
        itemTable.refresh();
    }

    private void searchItems(ActionEvent actionEvent) {
        Map<String, Item> itemsMap = DataHolder.getItemsMap();
        String text = searchField.getText();
        List<Item> searchItems;

        setRowHighlighted(text);
        itemTable.refresh();


//        searchItems = itemsMap.keySet().stream().filter(key -> key.contains(text)).map(itemsMap::get).collect(Collectors.toList());
//        loadTable(searchItems);
    }

    private void setRowHighlighted(String text){
        itemTable.setRowFactory(tv -> new TableRow<Item>() {
            @Override
            public void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else if (text == null) {
                    setStyle("");
                } else if (item.getItemCode().contains(text)) {
                    pseudoClassStateChanged(PseudoClass.getPseudoClass("highlighted"), true);
                } else {
                    setStyle("");
                }
            }
        });
    }

    private void refreshTable(ActionEvent actionEvent){
        Map<String, Item> itemsMap = DataHolder.getItemsMap();
        List<Item> values = new ArrayList<>(itemsMap.values());
        loadTable(values);
    }

    @FXML
    public void goToAddItem() {
        sceneService.openInNewWindow(ViewConstants.ADD_NEW_ITEM_VIEW);
    }

    @FXML
    public void editItem() {
        Item item = itemTable.getSelectionModel().getSelectedItem();
        if (item == null) {
            SimpleAlarm.showAlarm(Alert.AlertType.WARNING, "Nothing is selected!");
        } else {
            DataHolder.setItemToEdit(item);
            sceneService.openInNewWindow("edit-item-view.fxml");
        }
    }

    @FXML
    public void deleteItem(ActionEvent actionEvent) throws ObjectExistException {
        Item item = itemTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete item");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(String.format("Are you sure you want to delete Item %s with code %s", item.getItemName(), item.getItemCode()));

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            new DataPersistenceService().deleteItem(item);
            sceneService.changeToScene(actionEvent, ViewConstants.ITEM_VIEW);
        }
    }
}
