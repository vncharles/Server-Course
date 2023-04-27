package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.UserUpdateRequest;
import com.courses.server.dto.response.ClassDTO;
import com.courses.server.dto.response.MyCoursesDTO;
import com.courses.server.dto.response.UserDTO;
import com.courses.server.entity.Trainee;
import com.courses.server.entity.User;
import com.courses.server.entity.UserPackage;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.services.FileService;
import com.courses.server.services.TraineeService;
import com.courses.server.services.UserService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private TraineeService traineeService;

    @PutMapping("/update-info")
    public ResponseEntity<?> updateInfo(@RequestParam("id") Long id, @RequestBody UserUpdateRequest updateDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authen.check();

        userService.updateUser(id, auth.getName(), updateDTO);
        return ResponseEntity.ok(new MessageResponse("Cập nhật thông tin thành công"));
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile avatar) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (username == null || username.equals("anonymousUser")) {
            throw new BadRequestException(1302, "Người dùng chưa đăng nhập");
        }
        userService.updateAvatar(username, avatar);
        return ResponseEntity.ok(new MessageResponse("Cập nhật ảnh địa diện thành công"));
    }

    @GetMapping("/info")
    public ResponseEntity<UserDTO> userDetail(HttpServletRequest request) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Username: " + username);
        User user = userService.getUserDetail(username);

        UserDTO userResponse = new UserDTO(user);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // logger.info("Could not determine file type.");
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/my-courses")
    public ResponseEntity<?> listMyCourses(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<UserPackage> pageUserPackage = userService.listMyCourses(paging);
        List<MyCoursesDTO> myCoursesDTOS = pageUserPackage.getContent().stream().map(up -> new MyCoursesDTO(up))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", myCoursesDTOS);
        response.put("currentPage", pageUserPackage.getNumber());
        response.put("totalItems", pageUserPackage.getTotalElements());
        response.put("totalPages", pageUserPackage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-courses/{id}")
    public ResponseEntity<?> myCourseDetail(@PathVariable("id") Long id) {
        Authen.check();

        UserPackage userPackage = userService.myCourseDetail(id);

        return ResponseEntity.ok(new MyCoursesDTO(userPackage));
    }

    @PostMapping("/active-course")
    public ResponseEntity<?> activeCourse(@RequestParam("code") String code) {
        Authen.check();
        userService.activeCourse(code);

        return ResponseEntity.ok(new MessageResponse("Kích hoạt khóa học thành công"));
    }

    @GetMapping("/list-my-class")
    public ResponseEntity<?> listMyClass(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Trainee> pageTrainee = traineeService.getListTraineDetail(paging);
        List<ClassDTO> listClass = pageTrainee.getContent().stream().map(p -> new ClassDTO(p.getAClass()))
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("data", listClass);
        response.put("currentPage", pageTrainee.getNumber());
        response.put("totalItems", pageTrainee.getTotalElements());
        response.put("totalPages", pageTrainee.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
