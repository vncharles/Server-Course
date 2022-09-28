package com.courses.server.dto.request;

import com.courses.server.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;

    public RegisterRequest(User user) {
        super();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    public RegisterRequest() {
        super();
    }
}
