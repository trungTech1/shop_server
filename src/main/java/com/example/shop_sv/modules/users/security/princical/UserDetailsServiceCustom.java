package com.example.shop_sv.modules.users.security.princical;

import com.example.shop_sv.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.shop_sv.modules.users.model.entity.User;


import java.util.List;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // chuyển đổi tu User=> UserDetailCustom
        User user = userRepository.findByUsernameOrEmailOrPhone(username)
                .orElseThrow(() ->new UsernameNotFoundException("Username not found"));
        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .toList();
        return UserDetailsCustom.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

    }
}
