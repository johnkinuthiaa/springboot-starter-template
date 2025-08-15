package com.slippery.starter.config;
import com.slippery.starter.service.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtUtils jwtUtils;

    public JwtFilter(UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;

        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader =request.getHeader("Authorization");
        if(authHeader ==null ||authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        try {
            final var jwt =authHeader.substring(7);
            final var username =jwtUtils.extractUserNameFromToken(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(username !=null &&authentication ==null){
                UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
                if(jwtUtils.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request,response);
        }catch (Exception e){
            handlerExceptionResolver.resolveException(request,response,null,e);
        }
    }
}
