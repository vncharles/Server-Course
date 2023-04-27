package com.courses.server.services;

import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.PostReq;
import com.courses.server.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface PostService {
    Post getById(Long id);

    Page<Post> getAll(Pageable paging, Params params, boolean isGuest) throws IOException;

    Page<Post> getAllView(Pageable pageable);

    List<Post> getTopView(int top);

    List<Post> getTopRecent(int top);

    void create(PostReq post, MultipartFile image);

    void update(Long id, PostReq postReq, MultipartFile image);

    void delete(Long id);

    void uploadImagePost(Long id, MultipartFile image) throws IOException;
}
