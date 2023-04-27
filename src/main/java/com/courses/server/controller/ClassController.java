package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.ClassRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.response.ClassDTO;
import com.courses.server.entity.Class;
import com.courses.server.services.ClassService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/class")
public class ClassController {
	@Autowired
	private ClassService classService;

	@GetMapping("")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_TRAINER')")
	public ResponseEntity<?> getListClass(
			@RequestParam(defaultValue = "0") int page,
			@Param("keyword") String keyword, 
			@Param("status") Boolean status, 
			@Param("category") long category,
			@RequestParam(defaultValue = "10") int size) throws IOException {
		Authen.check();
		Pageable paging = PageRequest.of(page, size);

		Params params = new Params();
		params.setCategory(category);
		params.setActive(status);
		if (keyword != null && keyword.trim().length() != 0)
			params.setKeyword(keyword);
		else
			params.setKeyword(null);
		Page<Class> pageClass = classService.getAllClass(paging, params, false);
		List<ClassDTO> listClassDTO = pageClass.getContent().stream().map(_class -> new ClassDTO(_class))
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("data", listClassDTO);
		response.put("currentPage", pageClass.getNumber());
		response.put("totalItems", pageClass.getTotalElements());
		response.put("totalPages", pageClass.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_TRAINER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getClassDetail(@PathVariable("id") Long id) {
		Authen.check();
		Class _class = classService.getClassDetail(id);

		return ResponseEntity.ok(new ClassDTO(_class));
	}
	
	@GetMapping("/views")
	public ResponseEntity<?> getListClassView(
			@RequestParam(defaultValue = "0") int page,
			@Param("keyword") String keyword, 
			@RequestParam(defaultValue = "0") long category,
			@RequestParam(defaultValue = "10") int size) throws IOException {
		Pageable paging = PageRequest.of(page, size);

		Params params = new Params();
		params.setCategory(category);
		params.setActive(true);
		if (keyword != null && keyword.trim().length() != 0)
			params.setKeyword(keyword);
		else
			params.setKeyword(null);
		Page<Class> pageClass = classService.getAllClass(paging, params, true);
		List<ClassDTO> listClassDTO = pageClass.getContent().stream().map(_class -> new ClassDTO(_class))
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("data", listClassDTO);
		response.put("currentPage", pageClass.getNumber());
		response.put("totalItems", pageClass.getTotalElements());
		response.put("totalPages", pageClass.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/views/{id}")
	public ResponseEntity<?> getClassDetailView(@PathVariable("id") Long id) {
		Class _class = classService.getClassDetail(id);
return ResponseEntity.ok(new ClassDTO(_class));
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER')")
	@PostMapping("/create")
	public ResponseEntity<?> createClass(@RequestBody ClassRequest classRequest) {
		Authen.check();
		classService.addClass(classRequest);

		return ResponseEntity.ok(new MessageResponse("Tạo lớp học thành công"));
	}

	@PostMapping("/update")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER')")
	public ResponseEntity<?> updateClass(@RequestParam("id") Long id, @RequestBody ClassRequest classRequest) {
		Authen.check();
		classService.updateCLass(id, classRequest);

		return ResponseEntity.ok(new MessageResponse("Cập nhật lớp học thành công"));
	}
}
