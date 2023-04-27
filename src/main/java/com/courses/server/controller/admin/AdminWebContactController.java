package com.courses.server.controller.admin;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.WebContactRequest;
import com.courses.server.entity.WebContact;
import com.courses.server.services.WebContactService;
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
import java.util.Map;

@RestController
@RequestMapping("api/admin/web-contact")
public class AdminWebContactController {
    @Autowired
    private WebContactService webContactService;

    @GetMapping("")
    public ResponseEntity<?> listWebContact(@RequestParam(defaultValue = "0") int page,
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

        Page<WebContact> pageWebContacts = webContactService.getWebContactList(paging, params);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageWebContacts.getContent());
        response.put("currentPage", pageWebContacts.getNumber());
        response.put("totalItems", pageWebContacts.getTotalElements());
        response.put("totalPages", pageWebContacts.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_TRAINER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getClassDetail(@PathVariable("id") Long id) {
        Authen.check();
        WebContact contact = webContactService.getClassDetail(id);

        return ResponseEntity.ok(contact);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateWebContact(@RequestParam("id") Long id,
            @RequestBody WebContactRequest webContactRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        webContactService.updateWebContact(username, id, webContactRequest);
        return ResponseEntity.ok(new MessageResponse("Cập nhật chăm sóc khách hàng thông công"));
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateWebContact(@RequestParam("id") Long id,
            @RequestBody Map<String, Boolean> statusReq) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Boolean status = statusReq.get("status");
        webContactService.updateStatusWebContact(username, id, status);
        return ResponseEntity.ok(new MessageResponse("Cập nhật trạng thái thông công"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteWebContact(@RequestParam("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        webContactService.deleteWebContact(username, id);
        return ResponseEntity.ok(new MessageResponse("Xóa chăm sóc khách hàng thông công"));
    }

}
