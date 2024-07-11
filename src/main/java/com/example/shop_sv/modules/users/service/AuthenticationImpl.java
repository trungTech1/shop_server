package com.example.shop_sv.modules.users.service;

import com.example.shop_sv.modules.users.exception.UserNameOrPasswordInvalidException;
import com.example.shop_sv.modules.users.model.dto.request.FormLogin;
import com.example.shop_sv.modules.users.model.dto.responne.JWTResponse;
import com.example.shop_sv.modules.users.model.entity.User;
import com.example.shop_sv.modules.users.repository.UserRepository;
import com.example.shop_sv.modules.users.sevurity.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationImpl implements IAuthentication{
    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public JWTResponse login(FormLogin formLogin) {
        // kiê tra xem username và pass co khớp ko

        try {
             authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(formLogin.getUsername(),formLogin.getPassword()));
        }catch (Exception e){
            throw new UserNameOrPasswordInvalidException("username or pass incorrect");
        }
        User user = userRepository.findByUsernameOrEmailOrPhone(formLogin.getUsername())
                .orElseThrow(() ->new UsernameNotFoundException("Username not found"));
        // trả về cho người dùng các thông tin cần thiết
        return JWTResponse.builder()
                .id(user.getId())
                .roles(user.getRoles())
                .fullName(user.getFullName())
                .accessToken(jwtProvider.generateToken(user.getUsername()))
                .build();
    }
}
