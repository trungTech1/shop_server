package com.example.shop_sv.modules.users.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FormLogin {
    @NotBlank(message = "Username Không được để trống")
    private  String username;

    @NotBlank(message = "Password Không được để trống")
    private String password;

    @Override
    public String toString() {
        return "FormLogin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
