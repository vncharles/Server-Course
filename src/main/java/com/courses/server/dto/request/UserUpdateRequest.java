package com.courses.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserUpdateRequest {
    private String username;
    private String password;
    private String oldPassword;
    private String fullname;
    private String phoneNumber;
    private String note;
}
