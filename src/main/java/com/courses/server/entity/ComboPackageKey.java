package com.courses.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable @Data @AllArgsConstructor @NoArgsConstructor
public class ComboPackageKey implements Serializable {
    @Column(name = "combo_id")
    private Long comboId;

    @Column(name = "package_id")
    private Long packageId;
}
