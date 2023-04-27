package com.courses.server.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Combo extends BaseDomain {
    private String title;

    @NotEmpty(message = "Nhập 1 vài thông tin miêu tả...")
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    private String image;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ComboPackage> comboPackages;

    public Combo(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
