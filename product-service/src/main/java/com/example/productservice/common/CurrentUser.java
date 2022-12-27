package com.example.productservice.common;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Component
@RequestScope
public class CurrentUser {

    private String accesToken;
    private String numero;

    private String email;

    private List<String> Roles;

    public List<String> getRoles() {
        return Roles;
    }

    public void setRoles(List<String> roles) {
        Roles = roles;
    }

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
