package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.ClassRequest;
import com.courses.server.dto.response.ClassDTO;
import com.courses.server.entity.Class;
import com.courses.server.entity.ERole;
import com.courses.server.entity.Role;
import com.courses.server.repositories.RoleRepository;
import com.courses.server.services.ClassService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_TRAINER')")
        public ResponseEntity<?> getAllClass(@Param("packages") String packages){
        Authen.check();
        List<Class> classList = classService.getAllClass();

        Role role = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority rolee: auth.getAuthorities()){
            role = roleRepository.findByName(ERole.valueOf(rolee.getAuthority())).get();
        }

        List<ClassDTO> classDTOList = new ArrayList<>();
        if(packages!=null){
            for(Class _class: classList) {
                if(_class.getPackages()!=null)
                    if(_class.getPackages().contains(packages)) {
                        classDTOList.add(new ClassDTO(_class));
                    }
            }
        } else {
            for (Class _class : classList) {
                classDTOList.add(new ClassDTO(_class));
            }
        }

        List<ClassDTO> listFinal = new ArrayList<>();
        if(role.getName().equals(ERole.ROLE_TRAINER)) {
            for(ClassDTO _class: classDTOList){
                if(_class.getTrainer()!=null) {
                    if(_class.getTrainer().getUsername().equals(auth.getName())) {
                        listFinal.add(_class);
                    }
                }
            }
        } else  listFinal = classDTOList;

        return ResponseEntity.ok(listFinal);
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
