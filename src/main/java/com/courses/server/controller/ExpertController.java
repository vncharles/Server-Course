package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.ExpertRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.response.ExpertDTO;
import com.courses.server.entity.Expert;
import com.courses.server.services.ExpertService;
import com.courses.server.utils.Authen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expert")
public class ExpertController {
	@Autowired
	private ExpertService expertService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> getDetail(@PathVariable("id") Long id) {
		Authen.check();
		Expert expert = expertService.getById(id);

		return ResponseEntity.ok(new ExpertDTO(expert));
	}

	@GetMapping("")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER')")
	public ResponseEntity<?> getAllExpert(@Param("keyword") String keyword, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) throws IOException {
		Authen.check();
		Pageable paging = PageRequest.of(page, size);
		Params params = new Params();
		if (keyword != null && keyword.trim().length() != 0)
			params.setKeyword(keyword);
		else
			params.setKeyword(null);

		Page<Expert> pageExperts = expertService.getAllManage(paging, params);
		List<ExpertDTO> listExpertDTO = pageExperts.stream().map(exp -> new ExpertDTO(exp))
				.collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("data", listExpertDTO);
		response.put("currentPage", pageExperts.getNumber());
		response.put("totalItems", pageExperts.getTotalElements());
		response.put("totalPages", pageExperts.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/views/{id}")
	public ResponseEntity<?> getDetailView(@PathVariable("id") Long id) {
		Expert expert = expertService.getById(id);

		return ResponseEntity.ok(new ExpertDTO(expert));
	}

	@GetMapping("/views")
	public ResponseEntity<?> getAllExpertView(@Param("keyword") String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) throws IOException {
		Pageable paging = PageRequest.of(page, size);
		Params params = new Params();
		if (keyword != null && keyword.trim().length() != 0)
			params.setKeyword(keyword);
		else
			params.setKeyword(null);

		Page<Expert> pageExperts = expertService.getAllManage(paging, params);
		List<ExpertDTO> listExpertDTO = pageExperts.stream().map(exp -> new ExpertDTO(exp))
				.collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("data", listExpertDTO);
		response.put("currentPage", pageExperts.getNumber());
		response.put("totalItems", pageExperts.getTotalElements());
		response.put("totalPages", pageExperts.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> create(@RequestBody ExpertRequest req) {
		Authen.check();
		expertService.create(req);

		return ResponseEntity.ok(new MessageResponse("Tạo giảng viên thành công"));
	}

	@PutMapping("/update")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> update(@RequestParam("id") Long id, @RequestParam("data") String data,
			@RequestParam("image") @Nullable MultipartFile image) throws JsonMappingException, JsonProcessingException {
		Authen.check();
		ObjectMapper mapper = new ObjectMapper();
		ExpertRequest req = mapper.readValue(data, ExpertRequest.class);
		expertService.update(id, req, image);

		return ResponseEntity.ok(new MessageResponse("Cập nhật giảng viên thành công"));
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> delete(@RequestParam("id") Long id) {
		Authen.check();
		expertService.delete(id);

		return ResponseEntity.ok(new MessageResponse("Delete expert is success"));
	}
}
