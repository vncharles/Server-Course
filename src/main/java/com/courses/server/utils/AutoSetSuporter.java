package com.courses.server.utils;

import java.util.List;

import com.courses.server.entity.Setting;
import com.courses.server.entity.User;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.repositories.UserRepository;

public class AutoSetSuporter {

	private static User last_supporter;
	private static int index_supporter;

	public static User setSupporter(SettingRepository settingRepository, UserRepository userRepository) {
		Setting role = settingRepository.findByValueAndType("ROLE_SUPPORTER", 1).get();
		List<User> suppoters = userRepository.findAllByRole(role.getSetting_id());
		System.out.println(last_supporter);
		if (suppoters.size() > 0) {
			if (last_supporter == null) {
				last_supporter = suppoters.get(0);
				index_supporter = 0;
			} else {
				for (int i = 0; i < suppoters.size(); i++) {
					if (suppoters.get(i).equals(last_supporter)) {
						if (i == suppoters.size() - 1) {
							last_supporter = suppoters.get(0);
							index_supporter = 0;
						} else {
							last_supporter = suppoters.get(i + 1);
							index_supporter = i + 1;
						}
						break;
					}
					if (i == suppoters.size() - 1) {
						last_supporter = suppoters.get(index_supporter);
					}
				}
			}
		}
		return last_supporter;
	}
}
