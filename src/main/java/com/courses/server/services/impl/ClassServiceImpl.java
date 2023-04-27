package com.courses.server.services.impl;

import com.courses.server.dto.request.ClassRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.entity.Class;
import com.courses.server.entity.Expert;
import com.courses.server.entity.Package;
import com.courses.server.entity.Setting;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.ClassRepository;
import com.courses.server.repositories.ExpertRepository;
import com.courses.server.repositories.PackageRepository;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ClassServiceImpl implements ClassService {
	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SettingRepository settingRepository;

	@Autowired
	private PackageRepository packageRepository;

	@Autowired
	private ExpertRepository expertRepository;

	@Override
	public void addClass(ClassRequest classRequest) {
		Class _class = new Class();

		if (classRequest.getCode() == null) {
			throw new BadRequestException(400, "Please input code info");
		}
		_class.setCode(classRequest.getCode());
		if (classRequest.getDateFrom() == null) {
			throw new BadRequestException(400, "Please input start from info");
		}
		_class.setDateFrom(classRequest.getDateFrom());

		if (classRequest.getDateTo() == null) {
			throw new BadRequestException(400, "Please input to date info");
		}
		_class.setDateTo(classRequest.getDateTo());

		if (classRequest.getSchedule() == null) {
			throw new BadRequestException(400, "Please input to schedule");
		}
		_class.setSchedule(classRequest.getSchedule());

		if (classRequest.getTime() == null) {
			throw new BadRequestException(400, "Please  input to  time");
		}
		_class.setTime(classRequest.getTime());

		Package _package = null;
		try {
			_package = packageRepository.findById(classRequest.getPackages()).get();

			if (_package == null) {
				throw new NotFoundException(404, "Package  Không tồn tại!!!");
			}
			_class.setAPackage(_package);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (classRequest.isStatus() || !classRequest.isStatus()) {
			_class.setStatus(classRequest.isStatus());
		} else
			_class.setStatus(false);
		if (classRequest.getTrainer() != 0) {
			Expert trainer = expertRepository.findById(classRequest.getTrainer()).get();
			if (trainer == null) {
				throw new NotFoundException(404, "Trainer  Không tồn tại!!");
			}
			_class.setTrainer(trainer);
		} else {
			throw new NotFoundException(404, "Trainer  Không tồn tại!!");
		}

		if (classRequest.getSupporterId() == 0) {
			throw new BadRequestException(400, "Please input supporter info");
		}
		User supporter = null;
		try {
			supporter = userRepository.findById(classRequest.getSupporterId()).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (supporter == null) {
			throw new NotFoundException(404, "Suporter  Không tồn tại!!!");
		}
		_class.setSupporter(supporter);

		if (classRequest.getOnline() != null && !classRequest.getOnline()) {
			if (classRequest.getBranch() == null) {
				throw new BadRequestException(400, "Please input branch info");
			}
			Setting branch = null;
			try {
				branch = settingRepository.findByValueAndType(classRequest.getBranch(), 7).get();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (branch == null) {
				throw new NotFoundException(404, "Branch  Không tồn tại!!!");
			}
			_class.setBranch(branch);
			classRepository.save(_class);
		} else {
			classRepository.save(_class);
		}
	}

	@Override
	public void updateCLass(Long id, ClassRequest classRequest) {
		Class _class = classRepository.findById(id).get();
		if (_class == null) {
			throw new NotFoundException(404, "Class  Không tồn tại!!");
		}

		if (classRequest.getPackages() != 0) {
			Package _package = packageRepository.findById(classRequest.getPackages()).get();

			if (_package == null) {
				throw new NotFoundException(404, "Package  Không tồn tại!!!");
			}
			_class.setAPackage(_package);
		}
		if (classRequest.getDateFrom() != null) {
			_class.setDateFrom(classRequest.getDateFrom());
		}
		if (classRequest.getDateTo() != null) {
			_class.setDateTo(classRequest.getDateTo());
		}

		if (classRequest.isStatus() || !classRequest.isStatus()) {
			_class.setStatus(classRequest.isStatus());
		}
		if (classRequest.getSchedule() != null) {
			_class.setSchedule(classRequest.getSchedule());
		}
		if (classRequest.getTime() != null) {
			_class.setTime(classRequest.getTime());
		}

		if (classRequest.getTrainer() != 0) {
			Expert trainer = expertRepository.findById(classRequest.getTrainer()).orElse(null);
			if (trainer == null) {
				throw new NotFoundException(404, "Trainer  Không tồn tại!!");
			}
			_class.setTrainer(trainer);
		}
		if (classRequest.getOnline() != null) {
			if (!classRequest.getOnline()) {
				if (classRequest.getBranch() == null) {
					throw new BadRequestException(400, "Please input branch info");
				}
				Setting branch = null;
				try {
					branch = settingRepository.findByValueAndType(classRequest.getBranch(), 7).get();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (branch == null) {
					throw new NotFoundException(404, "Branch  Không tồn tại!!!");
				}
				_class.setBranch(branch);
			} else {
				_class.setBranch(null);
			}
		}

		classRepository.save(_class);
	}

	@Override
	public Page<Class> getAllClass(Pageable paging, Params params, boolean isGuest) throws IOException {
		Setting role = null;
		Authentication auth = null;
		if (!isGuest) {
			auth = SecurityContextHolder.getContext().getAuthentication();
			for (GrantedAuthority rolee : auth.getAuthorities()) {
				role = settingRepository.findByValueAndType(rolee.getAuthority(), 1).get();
			}
		}
		if (isGuest || !role.getSetting_value().equals("ROLE_SUPPORTER")) {
			if (params.getActive() == null && params.getCategory() == 0) {
				if (params.getKeyword() == null) {
					return classRepository.findAll(paging);
				} else {
					return classRepository.findAllByCodeOrName(params.getKeyword(), paging);
				}
			} else {
				if (params.getCategory() != 0 && params.getActive() == null) {
					if (params.getKeyword() == null) {
						return classRepository.findAllByTrainer(params.getCategory(), paging);
					} else {
						return classRepository.findAllByTrainerAndCodeOrName(params.getKeyword(), params.getCategory(),
								paging);
					}
				} else if (params.getCategory() == 0 && params.getActive() != null) {
					if (params.getKeyword() == null) {
						return classRepository.findAllByStatus(params.getActive(), paging);
					} else {
						return classRepository.findAllByCodeOrNameAndStatus(params.getKeyword(), params.getActive(),
								paging);
					}
				} else {
					if (params.getKeyword() == null) {
						return classRepository.findAllByTrainerAndStatus(params.getActive(), params.getCategory(),
								paging);
					} else {
						return classRepository.findAllByTrainerAndCodeOrNameAndStatus(params.getKeyword(),
								params.getActive(), params.getCategory(), paging);
					}

				}
			}
		} else {
			User supporter = userRepository.findByUsername(auth.getName());
			if (params.getActive() == null && params.getCategory() == 0) {
				if (params.getKeyword() == null) {
					return classRepository.findAllRoleSupporter(supporter.getId(), paging);
				} else {
					return classRepository.findAllByCodeOrNameRoleSupporter(params.getKeyword(), supporter.getId(),
							paging);
				}
			} else {
				if (params.getCategory() != 0 && params.getActive() == null) {
					if (params.getKeyword() == null) {
						return classRepository.findAllByTrainerRoleSupporter(params.getCategory(), supporter.getId(),
								paging);
					} else {
						return classRepository.findAllByTrainerAndCodeOrNameRoleSupporter(params.getKeyword(),
								params.getCategory(),
								supporter.getId(), paging);
					}
				} else if (params.getCategory() == 0 && params.getActive() != null) {
					if (params.getKeyword() == null) {
						return classRepository.findAllByStatusRoleSupporter(params.getActive(), supporter.getId(),
								paging);
					} else {
						return classRepository.findAllByCodeOrNameAndStatusRoleSupporter(params.getKeyword(),
								params.getActive(),
								supporter.getId(), paging);
					}
				} else {
					if (params.getKeyword() == null) {
						return classRepository.findAllByTrainerAndStatusRoleSupporter(params.getActive(),
								params.getCategory(),
								supporter.getId(), paging);
					} else {
						return classRepository.findAllByTrainerAndCodeOrNameAndStatusRoleSupporter(params.getKeyword(),
								params.getActive(), params.getCategory(), supporter.getId(), paging);
					}

				}
			}
		}
	}

	@Override
	public Class getClassDetail(Long id) {
		Class _class = classRepository.findById(id).get();
		if (_class == null) {
			throw new NotFoundException(404, "Class  Không tồn tại!!");
		}

		return _class;
	}
}
