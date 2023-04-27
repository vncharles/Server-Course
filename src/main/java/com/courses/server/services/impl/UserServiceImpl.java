package com.courses.server.services.impl;

import com.courses.server.dto.request.*;
import com.courses.server.dto.response.JwtResponse;
import com.courses.server.entity.*;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.ForbiddenException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.*;
import com.courses.server.security.jwt.JwtUtils;
import com.courses.server.security.services.UserDetailsImpl;
import com.courses.server.services.ExpertService;
import com.courses.server.services.FileService;
import com.courses.server.services.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SettingRepository settingRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private ExpertRepository expertRepository;

	@Autowired
	private ExpertService expertService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserPackageRepository userPackageRepository;

	@Autowired
	private OrderPackageRepository orderPackageRepository;

	@Override
	public String createAccount(RegisterRequest registerDTO) {
		if (registerDTO.getEmail() == null) {
			throw new BadRequestException(1200, "Email is required");
		}
		if (registerDTO.getUsername() == null) {
			throw new BadRequestException(1000, "Username is required");
		}
		if (registerDTO.getPassword() == null) {
			throw new BadRequestException(1100, "Password is required");
		}
		if (registerDTO.getPhone() == null) {
			throw new BadRequestException(1100, "Phone Number is required");
		}

		User user = userRepository.findByEmail(registerDTO.getEmail()).orElse(null);
		if (user != null) {
			throw new BadRequestException(1201, "Email has already existed");
		} else if (!registerDTO.getEmail().toLowerCase().matches(
				"^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {
			throw new BadRequestException(1203, "Email invalid");
		}

		User userCheckUsername = userRepository.findByUsername(registerDTO.getUsername());
		if (userCheckUsername != null) {
			throw new BadRequestException(1001, "Username has already existed");
		} else if (!registerDTO.getUsername().matches("^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")) {
			throw new BadRequestException(1003,
					"Username invalid, username include letters and digits, at least 6, case-insensitive");
		}

		if (!registerDTO.getPassword()
				.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,20}$")) {
			throw new BadRequestException(1101,
					"Password invalid, Password must be at least 8 characters with at least 1 special character 1 uppercase letter 1 lowercase letter and 1 number!");
		}

		if (user == null) {
			user = new User(registerDTO.getEmail(), registerDTO.getUsername(),
					encoder.encode(registerDTO.getPassword()), registerDTO.getPhone(), false);
			if (registerDTO.getFullName() != null) {
				user.setFullname(registerDTO.getFullName());
			}
		} else {
			user.setUsername(registerDTO.getUsername());
			user.setPassword(encoder.encode(registerDTO.getPassword()));
			user.setFullname(registerDTO.getFullName());
		}

		// Nếu user bth không có set role thì set thành ROLE_USER
		Setting userRole = settingRepository.findByValueAndType("ROLE_GUEST", 1)
				.orElseThrow(() -> new NotFoundException(404, "Error: Role  Không tồn tại"));

		user.setRole(userRole);

		String token = RandomString.make(30);

		user.setRegisterToken(token);
		user.setTimeRegisterToken(LocalDateTime.now());

		userRepository.save(user);

		return user.getRegisterToken();
	}

	@Override
	public void verifyRegister(User user) {
		user.setActive(true);
		user.setRegisterToken(null);
		userRepository.save(user);

		// Trainee trainee = null;
		// try {
		// trainee = traineeRepository.findByUser(user);
		// } catch (Exception ex) {ex.printStackTrace();}
		// if (trainee!=null) {
		// trainee.setStatus(true);
		// traineeRepository.save(trainee);
		// }
	}

	@Override
	public JwtResponse loginAccount(String email, String password) {
		if (!userRepository.existsByUsername(email)) {
			throw new NotFoundException(1002, "username has not existed");
		}

		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
		} catch (Exception ex) {
			throw new BadRequestException(1102, "wrong password");
		}
	}

	public void updateResetPasswordToken(String token, String email) throws NotFoundException {
		User customer = userRepository.findByEmail(email).orElse(null);
		if (customer != null) {
			customer.setResetPasswordToken(token);
			userRepository.save(customer);
		} else {
			throw new BadRequestException(1202, "email has not existed");
		}
	}

	public User getByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}

	public void updatePassword(User customer, String newPassword) {
		if (newPassword == null || newPassword == "") {
			throw new BadRequestException(1100, "Vui lòng nhập mật khẩu");
		} else if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,20}$")) {
			throw new BadRequestException(1101,
					"Sai định dạng mật khẩu vui lòng nhập ít nhất 8 kí tự có ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 kí tự đặc biệt");
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		customer.setPassword(encodedPassword);

		customer.setResetPasswordToken(null);
		customer.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

		userRepository.save(customer);
	}

	@Override
	public User getByRegisterToken(String token) {
		return userRepository.findByRegisterToken(token);
	}

	@Override
	public Page<User> getListUser(Pageable paging, Params params) throws IOException {
		if (params.getActive() == null && params.getCategory() == 0) {
			if (params.getKeyword() == null) {
				return userRepository.findAll(paging);
			} else {
				return userRepository.findAllByFullnameOrEmail(params.getKeyword(), paging);
			}
		} else {
			if (params.getCategory() != 0 && params.getActive() == null) {
				if (params.getKeyword() == null) {
					return userRepository.findAllByRole(params.getCategory(), paging);
				} else {
					return userRepository.findAllByFullnameOrEmailAndRole(params.getKeyword(), params.getCategory(),
							paging);
				}
			} else if (params.getCategory() == 0 && params.getActive() != null) {
				if (params.getKeyword() == null) {
					return userRepository.findAllByActive(params.getActive(), paging);
				} else {
					return userRepository.findAllByFullnameOrEmailAndActive(params.getKeyword(), params.getActive(),
							paging);
				}
			} else {
				if (params.getKeyword() == null) {
					return userRepository.findAllByActiveAndRole(params.getActive(), params.getCategory(), paging);
				} else {
					return userRepository.findAllByActiveAndFullnameOrEmailAndRole(params.getKeyword(),
							params.getActive(), params.getCategory(), paging);
				}

			}
		}
	}

	@Override
	public Page<User> getListManager(Pageable paging) {
		Setting role = settingRepository.findByValueAndType("ROLE_MANAGER", 1).get();
		return userRepository.findAllByRole(role.getSetting_id(), paging);
	}

	@Override
	public Page<Expert> getListExpert(Pageable paging) {
		return expertRepository.findAll(paging);
	}

	@Override
	public Page<User> getListTrainer(Pageable paging) {
		Setting role = settingRepository.findByValueAndType("ROLE_TRAINER", 1).get();
		return userRepository.findAllByRole(role.getSetting_id(), paging);
	}

	@Override
	public Page<User> getListSupporter(Pageable paging) {
		Setting role = settingRepository.findByValueAndType("ROLE_SUPPORTER", 1).get();
		return userRepository.findAllByRole(role.getSetting_id(), paging);
	}

	@Override
	public void updateRole(RoleRequest roleDTO) {
		String username = roleDTO.getUsername();

		if (!userRepository.existsByUsername(username)) {
			throw new NotFoundException(1002, "username has not existed");
		}
		User user = userRepository.findByUsername(username);
		Setting userRole = settingRepository.findByValueAndType(roleDTO.getRole(), 1)
				.orElseThrow(() -> new NotFoundException(404, "Error: Role  Không tồn tại"));
		if (!userRole.getSetting_value().equals(user.getRole().getSetting_value())) {
			if (user.getRole().getSetting_value().equals("ROLE_EXPERT")) {
				Expert expert = expertRepository.findByUser(user).orElse(null);
				if (expert != null) {
					expertService.delete(expert.getId());
				}
			}
			if (userRole.getSetting_value().equals("ROLE_EXPERT")) {
				Expert expert = new Expert("", "", false, "", user);
				expertRepository.save(expert);
			}
			user.setRole(userRole);
			user.setUpdatedDate(new Timestamp(new Date().getTime()).toString());
			userRepository.save(user);
		}
	}

	@Override
	public void updateUser(Long id, String username, UserUpdateRequest updateDTO) {
		if (!userRepository.existsById(id)) {
			throw new BadRequestException(1302, "Tài khoản không tồn tại");
		}

		User user = userRepository.findById(id).get();

		if (username != null) {
			if (user.getId() != userRepository.findByUsername(username).getId()) {
				throw new ForbiddenException(403, "Error forbidden!");
			}
		}

		if (updateDTO.getUsername() != null) {
			if (userRepository.existsByUsername(updateDTO.getUsername())) {
				throw new BadRequestException(1001, "Tên tài khoản đã tồn tại");
			}
			user.setUsername(updateDTO.getUsername());
		}
		if (updateDTO.getPhoneNumber() != null)
			user.setPhoneNumber(updateDTO.getPhoneNumber());
		if (updateDTO.getFullname() != null)
			user.setFullname(updateDTO.getFullname());
		if (updateDTO.getPassword() != null) {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), updateDTO.getOldPassword()));
			if (authentication == null) {
				throw new BadRequestException(1402, "sai mật khẩu cũ");
			}
			if (!updateDTO.getPassword()
					.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,20}$")) {
				throw new BadRequestException(1101,
						"Mật khẩu không đúng định dạng! mật khẩu phải có ít nhất 8 kí tự bào gồm chữ thường, chữ hoa và số");
			}
			user.setPassword(encoder.encode(updateDTO.getPassword()));
		}
		if (updateDTO.getNote() != null)
			user.setNote(updateDTO.getNote());
		user.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

		userRepository.save(user);
	}

	@Override
	public User getUserDetail(String username) {
		if (!userRepository.existsByUsername(username)) {
			throw new BadRequestException(1302, "account has not login");
		}

		return userRepository.findByUsername(username);
	}

	@Override
	public void updateAvatar(String username, MultipartFile file) throws IOException {
		User user = userRepository.findByUsername(username);
		String fileName = fileService.storeFile(file);
		user.setAvatar(fileName);
		user.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

		userRepository.save(user);
	}

	@Override
	public void updateActive(String username, UpdateActiveUserRequest activeUserDTO) {
		if (!userRepository.existsByUsername(username)) {
			throw new BadRequestException(1302, "Tài khoản ch được đăng nhập");
		}
		if (!userRepository.existsByUsername(activeUserDTO.getUsername())) {
			throw new NotFoundException(1002, "Tên tài khoản không tồn tại");
		}

		User user = userRepository.findByUsername(activeUserDTO.getUsername());
		user.setActive(!activeUserDTO.isStatus());
		user.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

		userRepository.save(user);
	}

	@Override
	public Page<UserPackage> listMyCourses(Pageable pageable) {
		// System.out.println("Count supporter: " + userRepository.countSupporter());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = userRepository.findByUsername(username);

		return userPackageRepository.findAllByUser(user, pageable);
	}

	@Override
	public UserPackage myCourseDetail(Long id) {
		UserPackage userPackage = null;
		try {
			userPackage = userPackageRepository.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (userPackage == null) {
			throw new NotFoundException(404, "Sản phẩm Không tồn tại");
		}

		return userPackage;
	}

	@Override
	public void activeCourse(String code) {
		if(code == null || code.length() ==0) {
			throw new NotFoundException(404, "Vui lòng nhập mã kích hoạt");
		}
		OrderPackage orderPackage = null;
		try {
			orderPackage = orderPackageRepository.findByActivationKey(code);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (orderPackage == null)
			throw new NotFoundException(404, "Mã kích hoặt không chính xác");
		if (orderPackage.isActivated())
			throw new BadRequestException(400, "Mã kích hoạt đã được sử dụng");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = userRepository.findByUsername(username);

		if (orderPackage.get_package() != null) {
			UserPackage userPackage = new UserPackage();
			userPackage.setUser(user);
			userPackage.setAPackage(orderPackage.get_package());
			userPackage.setOrder(orderPackage.getOrder());
			userPackage.setValidFrom(new Date());
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, orderPackage.get_package().getDuration());
			userPackage.setStatus(true);
			userPackage.setValidTo(c.getTime());
			userPackageRepository.save(userPackage);
		} else {
			for (ComboPackage element : orderPackage.get_combo().getComboPackages()) {
				UserPackage userPackage = new UserPackage();
				userPackage.setUser(user);
				userPackage.setAPackage(element.get_package());
				userPackage.setOrder(orderPackage.getOrder());
				userPackage.setValidFrom(new Date());
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.MONTH, element.get_package().getDuration());
				userPackage.setValidTo(c.getTime());
				userPackage.setStatus(true);
				userPackageRepository.save(userPackage);
			}
		}

		orderPackage.setActivated(true);
		orderPackageRepository.save(orderPackage);
	}

}
