package com.courses.server.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.PermissionRequest;
import com.courses.server.services.PermissionService;

@RestController
@RequestMapping("api/admin/permisson")
public class AdminPermissionController {
	@Autowired
	private PermissionService permissonService;

	@PutMapping("/updatePermission")
	public ResponseEntity<?> updateSetting(@RequestBody List<PermissionRequest> listPermission) {
		permissonService.updatePermissons(listPermission);
		return ResponseEntity.ok(new MessageResponse("Update setting success"));
	}
	
	@GetMapping("/getListPermission")
	public ResponseEntity<?> getListSetting() {
		return ResponseEntity.ok(permissonService.getPermissonList());
	}

	@GetMapping("/getListPermissionByRole")
	public ResponseEntity<?> getListType(@RequestBody PermissionRequest permissionRequest) {
		return ResponseEntity.ok(permissonService.getPermissonListByRole(permissionRequest.getRole_id()));
	}

}
