package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.PostReq;
import com.courses.server.dto.response.PostDTO;
import com.courses.server.entity.Post;
import com.courses.server.services.PostService;
import com.courses.server.utils.Authen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
public class PostController {
	@Autowired
	private PostService postService;

	@GetMapping("")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT', 'ROLE_MANAGER', 'ROLE_SUPPORTER')")
	public ResponseEntity<?> getAllPost(@Param("status") int status, @Param("keyword") String keyword,
			@RequestParam(defaultValue = "0") int page, @Param("category") long category,
			@RequestParam(defaultValue = "10") int size) throws IOException {
		Authen.check();
		Pageable paging = PageRequest.of(page, size);

		Params params = new Params();
		params.setCategory(category);
		params.setStatus(status);
		if (keyword != null && keyword.trim().length() != 0)
			params.setKeyword(keyword);
		else
			params.setKeyword(null);
		
		Page<Post> listPost = postService.getAll(paging, params, false);
		List<PostDTO> listPostDTO = listPost.getContent().stream().map(post -> new PostDTO(post))
				.collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("data", listPostDTO);
		response.put("currentPage", listPost.getNumber());
		response.put("totalItems", listPost.getTotalElements());
		response.put("totalPages", listPost.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@GetMapping("top-views")
	public ResponseEntity<?> TopViews(@RequestParam(defaultValue = "4") int top) {
		List<Post> listPost = postService.getTopView(top);
		List<PostDTO> listPostDTO = listPost.stream().map(post -> new PostDTO(post))
				.collect(Collectors.toList());

		return ResponseEntity.ok(listPostDTO);
	}

	@GetMapping("top-recent")
	public ResponseEntity<?> TopRecent(@RequestParam(defaultValue = "4") int top) {
		List<Post> listPost = postService.getTopRecent(top);
		List<PostDTO> listPostDTO = listPost.stream().map(post -> new PostDTO(post))
				.collect(Collectors.toList());

		return ResponseEntity.ok(listPostDTO);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT', 'ROLE_MANAGER','ROLE_SUPPORTER')")
	public ResponseEntity<?> detailPost(@PathVariable("id") Long id) {
		Authen.check();
		Post post = postService.getById(id);

		return ResponseEntity.ok(new PostDTO(post));
	}
	
	@GetMapping("/views")
	public ResponseEntity<?> getAllPostView(
			@Param("keyword") String keyword,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "0") long category,
			@RequestParam(defaultValue = "10") int size) throws IOException {
		Pageable paging = PageRequest.of(page, size);

		Params params = new Params();
		params.setCategory(category);
		params.setStatus(2);
		if (keyword != null && keyword.trim().length() != 0)
			params.setKeyword(keyword);
		else
			params.setKeyword(null);
		
		Page<Post> listPost = postService.getAll(paging, params, true);
		List<PostDTO> listPostDTO = listPost.getContent().stream().map(post -> new PostDTO(post))
				.collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("data", listPostDTO);
		response.put("currentPage", listPost.getNumber());
		response.put("totalItems", listPost.getTotalElements());
		response.put("totalPages", listPost.getTotalPages());

		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/views/{id}")
	public ResponseEntity<?> detailPostView(@PathVariable("id") Long id) {
		Post post = postService.getById(id);
		return ResponseEntity.ok(new PostDTO(post));
	}

	@PostMapping(value = "/create", headers = { "content-type=multipart/mixed", "content-type=multipart/form-data" })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT', 'ROLE_MANAGER', 'ROLE_SUPPORTER')")
	public ResponseEntity<?> createPost(@Validated @RequestParam("data") String data,
			@RequestParam("image") MultipartFile image) throws JsonProcessingException {
		Authen.check();
		ObjectMapper mapper = new ObjectMapper();
		PostReq req = mapper.readValue(data, PostReq.class);

		postService.create(req, image);
		return ResponseEntity.ok(new MessageResponse("Tạo bài viết thành công"));
	}

	@PutMapping(value = "/update", headers = { "content-type=multipart/mixed", "content-type=multipart/form-data" })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT', 'ROLE_MANAGER', 'ROLE_SUPPORTER')")
	public ResponseEntity<?> updatePost(@RequestParam("id") Long id, @RequestParam("data") String data,
			@RequestParam("image") @Nullable MultipartFile image) throws JsonProcessingException {
		Authen.check();
		ObjectMapper mapper = new ObjectMapper();
		PostReq req = mapper.readValue(data, PostReq.class);
		postService.update(id, req, image);
		return ResponseEntity.ok(new MessageResponse("Cập nhật bài viết thành công"));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MARKETER', 'ROLE_EXPERT', 'ROLE_MANAGER', 'ROLE_SUPPORTER')")
	public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
		Authen.check();
		postService.delete(id);
		return ResponseEntity.ok(new MessageResponse("Xóa bài viết thành công"));
	}

	@PostMapping("/upload-image-post")
	public ResponseEntity<?> uploadAvatar(@RequestParam("id") Long id, @RequestParam("image") MultipartFile avatar)
			throws IOException {
		Authen.check();
		postService.uploadImagePost(id, avatar);
		return ResponseEntity.ok(new MessageResponse("Cập nhật ảnh bài viết thành công"));
	}
}
