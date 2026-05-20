package com.example.leets7th.global.auth;

import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.common.BaseCode;
import com.example.leets7th.global.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws IOException, ServletException {

        try {
            filterChain.doFilter(request,response);
        }

        catch(ExpiredJwtException ex){
            responseException(response,ErrorCode.TOKEN_EXPIRATION);
        }

        catch(JwtException ex) {
            responseException(response,ErrorCode.TOKEN_INVALIDATION);
        }

    }

    private void responseException(HttpServletResponse response,BaseCode baseCode) throws IOException {
        response.setStatus(baseCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");


        ApiResponse<Void> apiResponse = ApiResponse.failure(baseCode);

        String body = objectMapper.writeValueAsString(apiResponse);

        response.getWriter().write(body);

    }





}
