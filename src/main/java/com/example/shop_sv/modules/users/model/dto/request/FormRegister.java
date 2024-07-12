package com.example.shop_sv.modules.users.model.dto.request;

import com.example.shop_sv.util.validator.animationCustom.UniqueField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormRegister {
    @NotBlank(message = "Username Không được để trống")
    @Size(min = 3, max = 50, message = "Username phải có độ dài từ 3 đến 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "Username chỉ được chứa chữ cái, số và các ký tự ., _, -")
    @UniqueField(fieldName = "username", message = "Username đã tồn tại")
    private String username;

    @NotBlank(message = "Password Không được để trống")
    private String password;

    @NotBlank(message = "Email Không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email không hợp lệ")
    @UniqueField(fieldName = "email", message = "Email đã tồn tại")
    private String email;

    @NotBlank(message = "Phone Không được để trống")
    private String phone;

    @NotBlank(message = "Fullname Không được để trống")
    private String fullName;

    private String AvatarUrl;

    @Override
    public String toString() {
        return "FormRegister{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", fullName='" + fullName + '\'' +
                ", AvatarUrl='" + AvatarUrl + '\'' +
                '}';
    }
}
