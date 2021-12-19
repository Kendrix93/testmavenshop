package com.example.shopfx.persistence;

import com.example.shopfx.datamodel.Item;
import com.example.shopfx.datamodel.ItemType;
import com.example.shopfx.datamodel.User;
import com.example.shopfx.exceptions.ObjectExistException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataPersistenceService {

    private final FileWriter fileWriter = new FileWriter();

    public void createUser(String login, String userPassword) throws ObjectExistException {
        Map<String, User> usersMap = DataHolder.getUsersMap();
        User user = usersMap.get(login);
        if(user != null){
            throw new ObjectExistException(String.format("User with login %s already exist.", login));
        }
        user = new User(login, userPassword);
        usersMap.put(login, user);
        DataHolder.setUsersMap(usersMap);
        fileWriter.saveUsersToFile();
    }

    public void deleteUser(User user) throws ObjectExistException {
        new DataFetcher().getUser(user.getLogin(), user.getPassword());
        Map<String, User> usersMap = DataHolder.getUsersMap();
        usersMap.remove(user.getLogin());
        DataHolder.setUsersMap(usersMap);
        fileWriter.saveUsersToFile();
    }

    public void createItem(String itemCode, String itemName, BigDecimal price, ItemType itemType, int quantity) throws ObjectExistException {
        Map<String, Item> itemsMap = DataHolder.getItemsMap();
        Item item = itemsMap.get(itemCode);
        if(item != null){
            throw new ObjectExistException(String.format("Objects with code %s already exist.", itemCode));
        }
        item = new Item(itemName, itemType, itemCode, price, quantity);
        itemsMap.put(itemCode, item);
        DataHolder.setItemsMap(itemsMap);
        fileWriter.saveItemsToFile();
    }

    public void editItem(String oldItemCode, Item newItem) throws ObjectExistException {
        Map<String, Item> itemsMap = DataHolder.getItemsMap();
        String itemCode = newItem.getItemCode();
        itemsMap.remove(oldItemCode);
        itemsMap.put(itemCode, newItem);
        DataHolder.setItemsMap(itemsMap);
        fileWriter.saveItemsToFile();
    }

    public void deleteItem(Item item) throws ObjectExistException {
        Map<String, Item> itemsMap = DataHolder.getItemsMap();
        itemsMap.remove(item.getItemCode());
        DataHolder.setItemsMap(itemsMap);
        fileWriter.saveItemsToFile();
    }

    public String addItemToCart(Item item) {
        Item itemToCart = new Item(item);
        Map<String, List<Item>> cartItemsMap = DataHolder.getCartItemsMap();
        String loggedUserLogin = DataHolder.getLoggedUserLogin();
        List<Item> itemsFromCart = new ArrayList<>();
        if (cartItemsMap.containsKey(loggedUserLogin)) {
            itemsFromCart.addAll(cartItemsMap.get(loggedUserLogin));
            List<Item> itemToRemoveFromCart = new ArrayList<>();
            itemsFromCart.stream()
                    .filter(itemFromCart -> itemFromCart.equals(itemToCart))
                    .forEach(itemFromCart -> {
                        int newItemQuantity = itemFromCart.getQuantity() + itemToCart.getQuantity();
                        itemToCart.setQuantity(newItemQuantity);
                        itemToRemoveFromCart.add(itemFromCart);
                    });
            itemsFromCart.removeAll(itemToRemoveFromCart);
        }
        itemsFromCart.add(itemToCart);
        cartItemsMap.put(loggedUserLogin, itemsFromCart);
        DataHolder.setCartItemsMap(cartItemsMap);
        return "Added item to cart!";
    }


}
