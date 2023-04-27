package com.courses.server.services.impl;

import com.courses.server.dto.request.ManagerSubjectRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.SubjectRequest;
import com.courses.server.entity.Expert;
import com.courses.server.entity.Setting;
import com.courses.server.entity.Subject;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.ForbiddenException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.ExpertRepository;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.repositories.SubjectRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private ExpertRepository expertRepository;

	@Autowired
	private SettingRepository settingRepository;

	@Override
	public List<Subject> getAllActive() {
		return subjectRepository.findAllByStatus(true);
	}

	@Override
	public Page<Subject> getAllSubject(Pageable paging, Params params) throws IOException {
		Setting role = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority rolee : auth.getAuthorities()) {
			role = settingRepository.findByValueAndType(rolee.getAuthority(), 1).get();
		}

		if (role.getSetting_value().equals("ROLE_MANAGER")) {
			User manager = userRepository.findByUsername(auth.getName());
			if (params.getActive() == null && params.getCategory() == 0) {
				if (params.getKeyword() == null) {
					return subjectRepository.findAllByManager(manager.getId(), paging);
				} else {
					return subjectRepository.findAllByManagerAndCodeOrName(params.getKeyword(), manager.getId(),
							paging);
				}
			} else {
				if (params.getCategory() != 0 && params.getActive() == null) {
					if (params.getKeyword() == null) {
						return subjectRepository.findAllByManagerAndCategory(params.getCategory(), manager.getId(),
								paging);
					} else {
						return subjectRepository.findAllByManagerAndCodeOrNameAndCategory(params.getKeyword(),
								params.getCategory(), manager.getId(), paging);
					}
				} else if (params.getCategory() == 0 && params.getActive() != null) {
					if (params.getKeyword() == null) {
						return subjectRepository.findAllByManagerAndStatus(params.getActive(), manager.getId(), paging);
					} else {
						return subjectRepository.findAllByManagerAndCodeOrNameAndStatus(params.getKeyword(),
								params.getActive(), manager.getId(), paging);
					}
				} else {
					if (params.getKeyword() == null) {
						return subjectRepository.findAllByManagerAndStatusAndCategory(params.getActive(),
								params.getCategory(), manager.getId(), paging);
					} else {
						return subjectRepository.findAllByManagerAndStatusAndCodeOrNameAndCategory(params.getKeyword(),
								params.getActive(), params.getCategory(), manager.getId(), paging);
					}

				}
			}
		} else {
			if (params.getActive() == null && params.getCategory() == 0) {
				if (params.getKeyword() == null) {
					return subjectRepository.findAll(paging);
				} else {
					return subjectRepository.findAllByCodeOrName(params.getKeyword(), paging);
				}
			} else {
				if (params.getCategory() != 0 && params.getActive() == null) {
					if (params.getKeyword() == null) {
						System.out.println(params.getCategory());
						return subjectRepository.findAllByCategory(params.getCategory(), paging);
					} else {
						return subjectRepository.findAllByCodeOrNameAndCategory(params.getKeyword(),
								params.getCategory(), paging);
					}
				} else if (params.getCategory() == 0 && params.getActive() != null) {
					if (params.getKeyword() == null) {
						return subjectRepository.findAllByStatus(params.getActive(), paging);
					} else {
						return subjectRepository.findAllByCodeOrNameAndStatus(params.getKeyword(), params.getActive(),
								paging);
					}
				} else {
					if (params.getKeyword() == null) {
						return subjectRepository.findAllByStatusAndCategory(params.getActive(), params.getCategory(),
								paging);
					} else {
						return subjectRepository.findAllByStatusAndCodeOrNameAndCategory(params.getKeyword(),
								params.getActive(), params.getCategory(), paging);
					}

				}
			}
		}

	}

	@Override
	public Subject getSubjectByCode(String username, Long id) {
		User user = userRepository.findByUsername(username);
		Subject subject = subjectRepository.findById(id).get();

		if (null == subject) {
			throw new NotFoundException(404, "Subject  Không tồn tại!!!");
		}

		if (user.getRole().getSetting_value().equals("ROLE_ADMIN")) {
			return subject;
		}

		if (subject.getManager().getUsername().equals(username)) {
			return subject;
		}

		throw new ForbiddenException(403, "Role forbidden!!");
	}

	@Override
	public void addSubject(SubjectRequest subjectRequest) {
		// System.out.println("Subject request service: " + subjectRequest);
		if (subjectRequest.getCode() == null || subjectRequest.getName() == null)
			throw new BadRequestException(400, "Please enter enough information!!");
		if (subjectRepository.findByCode(subjectRequest.getCode()) != null) {
			throw new BadRequestException(400, "Code subject is exist");
		}

		Setting category = null;
		try {
			category = settingRepository.findById(subjectRequest.getCategoryId()).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (category == null) {
			throw new NotFoundException(404, "Category  Không tồn tại");
		}

		Subject subject = new Subject();
		subject.setCode(subjectRequest.getCode());
		subject.setName(subjectRequest.getName());
		subject.setStatus(subjectRequest.isStatus());
		subject.setNote(subjectRequest.getNote());
		subject.setCategory(category);

		if (subjectRequest.getManager() != null) {
			User manager = userRepository.findByUsername(subjectRequest.getManager());
			if (null == manager) {
				throw new NotFoundException(404, "Manager  Không tồn tại!!!");
			} else if (manager.getRole().getSetting_value().equals("ROLE_MANAGER"))
				subject.setManager(manager);
		}

		if (subjectRequest.getExpert() != null) {
			Expert expert = expertRepository.findById(subjectRequest.getExpert()).get();
			if (null == expert) {
				throw new NotFoundException(404, "Expert  Không tồn tại!!!");
			} else
				subject.setExpert(expert);
		}

		subjectRepository.save(subject);
	}

	@Override
	public void updateSubject(Long id, SubjectRequest subjectRequest) {
		Subject subject = subjectRepository.findById(id).get();
		if (null == subject) {
			throw new NotFoundException(404, "Subject  Không tồn tại!!!");
		}

		if (subjectRequest.getCode() != null)
			subject.setCode(subjectRequest.getCode());
		if (subjectRequest.getName() != null)
			subject.setName(subjectRequest.getName());
		if (subjectRequest.getNote() != null)
			subject.setNote(subjectRequest.getNote());
		if (subjectRequest.getCategoryId() != null) {
			Setting category = null;
			try {
				category = settingRepository.findById(subjectRequest.getCategoryId()).get();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (category == null) {
				throw new NotFoundException(404, "Category  Không tồn tại");
			}

			subject.setCategory(category);
		}

		subject.setStatus(subjectRequest.isStatus());

		if (subjectRequest.getManager() != null) {
			User manager = userRepository.findByUsername(subjectRequest.getManager());
			if (null == manager) {
				throw new NotFoundException(404, "Manager  Không tồn tại!!!");
			}
			subject.setManager(manager);
		}
		if (subjectRequest.getExpert() != null) {
			Expert expert = expertRepository.findById(subjectRequest.getExpert()).get();
			if (null == expert) {
				throw new NotFoundException(404, "Expert  Không tồn tại!!!");
			} else
				subject.setExpert(expert);
		}
		subject.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

		subjectRepository.save(subject);
	}

	@Override
	public void managerUpdateSubject(ManagerSubjectRequest subjectRequest) {
		Subject subject = subjectRepository.findById(subjectRequest.getId()).get();
		if (null == subject) {
			throw new NotFoundException(404, "Subject  Không tồn tại!!!");
		}

		if (subjectRequest.isStatus() == true || subjectRequest.isStatus() == false)
			subject.setStatus(subjectRequest.isStatus());
		if (subjectRequest.getExpert() != null) {
			Expert expert = expertRepository.findById(subjectRequest.getExpert()).get();
			if (null == expert) {
				throw new NotFoundException(404, "Expert  Không tồn tại!!!");
			} else
				subject.setExpert(expert);
		}
		subject.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

		subjectRepository.save(subject);
	}

	@Override
	public Subject searchByCode(String username, String code) {
		return null;
	}
}
