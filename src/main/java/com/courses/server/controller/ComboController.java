package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.ComboRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.entity.Combo;
import com.courses.server.services.ComboService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/combo")
public class ComboController {
    @Autowired
    private ComboService comboService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("image")MultipartFile image,
    @RequestParam("data") String data) throws JsonProcessingException {
        Authen.check();
        ObjectMapper mapper = new ObjectMapper();
        ComboRequest req = mapper.readValue(data, ComboRequest.class);
        comboService.create(req, image);

        return ResponseEntity.ok(new MessageResponse("Tạo combo thành công"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("id") Long id,
            @RequestParam("image") @Nullable MultipartFile image,
            @RequestParam("data") String data) throws JsonProcessingException {
        Authen.check();
        ObjectMapper mapper = new ObjectMapper();
        ComboRequest req = mapper.readValue(data, ComboRequest.class);
        comboService.update(id, req, image);

        return ResponseEntity.ok(new MessageResponse("Cập nhật combo thành công"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        Authen.check();
        comboService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Xóa combo thành công"));
    }

    @GetMapping("")
    public ResponseEntity<?> list(@Param("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws IOException {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);
        Params params = new Params();
        if (keyword != null && keyword.trim().length() != 0)
            params.setKeyword(keyword);
        else
            params.setKeyword(null);

        Page<Combo> listCombo = comboService.getList(paging, params);

        Map<String, Object> response = new HashMap<>();
        response.put("data", listCombo.getContent());
        response.put("currentPage", listCombo.getNumber());
        response.put("totalItems", listCombo.getTotalElements());
        response.put("totalPages", listCombo.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/views")
    public ResponseEntity<?> getListClassView(
            @RequestParam(defaultValue = "0") int page,
            @Param("keyword") String keyword,
            @RequestParam(defaultValue = "10") int size) throws IOException {
        Pageable paging = PageRequest.of(page, size);
        Params params = new Params();
        if (keyword != null && keyword.trim().length() != 0)
            params.setKeyword(keyword);
        else
            params.setKeyword(null);
        Page<Combo> listCombo = comboService.getList(paging, params);
        Map<String, Object> response = new HashMap<>();
        response.put("data", listCombo.getContent());
        response.put("currentPage", listCombo.getNumber());
        response.put("totalItems", listCombo.getTotalElements());
        response.put("totalPages", listCombo.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/views/{id}")
    public ResponseEntity<?> packageDetailView(@PathVariable("id") Long id) {
        Combo combo = comboService.getDetail(id);
        return ResponseEntity.ok(combo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") Long id) {
        Authen.check();
        Combo combo = comboService.getDetail(id);
        return ResponseEntity.ok(combo);
    }

}
