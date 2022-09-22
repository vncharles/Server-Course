package com.courses.server.dto.request;

import com.courses.server.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class RegisterDTO{
    private String username;
    private String password;
    private String email;

    public RegisterDTO(User user) {
        super();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    public RegisterDTO() {
        super();
    }
}
