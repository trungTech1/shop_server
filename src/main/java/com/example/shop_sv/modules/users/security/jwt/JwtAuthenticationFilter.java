package com.example.shop_sv.modules.users.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            if (token != null && jwtProvider.validateToken(token)) {
                String user = jwtProvider.getUserNameAndIdAndRolesFromToken(token);
                String[] parts = user.split(" ");
                String username = parts[0];
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.info("Authenticated user: " + username);
            } else {
                logger.info("No valid JWT token found");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: " + e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
    private String getTokenFromRequest(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authorization);
        if (authorization!=null && authorization.startsWith("Bearer ")){
            return authorization.substring(7);
        }
        return null;
    }
}
