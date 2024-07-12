package com.example.shop_sv.modules.users.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public String admin(){
        return "Hello Admin";
    }
}
