package com.example.productservice.common.filter;

import com.example.productservice.common.config.Config;
import com.example.productservice.dto.token.TokenDTO;
import com.example.productservice.common.exception.HttpUnauthorizedException;
import com.example.productservice.common.exception.InvalidInputException;
import com.example.productservice.common.utiles.Constants;
import com.example.productservice.common.utiles.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class FilterAuthentification implements Filter {

    @Autowired
    Config config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, InvalidInputException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        if(Utiles.isNotNullAndNotEmpty(httpRequest.getHeader(Constants.AUTHORIZATION))){
            var tokenDTO = new TokenDTO();
            tokenDTO.setAccessToken(httpRequest.getHeader(Constants.AUTHORIZATION));
            try {
                config.verifySignaturekeycloakAndLoadPayload(tokenDTO);
            } catch (Exception e) {
                throw new HttpUnauthorizedException("Invalid AccesToken");
            }
        }

        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }
}
