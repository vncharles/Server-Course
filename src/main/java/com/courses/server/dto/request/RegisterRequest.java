package com.courses.server.dto.request;

import com.courses.server.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String fullName;


    public RegisterRequest(User user) {
        super();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhoneNumber();
        this.fullName = user.getFullname();
    }

    public RegisterRequest() {
        super();
    }
}
