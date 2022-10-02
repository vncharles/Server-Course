package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.ManagerSubjectRequest;
import com.courses.server.dto.request.SubjectRequest;
import com.courses.server.dto.response.SubjectDTO;
import com.courses.server.entity.Subject;
import com.courses.server.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> allSubject(@Param("name")String name){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Subject> subjectList = subjectService.getAllSubject(username);
        if(null!=name){
            List<SubjectDTO> subjectName = new ArrayList<>();
            for(Subject subject: subjectList){
                if(subject.getName().contains(name)){
                    subjectName.add(new SubjectDTO(subject));
                }
            }
            return ResponseEntity.ok(subjectName);
        }

        List<SubjectDTO> subjectDTO = new ArrayList<>();
        for(Subject subject: subjectList){
            subjectDTO.add(new SubjectDTO(subject));
        }

        return ResponseEntity.ok(subjectDTO);
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<SubjectDTO>  subjectDetail(@PathVariable("code") String code){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Subject subject = subjectService.getSubjectByCode(username, code);

        return ResponseEntity.ok(new SubjectDTO(subject));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createSubject(@RequestBody SubjectRequest subjectRequest) {
//        System.out.println("Subject request: " + subjectRequest);
        subjectService.addSubject(subjectRequest);

        return ResponseEntity.ok(new MessageResponse("Crete subject success!"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateSubject(@Param("id")Long id, @RequestBody SubjectRequest subjectRequest) {
        subjectService.updateSubject(id, subjectRequest);
        return ResponseEntity.ok(new MessageResponse("Update subject success!"));
    }

    @PutMapping("/manager-update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> managerUpdateSubject(@RequestBody ManagerSubjectRequest subjectRequest) {
        subjectService.managerUpdateSubject(subjectRequest);
        return ResponseEntity.ok(new MessageResponse("Update subject success!"));
    }
}
