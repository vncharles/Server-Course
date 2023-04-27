package com.courses.server.services;

import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.SettingRequest;
import com.courses.server.entity.Setting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface SettingService {
    void addSetting(SettingRequest settingRequest);
    
    void updateSettings(SettingRequest settingRequest);
    
    void deleteSetting(long setting_id);
    
    Page<Setting> getListSetting(Pageable paging, Params params) throws IOException;
    
    Setting getDetailSetting(long settingId);

    List<Setting> getSettingByType(Long typeId);

    String checkRole();
}
