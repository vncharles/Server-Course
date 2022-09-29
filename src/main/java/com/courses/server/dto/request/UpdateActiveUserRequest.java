package com.courses.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UpdateActiveUserRequest {
    private String username;
    private boolean status;
}
