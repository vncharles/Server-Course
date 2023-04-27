package com.courses.server.dto.response;

import com.courses.server.entity.Post;
import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String body;
    private UserDTO author;
    private String brefInfo;
    private String thumnailUrl;
    private int status;
    private long views;
    private String createDate;
    private Long categoryId;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.author = new UserDTO(post.getAuthor());
        this.brefInfo = post.getBrefInfo();
        this.thumnailUrl = post.getThumnailUrl();
        this.status = post.getStatus();
        this.categoryId = post.getCategory().getSetting_id();
        this.views = post.getViews();
        this.createDate = post.getCreatedDate();
    }
}
