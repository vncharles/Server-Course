package com.courses.server.controller.admin;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.UpdateActiveUserRequest;
import com.courses.server.dto.request.UserUpdateRequest;
import com.courses.server.dto.response.UserDTO;
import com.courses.server.entity.ERole;
import com.courses.server.entity.User;
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
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
public class AdminAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUser(@Param("name")String name,
                                        @Param("email")String email,
                                        @Param("phone")String phone) throws IOException {
        List<User> users = userService.getListUser();

//        if(name!=null){
//            for(User user: users) {
//                if(user.getFullname()!=null)
//                    if(!user.getFullname().contains(name))
//                        users.remove(user);
//            }
//        }
//        if(email!=null){
//            for(User user: users) {
//                if(user.getEmail()!=null)
//                    if(!user.getEmail().contains(email))
//                        users.remove(user);
//            }
//        }
//        if(phone!=null){
//            for(User user: users) {
//                if(user.getPhoneNumber()!=null)
//                    if(!user.getPhoneNumber().contains(phone))
//                        users.remove(user);
//            }
//        }

        List<UserDTO> userDTOList = new ArrayList<>();

        for(User user: users) {
            if(user!=null)
                userDTOList.add(new UserDTO(user));
        }

        List<UserDTO> listUserByName = new ArrayList<>();
        if(name!=null){
            for(UserDTO user: userDTOList) {
                if(user.getFullname()!=null)
                    if(user.getFullname().contains(name))
                        listUserByName.add(user);
            }
        } else listUserByName = userDTOList;

        List<UserDTO> listUserByEmail = new ArrayList<>();
        if(email!=null){
            for(UserDTO user: listUserByName) {
                if(user.getEmail()!=null)
                    if(user.getEmail().contains(email))
                        listUserByEmail.add(user);
            }
        } else listUserByEmail = listUserByName;

        List<UserDTO> listUserByPhone = new ArrayList<>();
        if(phone!=null){
            for(UserDTO user: listUserByEmail) {
                if(user.getPhoneNumber()!=null)
                    if(user.getPhoneNumber().contains(phone))
                        listUserByPhone.add(user);
            }
        } else listUserByPhone = listUserByEmail;


        return ResponseEntity.ok(listUserByPhone);
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
    public ResponseEntity<?> findUserByUsername(@Validated @PathVariable("id")Long id) throws IOException {
        User user = userRepository.findById(id).get();
        return ResponseEntity.ok(new UserDTO(user));
    }

    @PostMapping("/update-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestParam("id")Long id, @RequestBody UserUpdateRequest userUpdate) {
        userService.updateUser(id, null, userUpdate);
        return ResponseEntity.ok(new MessageResponse("Update user success"));
    }
}
