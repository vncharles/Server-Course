package com.courses.server.controller.admin;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.UpdateActiveUserRequest;
import com.courses.server.dto.request.UserUpdateRequest;
import com.courses.server.dto.response.ExpertDTO;
import com.courses.server.dto.response.UserDTO;
import com.courses.server.entity.Expert;
import com.courses.server.entity.User;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.UserService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
public class AdminAccountController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getAllUser(@Param("role") long role,
			@Param("status") Boolean status,
			@Param("name") String keyword,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) throws IOException {

		Pageable paging = PageRequest.of(page, size);
		Params params = new Params();
		params.setCategory(role);
		params.setActive(status);
		if (keyword != null && keyword.trim().length() != 0)
			params.setKeyword(keyword);
		else
			params.setKeyword(null);

		Page<User> userPages = userService.getListUser(paging, params);
		List<User> users = userPages.getContent();

		List<UserDTO> userDTOList = new ArrayList<>();

		for (User user : users) {
			if (user != null)
				userDTOList.add(new UserDTO(user));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("data", userDTOList);
		response.put("currentPage", userPages.getNumber());
		response.put("totalItems", userPages.getTotalElements());
		response.put("totalPages", userPages.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/manager-list")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getUserListManager(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<User> pageUsers = userService.getListManager(paging);
		List<User> users = pageUsers.getContent();

		List<UserDTO> userDTOList = new ArrayList<>();
		for (User user : users) {
			if (user.isActive())
				userDTOList.add(new UserDTO(user));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("data", userDTOList);
		response.put("currentPage", pageUsers.getNumber());
		response.put("totalItems", pageUsers.getTotalElements());
		response.put("totalPages", pageUsers.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/expert-list")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> getUserListExpert(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<Expert> pageUsers = userService.getListExpert(paging);
		List<ExpertDTO> listClassDTO = pageUsers.getContent().stream().map(_expert -> new ExpertDTO(_expert))
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("data", listClassDTO);
		response.put("currentPage", pageUsers.getNumber());
		response.put("totalItems", pageUsers.getTotalElements());
		response.put("totalPages", pageUsers.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/trainer-list")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> getUserListTrainer(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<User> pageUsers = userService.getListTrainer(paging);
		List<User> users = pageUsers.getContent();

		List<UserDTO> userDTOList = new ArrayList<>();
		for (User user : users) {
			if (user.isActive())
				userDTOList.add(new UserDTO(user));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("data", userDTOList);
		response.put("currentPage", pageUsers.getNumber());
		response.put("totalItems", pageUsers.getTotalElements());
		response.put("totalPages", pageUsers.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/supporter-list")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER')")
	public ResponseEntity<?> getUserListSupporter(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<User> pageUsers = userService.getListSupporter(paging);
		List<User> users = pageUsers.getContent();

		List<UserDTO> userDTOList = new ArrayList<>();
		for (User user : users) {
			if (user.isActive())
				userDTOList.add(new UserDTO(user));
		}

		Map<String, Object> response = new HashMap<>();
		response.put("data", userDTOList);
		response.put("currentPage", pageUsers.getNumber());
		response.put("totalItems", pageUsers.getTotalElements());
		response.put("totalPages", pageUsers.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/active")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateActiveUser(@Validated @RequestBody UpdateActiveUserRequest activeUserDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		Authen.check();

		userService.updateActive(username, activeUserDTO);

		return ResponseEntity.ok(new MessageResponse("Cập nhật trạng thái"));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> findUserByUsername(@PathVariable("id") Long id) throws IOException {
		User user = userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
		if (user == null) {
			throw new NotFoundException(404, "Id không tồn tại");
		}
		return ResponseEntity.ok(new UserDTO(user, true));
	}

	@PostMapping("/update-user")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateUser(@RequestParam("id") Long id, @RequestBody UserUpdateRequest userUpdate) {
		userService.updateUser(id, null, userUpdate);
		return ResponseEntity.ok(new MessageResponse("Cập nhật thông tin thành công"));
	}
}
