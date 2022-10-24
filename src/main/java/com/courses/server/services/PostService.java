package com.courses.server.services;

import com.courses.server.dto.request.PostReq;
import com.courses.server.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    Post getById(Long id);

    List<Post> getAll();

    void create(PostReq post);

    void update(Long id, PostReq postReq);

    void delete(Long id);
}
