package com.courses.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebContactRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String message;
    private String note;
    private Long categoryId;
}
