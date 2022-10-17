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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> allSubject(@Param("status")Boolean status,
                                        @Param("code") String code){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Subject> subjectList = subjectService.getAllSubject(username);

        List<SubjectDTO> subjectDTO = new ArrayList<>();
        for(Subject subject: subjectList){
            subjectDTO.add(new SubjectDTO(subject));
        }

        List<SubjectDTO> subjectDTOStatus = new ArrayList<>();
        if(status!=null){
            for(SubjectDTO subject: subjectDTO){
                if(subject.isStatus()==status.booleanValue()){
                    subjectDTOStatus.add(subject);
                }
            }
        } else subjectDTOStatus = subjectDTO;

        List<SubjectDTO> subjectDTOCode = new ArrayList<>();
        if(code!=null || code != ""){
            for(SubjectDTO subject: subjectDTOStatus){
                if(subject.getCode().contains(code)){
                    subjectDTOCode.add(subject);
                }
            }
        } else subjectDTOCode = subjectDTOStatus;

        return ResponseEntity.ok(subjectDTOCode);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<SubjectDTO>  subjectDetail(@PathVariable("id") Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Subject subject = subjectService.getSubjectByCode(username, id);

        return ResponseEntity.ok(new SubjectDTO(subject));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createSubject(@RequestBody SubjectRequest subjectRequeste) {
        subjectService.addSubject(subjectRequeste);

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
