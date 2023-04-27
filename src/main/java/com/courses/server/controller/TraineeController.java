package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.TraineeRequest;
import com.courses.server.entity.Trainee;
import com.courses.server.entity.UserPackage;
import com.courses.server.services.TraineeService;
import com.courses.server.utils.Authen;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/trainee")
public class TraineeController {
    @Autowired
    private TraineeService traineeService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER','ROLE_MANAGER')")
    public ResponseEntity<?> create(@RequestBody TraineeRequest req, HttpServletRequest request) {
        Authen.check();
        traineeService.create(req, request);

        return ResponseEntity.ok(new MessageResponse(" Tạo học viên thành công"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER','ROLE_MANAGER')")
    public ResponseEntity<?> update(@RequestParam("id") Long id,
            @RequestBody TraineeRequest req) {
        Authen.check();
        traineeService.update(id, req);

        return ResponseEntity.ok(new MessageResponse("Cập nhật học viên thành công"));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER','ROLE_MANAGER')")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        Authen.check();
        traineeService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Xóa học viên thành công"));
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER','ROLE_MANAGER')")
    public ResponseEntity<?> list(@Param("status") Boolean status,
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

        Page<Trainee> pageTrainee = traineeService.getList(paging, params);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageTrainee.getContent());
        response.put("currentPage", pageTrainee.getNumber());
        response.put("totalItems", pageTrainee.getTotalElements());
        response.put("totalPages", pageTrainee.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-trainee-online")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER','ROLE_MANAGER')")
    public ResponseEntity<?> listTraineeOnline(@Param("status") Boolean status,
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

        Page<UserPackage> pageTrainee = traineeService.getListOnline(paging, params);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageTrainee.getContent());
        response.put("currentPage", pageTrainee.getNumber());
        response.put("totalItems", pageTrainee.getTotalElements());
        response.put("totalPages", pageTrainee.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER','ROLE_MANAGER')")
    public ResponseEntity<?> detail(@PathVariable("id") Long id) {
        Authen.check();
        Trainee trainee = traineeService.getDetail(id);

        return ResponseEntity.ok(trainee);
    }
}