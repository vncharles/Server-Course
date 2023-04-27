package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.ManagerSubjectRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.SubjectRequest;
import com.courses.server.dto.response.SubjectDTO;
import com.courses.server.entity.Subject;
import com.courses.server.services.SubjectService;
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
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> allSubject(@Param("status") Boolean status,
            @Param("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
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

        Page<Subject> subjectList = subjectService.getAllSubject(paging, params);
        List<SubjectDTO> listSubjectDTO = subjectList.stream().map(exp -> new SubjectDTO(exp))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", listSubjectDTO);
        response.put("currentPage", subjectList.getNumber());
        response.put("totalItems", subjectList.getTotalElements());
        response.put("totalPages", subjectList.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/viewsActive")
    public ResponseEntity<?> getAllSubject() throws IOException {
        return ResponseEntity.ok(subjectService.getAllActive());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<SubjectDTO> subjectDetail(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Subject subject = subjectService.getSubjectByCode(username, id);

        return ResponseEntity.ok(new SubjectDTO(subject));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")

    public ResponseEntity<?> createSubject(@RequestBody SubjectRequest subjectRequeste) {
        subjectService.addSubject(subjectRequeste);

        return ResponseEntity.ok(new MessageResponse(" Tạo môn học thành công!"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> updateSubject(@Param("id") Long id, @RequestBody SubjectRequest subjectRequest) {
        subjectService.updateSubject(id, subjectRequest);

        return ResponseEntity.ok(new MessageResponse("Cập nhật môn học thành công!"));
    }

    @PutMapping("/manager-update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> managerUpdateSubject(@RequestBody ManagerSubjectRequest subjectRequest) {
        subjectService.managerUpdateSubject(subjectRequest);

        return ResponseEntity.ok(new MessageResponse("Cập nhật môn học thành công!"));
    }
}
