package com.courses.server.controller.admin;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.UpdateActiveUserRequest;
import com.courses.server.dto.request.UserUpdateRequest;
import com.courses.server.dto.response.UserDTO;
import com.courses.server.entity.ERole;
import com.courses.server.entity.Role;
import com.courses.server.entity.User;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.RoleRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.UserService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUser(@Param("role") ERole role,
                                        @Param("status")Boolean status,
                                        @Param("name")String name,
                                        @Param("size")Integer size) throws IOException {
        List<User> users = userService.getListUser();

        List<UserDTO> userDTOList = new ArrayList<>();

        for(User user: users) {
            if(user!=null)
                userDTOList.add(new UserDTO(user));
        }

        List<UserDTO> listUserByRole = new ArrayList<>();
        if(role!=null && roleRepository.findByName(role)!=null){
            ERole eRole = roleRepository.findByName(role).get().getName();
            for(UserDTO user: userDTOList) {
                if(user.getRole().name().equals(eRole.name()))
                    listUserByRole.add(user);
            }
        } else listUserByRole = userDTOList;

        List<UserDTO> listUserByStatus = new ArrayList<>();
        if(status!=null){
            for(UserDTO user: listUserByRole) {
                if(user.isActive()==status.booleanValue())
                    listUserByStatus.add(user);
            }
        } else listUserByStatus = listUserByRole;

        List<UserDTO> listUserByName = new ArrayList<>();
        if(name!=null){
            for(UserDTO user: listUserByStatus) {
                if(user.getFullname()!=null)
                    if(user.getFullname().contains(name))
                        listUserByName.add(user);
            }
        } else listUserByName = listUserByStatus;

        List<UserDTO> listFinal = new ArrayList<>();
        System.out.println("Size: " + size);
        if(size!=null && size.intValue() <= listUserByName.size()){
            for(int i = 0; i < size.intValue(); i++) {
                listFinal.add(listUserByName.get(i));
            }
        } else  listFinal = listUserByName;

        return ResponseEntity.ok(listFinal);
    }

    @GetMapping("/manager-list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserListManager() throws IOException, InterruptedException {
        List<User> users = userService.getListUser();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user: users) {
            if(user.getRole().getName().equals(ERole.ROLE_MANAGER) && user.isActive())
                userDTOList.add(new UserDTO(user));
        }
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/expert-list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> getUserListExpert() throws IOException, InterruptedException {
        List<User> users = userService.getListUser();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user: users) {
            if(user.getRole().getName().equals(ERole.ROLE_EXPERT) && user.isActive())
                userDTOList.add(new UserDTO(user));
        }
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/trainer-list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> getUserListTrainer() throws IOException, InterruptedException {
        List<User> users = userService.getListUser();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user: users) {
            if(user.getRole().getName().equals(ERole.ROLE_TRAINER) && user.isActive())
                userDTOList.add(new UserDTO(user));
        }
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/trainee-list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER', 'ROLE_TRAINER')")
    public ResponseEntity<?> getUserListTrainee() throws IOException, InterruptedException {
        List<User> users = userService.getListUser();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user: users) {
            if(user.getRole().getName().equals(ERole.ROLE_TRAINEE) && user.isActive())
                userDTOList.add(new UserDTO(user));
        }
        return ResponseEntity.ok(userDTOList);
    }

    @PostMapping("/active")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateActiveUser(@Validated @RequestBody UpdateActiveUserRequest activeUserDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Authen.check();

        userService.updateActive(username, activeUserDTO);

        return ResponseEntity.ok(new MessageResponse("Update status user success"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findUserByUsername(@PathVariable("id")Long id) throws IOException {
        User user = userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
        if(user==null){
            throw new NotFoundException(404, "ID not found user");
        }
        return ResponseEntity.ok(new UserDTO(user));
    }

    @PostMapping("/update-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestParam("id")Long id, @RequestBody UserUpdateRequest userUpdate) {
        userService.updateUser(id, null, userUpdate);
        return ResponseEntity.ok(new MessageResponse("Update user success"));
    }
}
