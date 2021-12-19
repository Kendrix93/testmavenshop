package com.example.shopfx.persistence;

import com.example.shopfx.constants.FilesConstants;
import com.example.shopfx.datamodel.Item;
import com.example.shopfx.datamodel.User;
import com.example.shopfx.exceptions.ObjectExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileWriter {

    private static final String USER_TITLES = "LOGIN;PASSWORD;";
    private static final String ITEM_TITLES = "ITEM TYPE;ITEM CODE;ITEM NAME;ITEM PRICE;QUANTITY;";
    private static final String ADMIN = "admin;admin;";
    private static final String DELIMITER = ";";
    private static final String ERROR = "Error";
    private static final String CANNOT_SAVE_CHANGES = "Cannot save changes!";
    private static final String CANNOT_FIND_FILE_TO_SAVE_CHANGES = "Cannot find file to save changes!";
    private static final String CANNOT_FIND_FILE = "Cannot find file";

    public void saveUsersToFile() throws ObjectExistException {
        try {
            PrintWriter printWriter = new PrintWriter(FilesConstants.USER_CSV_FILE_PATH);
            printWriter.println(USER_TITLES);

            Map<String, User> userMap = DataHolder.getUsersMap();

            if (userMap.isEmpty()) {
                printWriter.println(ADMIN);
            }

            userMap.values().forEach(user -> {
                String userAsString = user.getLogin() +
                        DELIMITER +
                        user.getPassword() +
                        DELIMITER;
                printWriter.println(userAsString);
            });
            printWriter.close();
            saveUserToXML(new ArrayList<>(userMap.values()));
        }catch (FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR);
            alert.setHeaderText(CANNOT_SAVE_CHANGES);
            alert.setContentText(CANNOT_FIND_FILE +  FilesConstants.USER_CSV_FILE_NAME);
            alert.show();
            throw new ObjectExistException(CANNOT_FIND_FILE_TO_SAVE_CHANGES);
        }
    }

    public void saveItemsToFile() throws ObjectExistException {
        try {
            PrintWriter printWriter = new PrintWriter(FilesConstants.ITEM_CSV_FILE_PATH);
            printWriter.println(ITEM_TITLES);
            Map<String, Item> itemMap = DataHolder.getItemsMap();
            itemMap.values().forEach(item -> {
                String itemAsString = item.getItemType() +
                        DELIMITER +
                        item.getItemCode() +
                        DELIMITER +
                        item.getItemName() +
                        DELIMITER +
                        item.getPrice() +
                        DELIMITER +
                        item.getQuantity() +
                        DELIMITER;
                printWriter.println(itemAsString);
            });
            printWriter.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR);
            alert.setHeaderText(CANNOT_SAVE_CHANGES);
            alert.setContentText(CANNOT_FIND_FILE +  FilesConstants.ITEM_CSV_FILE_NAME);
            alert.show();
            throw new ObjectExistException(CANNOT_FIND_FILE_TO_SAVE_CHANGES);
        }

    }


    public void createRecipeDocument(String finalPrice) {
        String user = DataHolder.getLoggedUserLogin();
        String fileName = "Recipe_Shop_XK_" + user + "_" + System.currentTimeMillis() + ".csv";
        try {
            PrintWriter printWriter = new PrintWriter("recipes/" + fileName);
            printWriter.println(";Recipe");
            printWriter.println("; for " + user);
            printWriter.println(";");
            printWriter.println(ITEM_TITLES);
            Map<String, List<Item>> cartItemsMap = DataHolder.getCartItemsMap();
            List<Item> items = cartItemsMap.get(user);
            items.forEach(item -> {
                String itemAsString = item.getItemType() +
                        DELIMITER +
                        item.getItemCode() +
                        DELIMITER +
                        item.getItemName() +
                        DELIMITER +
                        item.getPrice() +
                        DELIMITER +
                        item.getQuantity() +
                        DELIMITER;
                printWriter.println(itemAsString);
            });

            printWriter.println("");
            printWriter.println(";;; Final price :;" + finalPrice);
            printWriter.close();
            cartItemsMap.remove(user);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR);
            alert.setHeaderText(CANNOT_SAVE_CHANGES);
            alert.setContentText(CANNOT_FIND_FILE + FilesConstants.ITEM_CSV_FILE_NAME);
            alert.show();
        }

    }


    private void saveUserToXML(List<User> userList){
        ObjectMapper objectMapper = new XmlMapper();
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File("files/UsersXML.xml"), userList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
