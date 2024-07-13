package com.example.shop_sv.modules.users.controller;

import com.example.shop_sv.modules.users.model.dto.request.FormLogin;
import com.example.shop_sv.modules.users.model.dto.request.FormRegister;
import com.example.shop_sv.modules.users.service.IAuthentication;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthentication authentication;
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody FormLogin formLogin){
        return new ResponseEntity<>(authentication.login(formLogin), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody FormRegister formRegister){
        return  null;
    }
}
