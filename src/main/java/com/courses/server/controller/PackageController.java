package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.PackageReq;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.response.PackageDTO;
import com.courses.server.entity.Package;
import com.courses.server.services.PackageService;
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
@RequestMapping("/api/package")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @PostMapping(value = "/create", headers = { "content-type=multipart/mixed", "content-type=multipart/form-data" })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> createPackage(@Validated @RequestParam("data") String data,
                                           @RequestParam("image") MultipartFile image) throws JsonProcessingException {
        Authen.check();
        ObjectMapper mapper = new ObjectMapper();
        PackageReq req = mapper.readValue(data, PackageReq.class);

        packageService.create(req, image);

        return ResponseEntity.ok(new MessageResponse("Tạo khóa học thành công"));
    }

    @PutMapping(value = "/update", headers = { "content-type=multipart/mixed", "content-type=multipart/form-data" })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> updatePackage(@RequestParam("id") Long id, @RequestParam("data") String data,
                                           @RequestParam("image") @Nullable MultipartFile image) throws JsonProcessingException {
        Authen.check();
        ObjectMapper mapper = new ObjectMapper();
        PackageReq req = mapper.readValue(data, PackageReq.class);
        packageService.update(id, req, image);

        return ResponseEntity.ok(new MessageResponse("Cập nhật khóa học thành công"));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> delete(@RequestParam("id")Long id) {
        Authen.check();
        packageService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Xóa khóa học thành công"));
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> listPackage(@Param("status") Boolean status, 
    		@Param("keyword") String keyword,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "0") long category,
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
		
        Page<Package> listPackage = packageService.getList(paging, params);
        List<PackageDTO> listClassDTO = listPackage.getContent().stream().map(_class -> new PackageDTO(_class))
				.collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("data", listClassDTO);
        response.put("currentPage", listPackage.getNumber());
        response.put("totalItems", listPackage.getTotalElements());
        response.put("totalPages", listPackage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/views")
    public ResponseEntity<?> listPackageViews( 
    		@Param("keyword") String keyword,
			@RequestParam(defaultValue = "0") int page, 
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
		
        Page<Package> listPackage = packageService.getList(paging, params);
        List<PackageDTO> listClassDTO = listPackage.getContent().stream().map(_class -> new PackageDTO(_class))
				.collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("data", listClassDTO);
        response.put("currentPage", listPackage.getNumber());
        response.put("totalItems", listPackage.getTotalElements());
        response.put("totalPages", listPackage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("top-views")
    public ResponseEntity<?> TopViews(@RequestParam(defaultValue = "4") int top) {
        List<Package> packages = packageService.getTopView(top);
        List<PackageDTO> listPackageDTO = packages.stream().map(pac -> new PackageDTO(pac))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listPackageDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> packageDetail(@PathVariable("id")Long id) {
        Authen.check();
        Package _package = packageService.getDetail(id);
        return ResponseEntity.ok(new PackageDTO(_package));
    }
    
    @GetMapping("/views/{id}")
    public ResponseEntity<?> packageDetailView(@PathVariable("id")Long id) {
        Package _package = packageService.getDetail(id);
        return ResponseEntity.ok(new PackageDTO(_package));
    }
}
