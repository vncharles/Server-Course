package com.courses.server.services.impl;

import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.SettingRequest;
import com.courses.server.entity.*;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.repositories.TypeRepository;
import com.courses.server.services.PermissionService;
import com.courses.server.services.SettingService;
import com.courses.server.utils.PermissionList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {
	@Autowired
	private SettingRepository settingRepository;

	@Autowired
	private PermissionService permissonService;

	@Autowired
	private TypeRepository typeRepository;

	@Override
	public void addSetting(SettingRequest settingRequest) {
		Type userRole = typeRepository.findbyType_id(settingRequest.getType_id())
				.orElseThrow(() -> new NotFoundException(404, "Error: Type  Không tồn tại"));
		if (settingRepository.findByValueAndType(settingRequest.getSetting_value(), userRole.getType_id())
				.orElse(null) != null)
			throw new BadRequestException(2001, "Error: setting is existed");

		Setting setting = Setting.builder().setting_title(settingRequest.getSetting_title())
				.setting_value(settingRequest.getSetting_value()).status(true)
				.desciption(settingRequest.getDesciption()).display_order(settingRequest.getDesciption())
				.desciption(settingRequest.getDesciption()).type(userRole).build();
		settingRepository.save(setting);

		if (settingRequest.getType_id() == 1) {
			List<Setting> listSetting = settingRepository.findByType(2);
			for (Setting settingScreen : listSetting) {
				Permissions permission = Permissions.builder().get_all_data(true).can_add(false).can_delete(false)
						.can_edit(false).permissionId(new PermissionId(setting, settingScreen)).build();
				permissonService.addPermisson(permission);
			}
		} else if (settingRequest.getType_id() == 2) {
			List<Setting> listSetting = settingRepository.findByType(1);
			for (Setting settingRole : listSetting) {
				Permissions permission = Permissions.builder().get_all_data(true).can_add(false).can_delete(false)
						.can_edit(false).permissionId(new PermissionId(settingRole, setting)).build();
				permissonService.addPermisson(permission);
			}
		}
		PermissionList.resetListPermisson(settingRepository, permissonService);
	}

	@Override
	public Page<Setting> getListSetting(Pageable paging, Params params) {

		if (params.getActive() == null && params.getCategory() == 0) {
			if (params.getKeyword() == null) {
				return settingRepository.findAll(paging);
			} else {
				return settingRepository.findAllBySetting_titleContaining(params.getKeyword(), paging);
			}
		} else {
			if (params.getCategory() != 0 && params.getActive() == null) {
				if (params.getKeyword() == null) {
					return settingRepository.findAllByType((int) params.getCategory(), paging);
				} else {
					return settingRepository.findAllBySetting_titleContainingAndType(params.getKeyword(),
							(int) params.getCategory(), paging);
				}
			} else if (params.getCategory() == 0 && params.getActive() != null) {
				if (params.getKeyword() == null) {
					return settingRepository.findAllByStatus(params.getActive(), paging);
				} else {
					return settingRepository.findAllBySetting_titleContainingAndStatus(params.getKeyword(),
							params.getActive(),
							paging);
				}
			} else {
				if (params.getKeyword() == null) {
					return settingRepository.findAllByStatusAndType(params.getActive(), (int) params.getCategory(),
							paging);
				} else {
					return settingRepository.findAllByStatusAndSetting_titleContainingAndType(params.getKeyword(),
							params.getActive(), (int) params.getCategory(), paging);
				}

			}
		}
	}

	@Override
	public void updateSettings(SettingRequest settingRequest) {
		Setting setting = settingRepository.findById(settingRequest.getSetting_id()).orElse(null);
		if (setting == null)
			throw new NotFoundException(404, "Error: Setting  Không tồn tại");
		if (settingRequest.getSetting_title() != null && settingRequest.getSetting_title().trim().length() != 0)
			setting.setSetting_title(settingRequest.getSetting_title());
		if (settingRequest.getSetting_value() != null && settingRequest.getSetting_value().trim().length() != 0)
			setting.setSetting_value(settingRequest.getSetting_value());
		if (settingRequest.getDesciption() != null && settingRequest.getDesciption().trim().length() != 0)
			setting.setDesciption(settingRequest.getDesciption());
		if (settingRequest.getDisplay_order() != null && settingRequest.getDisplay_order().trim().length() != 0)
			setting.setDisplay_order(settingRequest.getDisplay_order());
		if (settingRequest.getStatus() != null)
			setting.setStatus(settingRequest.getStatus());
		if (settingRequest.getType_id() != 0) {

			Type type = typeRepository.findbyType_id(settingRequest.getType_id())
					.orElseThrow(() -> new NotFoundException(404, "Error: Type  Không tồn tại"));
			setting.setType(type);
		}
		settingRepository.save(setting);
		PermissionList.resetListPermisson(settingRepository, permissonService);
	}

	@Override
	public void deleteSetting(long setting_id) {
		Setting setting = settingRepository.findById(setting_id).orElse(null);
		if (setting == null)
			throw new NotFoundException(404, "Error: Setting  Không tồn tại");
		settingRepository.deleteById(setting_id);
	}

	@Override
	public Setting getDetailSetting(long settingId) {
		Setting setting = settingRepository.findById(settingId).orElse(null);
		if (setting == null)
			throw new NotFoundException(404, "Error: Setting  Không tồn tại");
		return setting;
	}

	@Override
	public List<Setting> getSettingByType(Long typeId) {
		return settingRepository.findAllByType(typeId);
	}

	@Override
	public String checkRole() {
		String role = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority rolee : auth.getAuthorities()) {
			role = String.valueOf(settingRepository.findByValueAndType(rolee.getAuthority(), 1).get());
		}
		return role;
	}

}
