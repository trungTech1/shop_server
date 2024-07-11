package com.example.shop_sv.modules.users.exception;

public class UserNameOrPasswordInvalidException extends RuntimeException{
    public UserNameOrPasswordInvalidException(String message) {
        super(message);
    }
}
