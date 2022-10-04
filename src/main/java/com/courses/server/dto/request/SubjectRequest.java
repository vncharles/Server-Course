package com.courses.server.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SubjectRequest {
    private String code;
    private String name;
    private double price;
    private boolean status;
    private String note;
    private String manager;
    private String expert;
    private MultipartFile image;
}
