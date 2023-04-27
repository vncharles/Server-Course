package com.courses.server.utils;

import java.util.HashMap;
import java.util.List;
import com.courses.server.dto.response.ScreenDTO;
import com.courses.server.entity.Permissions;
import com.courses.server.entity.Setting;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.services.PermissionService;

public class PermissionList {

	private static HashMap<String, HashMap<String, ScreenDTO>> listPermisson;

	//lấy quyền màn hình của role từ database
	private static void setListPermisson(SettingRepository settingRepository, PermissionService permissonService) {
		listPermisson = new HashMap<String, HashMap<String, ScreenDTO>>();
		List<Setting> listRole = settingRepository.findByType(1);
		for (Setting setting : listRole) {
			HashMap<String, ScreenDTO> rolePermisson = new HashMap<String, ScreenDTO>();
			List<Permissions> permissons = permissonService.getPermissonListByRole(setting.getSetting_id());
			for (Permissions permisson : permissons) {
				new ScreenDTO();
				rolePermisson.put(permisson.getPermissionId().getScreen_id().getSetting_value(),
						ScreenDTO.builder().get_all_data(permisson.getGet_all_data())
								.can_add(permisson.getCan_add()).can_delete(permisson.getCan_delete())
								.can_edit(permisson.getCan_edit()).build());
			}
			listPermisson.put(setting.getSetting_value(), rolePermisson);
		}
	}
	//check quyền của role với màn hình và action
	public static Boolean checkRole(SettingRepository settingRepository, PermissionService permissonService, String role,
			 String screen, String action) {

		if (listPermisson == null || listPermisson.size() == 0)
			setListPermisson(settingRepository, permissonService);

		HashMap<String, ScreenDTO> permissions = listPermisson.get(role);

		if (permissions == null || permissions.size() == 0) {
			return false;
		} else {
			ScreenDTO screenSetting = permissions.get(screen);
			if (screenSetting == null) {
				return false;
			} else {
				switch (action) {
				case "get_all":
					return screenSetting.getGet_all_data();
				case "add":
					return screenSetting.getCan_add();
				case "delete":
					return screenSetting.getCan_delete();
				case "edit":
					return screenSetting.getCan_edit();
				default:
					return false;
				}
			}
		}
	}
	//lấy quyền của role với màn hình
	public static ScreenDTO getSettingRoleScreen(SettingRepository settingRepository, PermissionService permissonService, String role,
			 String screen) {

		if (listPermisson == null || listPermisson.size() == 0)
			setListPermisson(settingRepository, permissonService);

		HashMap<String, ScreenDTO> permissions = listPermisson.get(role);

		if (permissions == null || permissions.size() == 0) {
			return null;
		} else {
			return permissions.get(screen);
		}
	}
	//reset list Permisson
	public static void resetListPermisson(SettingRepository settingRepository, PermissionService permissonService) {
		setListPermisson(settingRepository, permissonService);
	}
}
