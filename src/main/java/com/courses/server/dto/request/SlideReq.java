package com.courses.server.dto.request;

import lombok.Data;
import java.util.Date;

@Data
public class SlideReq {
    private Date validTo;
    private String title;
    private String url;
    private int status;
}
