package com.courses.server.controller;
import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.InfoContactCourseRequest;
import com.courses.server.entity.InfoContactCourse;
import com.courses.server.services.InfoContactCourseService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/info-contact-course")
public class InfoContactCourseController {
    @Autowired
    private InfoContactCourseService infoContactCourseService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody InfoContactCourseRequest req) {
        infoContactCourseService.create(req);

        return ResponseEntity.ok(new MessageResponse("Create contact success"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> update(@RequestParam("id")Long id,
                                    @RequestBody InfoContactCourseRequest req) {
        Authen.check();
        infoContactCourseService.update(id, req);

        return ResponseEntity.ok(new MessageResponse("Update contact success"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id")Long id) {
        Authen.check();
        infoContactCourseService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Delete contact success"));
    }

    @GetMapping("")
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10")int size) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<InfoContactCourse> pageContact = infoContactCourseService.getList(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageContact.getContent());
        response.put("currentPage", pageContact.getNumber());
        response.put("totalItems", pageContact.getTotalElements());
        response.put("totalPages", pageContact.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable("id")Long id) {
        Authen.check();
        InfoContactCourse infoContact = infoContactCourseService.getDetail(id);
        return ResponseEntity.ok(infoContact);
    }
}
