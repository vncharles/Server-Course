package com.courses.server.dto.response;

import com.courses.server.entity.Post;
import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String body;
    private UserDTO author;
    private String createDate;
    private String thumnailUrl;
    private int status;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.author = new UserDTO(post.getAuthor());
        this.createDate = post.getCreatedDate();
        this.thumnailUrl = post.getThumnailUrl();
        this.status = post.getStatus();
    }
}
