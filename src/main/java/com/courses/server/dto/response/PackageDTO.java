package com.courses.server.dto.response;

import com.courses.server.entity.Package;
import lombok.Data;

@Data
public class PackageDTO {
    private Long id;
    private String title;
    private String excerpt;
    private int duration;
    private String description;
    private boolean status;
    private double listPrice;
    private double salePrice;
    private long views;
    private SubjectDTO sucjectCode;
    private String image;

    public PackageDTO(Package _package) {
        this.id = _package.getId();
        this.title = _package.getTitle();
        this.excerpt = _package.getExcerpt();
        this.duration = _package.getDuration();
        this.description = _package.getDescription();
        this.status = _package.isStatus();
        this.listPrice = _package.getListPrice();
        this.salePrice = _package.getSalePrice();
        this.views = _package.getViews();
        this.image = _package.getImage();
        this.sucjectCode = _package.getSubject()!= null ? new SubjectDTO(_package.getSubject()) : null;
    }
}
