package com.courses.server.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.SettingRequest;
import com.courses.server.entity.Setting;
import com.courses.server.services.SettingService;
import com.courses.server.utils.Authen;
import com.courses.server.repositories.TypeRepository;

@RestController
@RequestMapping("api/admin/setting")
public class AdminSettingController {
	@Autowired
	private SettingService settingService;

	@Autowired
	private TypeRepository typeRepository;

	@PostMapping("/addSetting")
	public ResponseEntity<?> addSetting(@RequestBody SettingRequest settingRequest) {
		settingService.addSetting(settingRequest);
		return ResponseEntity.ok(new MessageResponse("Thêm cấu hình thành công"));
	}

	@PutMapping("/updateSetting")
	public ResponseEntity<?> updateSetting(@RequestBody SettingRequest settingRequest) {
		settingService.updateSettings(settingRequest);
		return ResponseEntity.ok(new MessageResponse("Cập nhật cấu hình thành công"));
	}

	@GetMapping("/getListSetting")
	public ResponseEntity<?> getListSetting(
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

		Page<Setting> pageWebContacts = settingService.getListSetting(paging, params);

		Map<String, Object> response = new HashMap<>();
		response.put("data", pageWebContacts.getContent());
		response.put("currentPage", pageWebContacts.getNumber());
		response.put("totalItems", pageWebContacts.getTotalElements());
		response.put("totalPages", pageWebContacts.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getListType")
	public ResponseEntity<?> getListType() {
		return ResponseEntity.ok(typeRepository.findAll());
	}

	@GetMapping("/getSetting/{id}")
	public ResponseEntity<?> getSetting(@PathVariable Long id) {
		return ResponseEntity.ok(settingService.getDetailSetting(id));
	}

	@GetMapping("/list-category-WebContact")
	public ResponseEntity<?> listCategoryWebContact() {
		return ResponseEntity.ok(settingService.getSettingByType(Long.valueOf(5)));
	}

	@GetMapping("/list-category-subject")
	public ResponseEntity<?> listCategorySubject() {
		return ResponseEntity.ok(settingService.getSettingByType(Long.valueOf(6)));
	}

	@GetMapping("/list-category-post")
	public ResponseEntity<?> listCategoryPost() {
		return ResponseEntity.ok(settingService.getSettingByType(Long.valueOf(8)));
	}
	
	@GetMapping("/list-category-branch")
	public ResponseEntity<?> listCategoryBranch() {
		return ResponseEntity.ok(settingService.getSettingByType(Long.valueOf(7)));
	}

}
