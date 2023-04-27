package com.courses.server.service.impl;

import com.courses.server.dto.request.SubjectRequest;
import com.courses.server.entity.Expert;
import com.courses.server.entity.Setting;
import com.courses.server.entity.Subject;
import com.courses.server.entity.User;
import com.courses.server.repositories.ExpertRepository;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.repositories.SubjectRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.impl.SubjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private SubjectRepository subjectRepository;

	@Mock
	private ExpertRepository expertRepository;

	@Mock
	private SettingRepository settingRepository;

	@InjectMocks
	private SubjectServiceImpl service;

	@Test
	public void addSubject() {
		SubjectRequest req = new SubjectRequest();
		req.setCode("c");
		req.setName("m");
		req.setManager("m");
		req.setName("n");
		req.setExpert(1L);
		req.setStatus(true);

		Mockito.when(settingRepository.findById(Mockito.any())).thenReturn(Optional.of(new Setting()));
		Mockito.when(expertRepository.findById(Mockito.any())).thenReturn(Optional.of(new Expert()));
		Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(new User() {{setRole(new Setting() {{setSetting_value("ROLE_SUPPORTER");}});}});

		service.addSubject(req);

		Mockito.verify(subjectRepository).save(Mockito.any());
	}

	@Test
	public void updateSubject() {
		SubjectRequest req = new SubjectRequest();
		req.setCode("c");
		req.setName("m");
		req.setManager("m");
		req.setName("n");
		req.setExpert(1L);
		req.setStatus(true);
		req.setCategoryId(1L);

		Mockito.when(subjectRepository.findById(Mockito.any())).thenReturn(Optional.of(new Subject()));
		Mockito.when(settingRepository.findById(Mockito.any())).thenReturn(Optional.of(new Setting()));
		Mockito.when(expertRepository.findById(Mockito.any())).thenReturn(Optional.of(new Expert()));
		Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(new User() {{setRole(new Setting() {{setSetting_value("ROLE_SUPPORTER");}});}});

		service.updateSubject(1L, req);

		Mockito.verify(subjectRepository).save(Mockito.any());
	}

}
