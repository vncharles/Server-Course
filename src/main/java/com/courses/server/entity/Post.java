package com.courses.server.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Post extends BaseDomain {
    private static final int MIN_TITLE_LENGTH = 7;

    @Length(min = MIN_TITLE_LENGTH, message = "Title must be at least " + MIN_TITLE_LENGTH + " characters long")
    @NotEmpty(message = "Please enter the title")
    @Column(name = "title", columnDefinition = "TEXT", nullable = true)
    private String title;

    @NotEmpty(message = "Please enter the body")
    @Column(name = "body", columnDefinition = "TEXT", nullable = true)
    private String body;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "setting_id")
    private Setting category;

    @Column(name = "bref_info")
    private String brefInfo;

    private String thumnailUrl;
    private int status;
    private long views;

    public void increaseView() {
        this.views += 1;
    }
}
