package com.example.shopfx.validation;

import com.example.shopfx.datamodel.ItemType;
import com.example.shopfx.exceptions.InvalidValueException;

public class ItemValidation {

    public void validateItem(String itemName, String itemCode, String price, ItemType itemType) throws InvalidValueException {
        if(itemName == null || itemName.isEmpty()){
            throw new InvalidValueException("Invalid name!");
        }

        if(itemCode == null || itemCode.isEmpty()){
            throw new InvalidValueException("Invalid item code!");
        }

        validatePrice(price);

        if(itemType == null){
            throw new InvalidValueException("Choose Item Type!");
        }
    }

    private void validatePrice(String price) throws InvalidValueException {
        if(price == null || price.isEmpty()){
            throw new InvalidValueException("Invalid price " + price);
        }
        try{
            Long.parseLong(price);
        }catch (NumberFormatException ex){
            throw new InvalidValueException("Invalid price " + price);
        }


    }

}
