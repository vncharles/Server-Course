package com.courses.server.services.impl;

import com.courses.server.dto.request.ExpertRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.entity.Expert;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.ExpertRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.ExpertService;
import com.courses.server.services.FileService;

import io.jsonwebtoken.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExpertServiceImpl implements ExpertService {
	@Autowired
	private ExpertRepository expertRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Expert getById(Long id) {
		Expert expert = null;
		try {
			expert = expertRepository.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (expert == null) {
			throw new NotFoundException(404, "Giảng viên Không tồn tại!");
		}

		return expert;
	}

	@Override
	public Page<Expert> getAllManage(Pageable pageable, Params params) throws IOException {
		if (params.getKeyword() == null) {
			return expertRepository.findAll(pageable);
		} else {
			return expertRepository.findAllByJobTitleContainingOrDescriptionContaining(params.getKeyword(),
					params.getKeyword(), pageable);
		}
	}

	@Override
	public Page<Expert> getAllView(Pageable pageable) {
//		System.out.println("Cout expert: " + expertRepository.count());
		return expertRepository.findAllByStatus(true, pageable);
	}

	@Override
	public void create(ExpertRequest req) {
		if (req.getDescription() == null || req.getCompany() == null || req.getJobTitle() == null
				|| req.getStatus() == null || req.getUserId() == null) {
			throw new BadRequestException(400, "Please input full information");
		}
		User user = null;
		try {
			user = userRepository.findById(req.getUserId()).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (user == null) {
			throw new NotFoundException(404, "User trainer  Không tồn tại");
		}
		Expert expert = new Expert(req.getCompany(), req.getJobTitle(),  req.getStatus(),req.getDescription(), user);
		expertRepository.save(expert);
	}

	@Override
	public void update(Long id, ExpertRequest req, MultipartFile image) {
		Expert expert = null;
		try {
			expert = expertRepository.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (expert == null) {
			throw new NotFoundException(404, "Expert  Không tồn tại!");
		}
		if (req.getDescription() != null && req.getDescription().trim().length() > 0) {
			expert.setDescription(req.getDescription());
		} else if (req.getDescription() == null) {
			if (expert.getDescription() == null || expert.getDescription().trim().length() == 0)
				throw new NotFoundException(404, "Description  Không tồn tại!");
		} else {
			throw new NotFoundException(404, "Description  Không tồn tại!");
		}
		if (req.getCompany() != null && req.getCompany().trim().length() > 0) {
			expert.setCompany(req.getCompany());
		} else if (req.getCompany() == null) {
			if (expert.getCompany() == null || expert.getCompany().trim().length() == 0)
				throw new NotFoundException(404, "Company  Không tồn tại!");
		} else {
			throw new NotFoundException(404, "Company  Không tồn tại!");
		}
		if (req.getJobTitle() != null && req.getJobTitle().trim().length() > 0) {
			expert.setJobTitle(req.getJobTitle());
		} else if (req.getJobTitle() == null) {
			if (expert.getJobTitle() == null || expert.getJobTitle().trim().length() == 0)
				throw new NotFoundException(404, "JobTitle  Không tồn tại!");
		} else {
			throw new NotFoundException(404, "JobTitle  Không tồn tại!");
		}
		if (req.getStatus() != null) {
			expert.setStatus(req.getStatus());
		}
		if (req.getUserId() != null) {
			User user = null;
			try {
				user = userRepository.findById(req.getUserId()).get();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (user == null) {
				throw new NotFoundException(404, "User trainer  Không tồn tại");
			}
			if (req.getUsername() != null && req.getUsername().trim().length() > 0) {
				user.setUsername(req.getUsername());
			} else if (req.getUsername() == null) {
				if (user.getUsername() == null || user.getUsername().trim().length() == 0)
					throw new NotFoundException(404, "Username  Không tồn tại!");
			} else {
				throw new NotFoundException(404, "Username  Không tồn tại!");
			}
			if (req.getFullname() != null && req.getFullname().trim().length() > 0) {
				user.setFullname(req.getFullname());
			} else if (req.getFullname() == null) {
				if (user.getFullname() == null || user.getFullname().trim().length() == 0)
					throw new NotFoundException(404, "Fullname  Không tồn tại!");
			} else {
				throw new NotFoundException(404, "Fullname  Không tồn tại!");
			}
			if (req.getPhone() != null && req.getPhone().trim().length() > 0) {
				user.setPhoneNumber(req.getPhone());
			} else if (req.getPhone() == null) {
				if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().length() == 0)
					throw new NotFoundException(404, "PhoneNumber  Không tồn tại!");
			} else { 
				throw new NotFoundException(404, "PhoneNumber  Không tồn tại!");
			}
			if (image != null) {
				String fileName = fileService.storeFile(image);
				user.setAvatar(fileName);
				user.setUpdatedDate(new Timestamp(new Date().getTime()).toString());
			} else {
				if (user.getAvatar() == null || user.getAvatar().trim().length() == 0)
					throw new NotFoundException(404, "Avatar  Không tồn tại!");
			}
			userRepository.save(user);
		}
		expertRepository.save(expert);
	}

	@Override
	public void delete(Long id) {
		Expert expert = null;
		try {
			expert = expertRepository.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (expert == null) {
			throw new NotFoundException(404, "Expert  Không tồn tại!");
		}

		expertRepository.delete(expert);
	}
}
