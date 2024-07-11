package com.example.shop_sv.modules.users.service;


import com.example.shop_sv.modules.users.model.dto.request.FormLogin;
import com.example.shop_sv.modules.users.model.dto.responne.JWTResponse;

public interface IAuthentication {
    JWTResponse login(FormLogin formLogin);
}
