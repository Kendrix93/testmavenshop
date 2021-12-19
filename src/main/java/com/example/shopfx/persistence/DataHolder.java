package com.example.shopfx.persistence;

import com.example.shopfx.constants.ViewConstants;
import com.example.shopfx.datamodel.Item;
import com.example.shopfx.datamodel.User;
import javafx.event.ActionEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHolder {

    private DataHolder() {
    }

    private static Map<String, User> usersMap = new HashMap<>();
    private static Map<String, Item> itemsMap = new HashMap<>();
    private static Map<String, List<Item>> cartItemsMap = new HashMap<>();
    private static Item itemToEdit = null;
    private static String loggedUserLogin = null;
    private static String loadingScene = null;
    private static String alarmMessage;

    public static Map<String, User> getUsersMap() {
        return new HashMap<>(usersMap);
    }

    public static void setUsersMap(Map<String, User> usersMap) {
        DataHolder.usersMap = usersMap;
    }

    public static Map<String, Item> getItemsMap() {
        return new HashMap<>(itemsMap);
    }

    public static void setItemsMap(Map<String, Item> itemsMap) {
        DataHolder.itemsMap = itemsMap;
    }

    public static Item getItemToEdit() {
        return new Item(itemToEdit);
    }

    public static void setItemToEdit(Item itemToEdit) {
        DataHolder.itemToEdit = itemToEdit;
    }


    public static Map<String, List<Item>> getCartItemsMap() {
        return new HashMap<>(cartItemsMap);
    }

    public static void setCartItemsMap(Map<String, List<Item>> cartItemsMap) {
        DataHolder.cartItemsMap = cartItemsMap;
    }

    public static String getLoggedUserLogin() {
        return loggedUserLogin;
    }

    public static void setLoggedUserLogin(String loggedUserLogin) {
        DataHolder.loggedUserLogin = loggedUserLogin;
    }

    public static String getLoadingScene() {
        if(loadingScene == null){
            return ViewConstants.ITEM_VIEW;
        }
        return loadingScene;
    }

    public static void setLoadingScene(String loadingScene) {
        DataHolder.loadingScene = loadingScene;
    }

    public static String getAlarmMessage() {
        return alarmMessage;
    }

    public static void setAlarmMessage(String alarmMessage) {
        DataHolder.alarmMessage = alarmMessage;
    }
}
