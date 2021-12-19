package com.example.shopfx.persistence;

import com.example.shopfx.datamodel.Item;
import com.example.shopfx.datamodel.ItemType;
import com.example.shopfx.datamodel.User;
import com.example.shopfx.exceptions.InvalidValueException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.example.shopfx.constants.FilesConstants.ITEM_CSV_FILE_PATH;
import static com.example.shopfx.constants.FilesConstants.USER_CSV_FILE_PATH;

public class FileReader {



    public void readItemsFromFile() {
        Map<String, Item> itemMap = new HashMap<>();
        File file = new File(ITEM_CSV_FILE_PATH);
        try (Scanner in = new Scanner(file)) {
            if (in.hasNext()) {
                in.nextLine();
                while (in.hasNext()) {
                    createItem(itemMap, in);
                }
            }
            DataHolder.setItemsMap(itemMap);
        } catch (FileNotFoundException e) {
            createFile(ITEM_CSV_FILE_PATH);
        } catch (InvalidValueException e) {
            e.printStackTrace();
        }

    }

    public void readUsersFromFile() {
        Map<String, User> userMap = new HashMap<>();
        File file = new File(USER_CSV_FILE_PATH);
        try (Scanner in = new Scanner(file)) {
            if (in.hasNext()) {
                in.nextLine();
                while (in.hasNext()) {
                    createUser(userMap, in);
                }
            }
            DataHolder.setUsersMap(userMap);
        } catch (FileNotFoundException e) {
            createFile(USER_CSV_FILE_PATH);
            e.printStackTrace();
        }
    }

    private void createItem(Map<String, Item> itemMap, Scanner in) throws InvalidValueException {
        String data = in.nextLine();
        List<String> dataList = Arrays.asList(data.split(";"));
        String itemTypeString = dataList.get(0);
        String itemCode = dataList.get(1);
        String itemName = dataList.get(2);
        String itemPrice = dataList.get(3);
        int quantity = Integer.parseInt(dataList.get(4));
        ItemType itemType = ItemType.getItemType(itemTypeString);

        long aLong = Long.parseLong(itemPrice);
        BigDecimal bigDecimalPrice = BigDecimal.valueOf(aLong);

        Item item = new Item(itemName, itemType, itemCode, bigDecimalPrice, quantity);
        itemMap.put(itemCode, item);
    }

    private void createUser(Map<String, User> userMap, Scanner in) {
            String data = in.nextLine();
            List<String> dataList = Arrays.asList(data.split(";"));
            String login = dataList.get(0);
            String password = dataList.get(1);
            User user = new User(login, password);
            userMap.put(user.getLogin(), user);
    }


    private void createFile(String fileName){
        try {
            Path newFilePath = Paths.get(fileName);
            Files.createFile(newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
