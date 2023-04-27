package com.courses.server.controller;
import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.SlideReq;
import com.courses.server.entity.Slide;
import com.courses.server.services.SlideService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/slide")
public class SlideController {
    @Autowired
    private SlideService slideService;

    @GetMapping("/manage")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_MARKETER', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> getAllSlideManage(@Param("status")Integer status,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10")int size) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Slide> pageSlide = slideService.getAll(status, paging);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageSlide.getContent());
        response.put("currentPage", pageSlide.getNumber());
        response.put("totalItems", pageSlide.getTotalElements());
        response.put("totalPages", pageSlide.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/views")
    public ResponseEntity<?> getAllSlideViews() {
        return ResponseEntity.ok(slideService.getAllView());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_MARKETER', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> createSlide(@RequestParam("image")MultipartFile image,
                                         @RequestParam("data") String data) throws JsonProcessingException {
        Authen.check();
        ObjectMapper mapper = new ObjectMapper();
        SlideReq req = mapper.readValue(data, SlideReq.class);
        slideService.create(req ,image);
        return ResponseEntity.ok(new MessageResponse("Tạo slide thành công"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_MARKETER', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> updateSlide(@RequestParam("id")Long id,
                                         @RequestParam("image") @Nullable MultipartFile image,
                                         @RequestParam("data") String data) throws JsonProcessingException {
        Authen.check();
        ObjectMapper mapper = new ObjectMapper();
        SlideReq req = mapper.readValue(data, SlideReq.class);
        slideService.update(id, req, image);
        return ResponseEntity.ok(new MessageResponse("Cập nhật slide thành công"));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_MARKETER', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> deleteSlide(@RequestParam("id")Long id) {
        Authen.check();
        slideService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Xóa slide thành công"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_MARKETER', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> detail(@PathVariable("id")Long id){
        Authen.check();
        return ResponseEntity.ok(slideService.getDetail(id));
    }
}
