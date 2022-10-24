package com.courses.server.services.impl;

import com.courses.server.dto.request.PostReq;
import com.courses.server.entity.Post;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.PostRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post getById(Long id) {
        Post post = postRepository.findById(id).get();
        if(post==null) {
            throw new NotFoundException(404, "Post is not found!");
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public void create(PostReq postReq) {
        if(postReq.getTitle()==null){
            throw new BadRequestException(400, "Title is require");
        }
        if(postReq.getBody()==null){
            throw new BadRequestException(400, "Body is require");
        }
        if(postReq.getAuthorId()==null){
            throw new BadRequestException(400, "Id user is require");
        }

        User user = userRepository.findById(postReq.getAuthorId()).get();
        if(user==null){
            throw new NotFoundException(404, "User is not found!");
        }

        Post post = new Post();
        post.setTitle(postReq.getTitle());
        post.setBody(post.getBody());
        post.setAuthor(user);
        post.setThumnailUrl(post.getThumnailUrl());
        post.setStatus(0);

        postRepository.save(post);
    }

    @Override
    public void update(Long id, PostReq postReq) {
        Post post = postRepository.findById(id).get();
        if(post==null) {
            throw new NotFoundException(404, "Post is not found!");
        }

        if(postReq.getTitle()!=null){
            post.setTitle(postReq.getTitle());
        }
        if(postReq.getBody()!=null){
            post.setBody(postReq.getBody());
        }
        if(postReq.getAuthorId()!=null){
            User user = userRepository.findById(postReq.getAuthorId()).get();
            if(user==null){
                throw new NotFoundException(404, "User is not found!");
            }
            post.setAuthor(user);
        }
        if(postReq.getThumnailUrl()!=null){
            post.setThumnailUrl(postReq.getThumnailUrl());
        }
        if(postReq.getStatus()>=0 && postReq.getStatus()<=4){
            post.setStatus(postReq.getStatus());
        }

        postRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).get();
        if(post==null) {
            throw new NotFoundException(404, "Post is not found!");
        }

        postRepository.delete(post);
    }
}
