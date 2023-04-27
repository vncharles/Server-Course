package com.courses.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Slide extends BaseDomain{
    private String imageUrl;
    private Date validTo;
    private String title;
    private String url;
    private int status;

    public Slide(String imageUrl, Date validTo, int status, String url, String title) {
        this.validTo = validTo;
        this.status = status;
        this.imageUrl = imageUrl;
        this.title = title;
        this.url = url;
    }

    public Slide() {

    }
}
