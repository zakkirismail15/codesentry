package com.zakkirdev.codesentry.controller.response;


import com.zakkirdev.codesentry.repository.enums.AccessRole;
import lombok.Data;

@Data
public class LoginResponse {

    private String email;
    private String token;
    private AccessRole role;

    public LoginResponse(String email, String token, AccessRole role) {
        this.email = email;
        this.token = token;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

