package com.example.shopfx.datamodel;

import com.example.shopfx.exceptions.InvalidValueException;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public enum ItemType {

    SMALL_ITEM, MEDIUM_ITEM, BIG_ITEM;

    public static List<ItemType> getAllItemType(){
        return Arrays.asList(SMALL_ITEM, MEDIUM_ITEM, BIG_ITEM);
    }

    public static ItemType getItemType(String itemType) throws InvalidValueException {
        if(itemType.equalsIgnoreCase(SMALL_ITEM.toString())){
            return SMALL_ITEM;
        }else if(itemType.equalsIgnoreCase(MEDIUM_ITEM.toString())){
            return MEDIUM_ITEM;
        }else if(itemType.equalsIgnoreCase(BIG_ITEM.toString())){
            return BIG_ITEM;
        } else{
            throw new InvalidValueException("Cannot find type: " + itemType);
        }
    }

}
