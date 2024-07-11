package com.example.shop_sv.modules.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class TestController {
    @GetMapping("/admin/data")
    public ResponseEntity<String> admin(){
        return new ResponseEntity<>("thành công", HttpStatus.OK);
    }
    @GetMapping("/user/data")
    public ResponseEntity<String> user(){
        return new ResponseEntity<>("thành công", HttpStatus.OK);
    } @GetMapping("/mod/data")
    public ResponseEntity<String> mod(){
        return new ResponseEntity<>("thành công", HttpStatus.OK);
    }

}
