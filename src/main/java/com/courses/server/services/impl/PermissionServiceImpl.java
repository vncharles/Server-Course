package com.courses.server.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.courses.server.dto.request.PermissionRequest;
import com.courses.server.entity.PermissionId;
import com.courses.server.entity.Permissions;
import com.courses.server.entity.Setting;
import com.courses.server.repositories.PermissionRespository;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.services.PermissionService;
import com.courses.server.utils.PermissionList;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRespository permissionRespository;

	@Autowired
	private SettingRepository settingRepository;

	@Override
	public List<Permissions> getPermissonListByRole(long role_id) {
		List<Permissions> permissons = permissionRespository.findByRoleId(role_id);
		return permissons;
	}

	@Override
	public List<Permissions> getPermissonList() {
		List<Permissions> permissons = permissionRespository.findAll();
		return permissons;
	}

	@Override
	public void addPermisson(Permissions permission) {
		permissionRespository.save(permission);
	}

	@Override
	public void updatePermissons(List<PermissionRequest> requests) {
		for (PermissionRequest permisson : requests) {
			Setting role = settingRepository.findById(permisson.getRole_id()).orElse(null);
			Setting screen = settingRepository.findById(permisson.getScreen_id()).orElse(null);
			if (role == null || screen == null)
				continue;

			permissionRespository.save(Permissions.builder().can_add(permisson.isCan_add())
					.can_delete(permisson.isCan_delete()).can_edit(permisson.isCan_edit())
					.get_all_data(permisson.isGet_all_data()).permissionId(new PermissionId(role, screen)).build());
		}
		PermissionList.resetListPermisson(settingRepository, this);
	}

}
