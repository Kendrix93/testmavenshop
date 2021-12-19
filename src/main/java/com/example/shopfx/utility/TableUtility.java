package com.example.shopfx.utility;

import com.example.shopfx.datamodel.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.List;

public class TableUtility {


    public void fillTable(TableColumn<Item, String> itemName, TableColumn<Item, String> itemCode, TableColumn<Item, BigDecimal> itemPrice, TableColumn<Item, String> itemType, TableColumn<Item, String> itemQuantity, List<Item> itemList, TableView<Item> itemTable) {
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        ObservableList<Item> observableListItem = FXCollections.observableList(itemList);
        itemTable.setItems(observableListItem);
    }

}
