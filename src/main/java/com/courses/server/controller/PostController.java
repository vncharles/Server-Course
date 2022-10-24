package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.PostReq;
import com.courses.server.dto.response.PostDTO;
import com.courses.server.entity.ERole;
import com.courses.server.entity.Post;
import com.courses.server.entity.Role;
import com.courses.server.services.PostService;
import com.courses.server.services.RoleService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT')")
    public ResponseEntity<?> getAllPost() {
        Authen.check();
        Role role = roleService.checkRole();
        List<Post> listPost = postService.getAll();
        List<PostDTO> postDTOList = new ArrayList<>();
        for(Post post: listPost){
            postDTOList.add(new PostDTO(post));
        }
        if(role.getName().equals(ERole.ROLE_ADMIN) || role.getName().equals(ERole.ROLE_MARKETER)){
            return ResponseEntity.ok(postDTOList);
        }

        List<PostDTO> postDTOFilter = postDTOList.stream().filter(p -> p.getAuthor().getRole().equals(ERole.ROLE_EXPERT)).collect(Collectors.toList());
        return ResponseEntity.ok(postDTOFilter);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT')")
    public ResponseEntity<?> detailPost(@PathVariable("id") Long id){
        Authen.check();
        Post post = postService.getById(id);

        return ResponseEntity.ok(new PostDTO(post));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT')")
    public ResponseEntity<?> createPost(@RequestBody PostReq req){
        Authen.check();
        postService.create(req);

        return ResponseEntity.ok(new MessageResponse("Create post success"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT')")
    public ResponseEntity<?> updatePost(@RequestParam("id")Long id,
                                        @RequestBody PostReq req) {
        Authen.check();
        postService.update(id, req);
        return ResponseEntity.ok(new MessageResponse("Update post success"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT')")
    public ResponseEntity<?> deletePost(@PathVariable("id")Long id) {
        Authen.check();
        postService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Delete post is success"));
    }
}
