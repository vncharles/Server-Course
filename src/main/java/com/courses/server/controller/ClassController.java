package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.ClassRequest;
import com.courses.server.dto.response.ClassDTO;
import com.courses.server.entity.Class;
import com.courses.server.services.ClassService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_TRAINER')")
    public ResponseEntity<?> getAllClass(@RequestParam("package")String packages){
        Authen.check();
        List<Class> classList = classService.getAllClass();

        List<ClassDTO> classDTOList = new ArrayList<>();
        if(packages!=null || packages!=""){
            for(Class _class: classList) {
                if(_class.getPackages().contains(packages)) {
                    classDTOList.add(new ClassDTO(_class));
                }
            }
        } else {
            for (Class _class : classList) {
                classDTOList.add(new ClassDTO(_class));
            }
        }

        return ResponseEntity.ok(classDTOList);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_TRAINER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getClassDetail(@PathVariable("id")Long id) {
        Authen.check();
        Class _class = classService.getClassDetail(id);

        return ResponseEntity.ok(new ClassDTO(_class));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<?> createClass(@RequestBody ClassRequest classRequest) {
        Authen.check();
        classService.addClass(classRequest);

        return ResponseEntity.ok(new MessageResponse("Create class success"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> updateClass(@RequestParam("id") Long id, @RequestBody ClassRequest classRequest) {
        Authen.check();
        classService.updateCLass(id, classRequest);

        return ResponseEntity.ok(new MessageResponse("Update class success"));
    }
}
