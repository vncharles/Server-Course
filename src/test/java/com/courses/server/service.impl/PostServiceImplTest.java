package com.courses.server.service.impl;

import com.courses.server.dto.request.PostReq;
import com.courses.server.entity.Post;
import com.courses.server.entity.Setting;
import com.courses.server.entity.User;
import com.courses.server.repositories.PostRepository;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.FileService;
import com.courses.server.services.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class PostServiceImplTest {

	@Mock
	private PostRepository postRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private FileService fileService;

	@Mock
	private SettingRepository settingRepository;

	@InjectMocks
	private PostServiceImpl service;

	@Test
	public void create() {
		PostReq req = new PostReq();
		req.setBody("b");
		req.setBrefInfo("b");
		req.setStatus(2);
		req.setAuthorId(1L);
		req.setCategoryId(1L);
		req.setTitle("ttttttttttt");

		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
		Mockito.when(settingRepository.findById(Mockito.any())).thenReturn(Optional.of(new Setting()));

		service.create(req, null);

		Mockito.verify(postRepository).save(Mockito.any());
	}

	@Test
	public void update() {
		PostReq req = new PostReq();
		req.setBody("b");
		req.setBrefInfo("b");
		req.setStatus(2);
		req.setAuthorId(1L);
		req.setCategoryId(1L);
		req.setTitle("ttttttttttt");

		Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(new Post()));
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(new User()));
		Mockito.when(settingRepository.findById(Mockito.any())).thenReturn(Optional.of(new Setting()));

		service.update(1L, req, null);

		Mockito.verify(postRepository).save(Mockito.any());
	}


}
