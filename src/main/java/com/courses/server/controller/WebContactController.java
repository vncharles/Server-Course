package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.WebContactRequest;
import com.courses.server.services.WebContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web-contact")
public class WebContactController {
    @Autowired
    private WebContactService webContactService;

    @PostMapping("/add")
    public ResponseEntity<?> addWebContact(@RequestBody WebContactRequest webContactRequest) {
        webContactService.addWebContact(webContactRequest);
        return ResponseEntity.ok(new MessageResponse("Gửi yêu cầu thành công"));
    }

}
