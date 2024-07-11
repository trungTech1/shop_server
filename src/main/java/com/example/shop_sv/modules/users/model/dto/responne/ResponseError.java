package com.example.shop_sv.modules.users.model.dto.responne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseError {
    private int status;
    private String message;
    private Object details;
}