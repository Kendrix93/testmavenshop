package com.example.shopfx.persistence;

import com.example.shopfx.datamodel.Item;
import com.example.shopfx.datamodel.User;
import com.example.shopfx.exceptions.ObjectExistException;

import java.util.Map;

public class DataFetcher {

    public User getUser(String login, String password) throws ObjectExistException {
        Map<String, User> usersMap = DataHolder.getUsersMap();
        User user = usersMap.get(login);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
            throw new ObjectExistException("Wrong password!");
        }
        throw new ObjectExistException(String.format("Cannot find user %s", login));
    }


    public Item getItem(String itemCode) throws ObjectExistException {
        Map<String, Item> itemsMap = DataHolder.getItemsMap();
        Item item = itemsMap.get(itemCode);
        if (item == null) {
            throw new ObjectExistException(String.format("Cannot find item with code %s.", itemCode));
        }
        return item;
    }

}
