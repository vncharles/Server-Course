package com.courses.server.services;

import java.util.List;

import com.courses.server.dto.request.PermissionRequest;
import com.courses.server.entity.Permissions;

public interface PermissionService {
	List<Permissions> getPermissonListByRole(long role_id);
	
	public List<Permissions> getPermissonList();
	
	void updatePermissons(List<PermissionRequest> request);
	
	void addPermisson(Permissions permission);
}
