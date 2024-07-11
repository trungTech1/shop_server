package com.example.shop_sv.modules.users.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormRegister {
    @NotBlank(message = "Username Không được để trống")
    private String username;

    @NotBlank(message = "Password Không được để trống")
    private String password;

    @NotBlank(message = "Email Không được để trống")
    private String email;

    @NotBlank(message = "Phone Không được để trống")
    private String phone;

    @NotBlank(message = "Fullname Không được để trống")
    private String fullName;
}
