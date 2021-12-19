package com.example.shopfx.validation;

import com.example.shopfx.exceptions.InvalidValueException;

public class UserValidation {

    public static void validateUser(String userName, String userPassword, String passwordRepeated) throws InvalidValueException {

        if(userName == null || userName.isEmpty()){
            throw new InvalidValueException("Invalid user name!");
        }

        if(userPassword == null || userPassword.isEmpty()){
            throw new InvalidValueException("Password cannot by empty!");
        }

        if(passwordRepeated == null || passwordRepeated.isEmpty()){
            throw new InvalidValueException("Repeated password cannot by empty!");
        }

        if(!passwordRepeated.equals(userPassword)){
            throw new InvalidValueException("The passwords do not match!");
        }

    }
}
