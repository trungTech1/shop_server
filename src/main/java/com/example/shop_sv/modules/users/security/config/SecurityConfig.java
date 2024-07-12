package com.example.shop_sv.modules.users.security.config;

import com.example.shop_sv.modules.users.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

// cấu hinh security
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    // định nghĩa bean
    // authentication manager : authenticate()
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Allow frontend origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Allow necessary HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Allow necessary headers
        configuration.setAllowCredentials(true); // Optional: if you need to send cookies or use credentials
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS configuration to all paths
        return source;
    }

    @Bean // phân quyền
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors ->cors.configurationSource(corsConfigurationSource())) // chia sẻ tài nguyên
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // phi trạng thai
                // xử lí lỗi :
                .exceptionHandling(handler ->
                        handler.accessDeniedHandler(new AccessDeniedCustomHandler()) // ko có quyen
                                .authenticationEntryPoint(new AuthentiactionEntryPointCustom())
                        )
                .authorizeHttpRequests( // cấu hình phân quyền theo đường dẫn
                        auth -> auth.requestMatchers("/auth/**").permitAll() // đăng nhập đăng kí
                                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // chỉ co quyên admin
                                .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                                .requestMatchers("/mod/**").hasAuthority("ROLE_MODERATOR")
                                .requestMatchers("/user-mod/**").hasAnyAuthority("ROLE_USER", "ROLE_MODERATOR")
                                .requestMatchers("/public/**").permitAll()
                                .anyRequest().authenticated() // còn lại thì phải được xác thực
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
