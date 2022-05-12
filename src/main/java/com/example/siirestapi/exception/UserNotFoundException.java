package com.example.siirestapi.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("Użytkownik o podanym loginie nie istnieje");
    }
}
