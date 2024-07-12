package com.example.shop_sv.modules.users.advice;

import com.example.shop_sv.modules.users.exception.UserNameOrPasswordInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler(UserNameOrPasswordInvalidException.class)
    public ResponseEntity<?> handlerLogin(UserNameOrPasswordInvalidException e){
        Map<String , Object> map = new HashMap<>();
        map.put("code","400");
        map.put("error", HttpStatus.BAD_REQUEST);
        map.put("message",e.getMessage());
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerValidator(MethodArgumentNotValidException e){
        Map<String , String> detailError = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            detailError.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        Map<String , Object> map = new HashMap<>();
        map.put("code","400");
        map.put("error", HttpStatus.BAD_REQUEST);
        map.put("message",detailError);
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<?> handlerInsufficientAuthentication(InsufficientAuthenticationException e){
        Map<String , Object> map = new HashMap<>();
        map.put("code","401");
        map.put("error", HttpStatus.UNAUTHORIZED);
        map.put("message",e.getMessage());
        return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);
    }
}
