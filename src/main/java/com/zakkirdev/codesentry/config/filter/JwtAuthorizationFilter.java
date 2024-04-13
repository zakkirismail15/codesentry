package com.zakkirdev.codesentry.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zakkirdev.codesentry.util.auth.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;


    public JwtAuthorizationFilter(JwtUtil jwtUtil, ObjectMapper mapper){
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> errorDetails =  new HashMap<>();
        try{
            String accessToken = jwtUtil.resolveToken(request);
            if(accessToken == null){
                filterChain.doFilter(request,response);
                return;
            }
            Claims claims = jwtUtil.resolveClaims(request);
            if(claims != null & jwtUtil.validateClaims(claims)){
                String email = claims.getSubject();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,null,getAuthorities(claims));
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);
        }
        filterChain.doFilter(request,response);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims){
        String roles = (String) claims.get("roles");
        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for(String role: roleNames){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
