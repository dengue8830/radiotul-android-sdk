package com.amla.radiotulsdk.access;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class UserDoesntExistsException extends Exception {
    public UserDoesntExistsException(String message){
        super(message);
    }
}
