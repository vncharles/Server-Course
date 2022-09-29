package com.courses.server.controller.admin;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.WebContactRequest;
import com.courses.server.entity.WebContact;
import com.courses.server.services.WebContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/web-contact")
public class AdminWebContactController {
    @Autowired
    private WebContactService webContactService;

    @GetMapping("/")
    public ResponseEntity<List<WebContact>> listWebContact(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List<WebContact> webContacts = webContactService.getWebContactList(username);

        return ResponseEntity.ok(webContacts);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateWebContact(@RequestParam("id") Long id,
                                              @RequestBody WebContactRequest webContactRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        webContactService.updateWebContact(username, id, webContactRequest);
        return ResponseEntity.ok(new MessageResponse("Update web contact success"));
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateWebContact(@RequestParam("id") Long id,
                                              @RequestBody Map<String, Boolean> statusReq){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Boolean status = statusReq.get("status");
        System.out.println("Status: " + status);
        webContactService.updateStatusWebContact(username, id, status);
        return ResponseEntity.ok(new MessageResponse("Update status web contact success"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteWebContact(@RequestParam("id") Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        webContactService.deleteWebContact(username, id);
        return ResponseEntity.ok(new MessageResponse("Delete web contact success"));
    }

}
