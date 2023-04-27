package com.courses.server.services.impl;

import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.WebContactRequest;
import com.courses.server.entity.Setting;
import com.courses.server.entity.User;
import com.courses.server.entity.WebContact;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.repositories.WebContactRepository;
import com.courses.server.services.WebContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class WebContactServiceImpl implements WebContactService {
    @Autowired
    private WebContactRepository webContactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public void addWebContact(WebContactRequest webContactRequest) {
        Setting category = null;
        try {
            category = settingRepository.findById(webContactRequest.getCategoryId()).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (category == null) {
            throw new NotFoundException(404, "Category  Không tồn tại");
        }

        WebContact webContact = new WebContact(
                webContactRequest.getFullName(),
                webContactRequest.getEmail(),
                webContactRequest.getPhoneNumber(),
                webContactRequest.getMessage(),
                false);
        webContact.setCategory(category);

        webContactRepository.save(webContact);
    }

    @Override
    public Page<WebContact> getWebContactList(Pageable paging, Params params) {
        Setting role = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority rolee : auth.getAuthorities()) {
            role = settingRepository.findByValueAndType(rolee.getAuthority(), 1).get();
        }
        if (!role.getSetting_value().equals("ROLE_SUPPORTER")) {
            if (params.getActive() == null && params.getCategory() == 0) {
                if (params.getKeyword() == null) {
                    return webContactRepository.findAll(paging);
                } else {
                    return webContactRepository.findAllByFullNameOrEmail(params.getKeyword(), paging);
                }
            } else {
                if (params.getCategory() != 0 && params.getActive() == null) {
                    if (params.getKeyword() == null) {
                        return webContactRepository.findAllByCategory(params.getCategory(), paging);
                    } else {
                        return webContactRepository.findAllByFullNameOrEmailAndCategory(params.getKeyword(),
                                params.getCategory(), paging);
                    }
                } else if (params.getCategory() == 0 && params.getActive() != null) {
                    if (params.getKeyword() == null) {
                        return webContactRepository.findAllByStatus(params.getActive(), paging);
                    } else {
                        return webContactRepository.findAllByFullNameOrEmailAndStatus(params.getKeyword(),
                                params.getActive(),
                                paging);
                    }
                } else {
                    if (params.getKeyword() == null) {
                        return webContactRepository.findAllByStatusAndCategory(params.getActive(), params.getCategory(),
                                paging);
                    } else {
                        return webContactRepository.findAllByStatusAndFullNameOrEmailAndCategory(params.getKeyword(),
                                params.getActive(), params.getCategory(), paging);
                    }

                }
            }
        } else {
            User supporter = userRepository.findByUsername(auth.getName());
            if (params.getActive() == null && params.getCategory() == 0) {
                if (params.getKeyword() == null) {
                    return webContactRepository.findAllRoleSupporter(supporter.getId(), paging);
                } else {
                    return webContactRepository.findAllByFullNameOrEmailRoleSupporter(params.getKeyword(),
                            supporter.getId(), paging);
                }
            } else {
                if (params.getCategory() != 0 && params.getActive() == null) {
                    if (params.getKeyword() == null) {
                        return webContactRepository.findAllByCategoryRoleSupporter(params.getCategory(),
                                supporter.getId(), paging);
                    } else {
                        return webContactRepository.findAllByFullNameOrEmailAndCategoryRoleSupporter(
                                params.getKeyword(),
                                params.getCategory(), supporter.getId(), paging);
                    }
                } else if (params.getCategory() == 0 && params.getActive() != null) {
                    if (params.getKeyword() == null) {
                        return webContactRepository.findAllByStatusRoleSupporter(params.getActive(), supporter.getId(),
                                paging);
                    } else {
                        return webContactRepository.findAllByFullNameOrEmailAndStatusRoleSupporter(params.getKeyword(),
                                params.getActive(),
                                supporter.getId(), paging);
                    }
                } else {
                    if (params.getKeyword() == null) {
                        return webContactRepository.findAllByStatusAndCategoryRoleSupporter(params.getActive(),
                                params.getCategory(),
                                supporter.getId(), paging);
                    } else {
                        return webContactRepository.findAllByStatusAndFullNameOrEmailAndCategoryRoleSupporter(
                                params.getKeyword(),
                                params.getActive(), params.getCategory(), supporter.getId(), paging);
                    }

                }
            }
        }
    }

    @Override
    public void updateWebContact(String username, Long id, WebContactRequest webContactRequest) {
        if (!userRepository.existsByUsername(username)) {
            throw new BadRequestException(1302, "account has not login");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<WebContact> webContactFind = webContactRepository.findById(id);
        WebContact webContact = webContactFind.get();

        if (webContactRequest.getNote() != null)
            webContact.setNote(webContactRequest.getNote());
      
        webContact.setSupporter(userRepository.findByUsername(auth.getName()));
        webContactRepository.save(webContact);
    }

    @Override
    public void updateStatusWebContact(String username, Long id, boolean status) {
        if (!userRepository.existsByUsername(username)) {
            throw new BadRequestException(1302, "account has not login");
        }

        Optional<WebContact> webContactFind = webContactRepository.findById(id);
        WebContact webContact = webContactFind.get();

        webContact.setStatus(!status);
        webContact.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

        webContactRepository.save(webContact);
    }

    @Override
    public void deleteWebContact(String username, Long id) {
        if (!userRepository.existsByUsername(username)) {
            throw new BadRequestException(1302, "account has not login");
        }

        webContactRepository.deleteById(id);
    }

   
    @Override
	public WebContact getClassDetail(Long id) {
		WebContact contact = webContactRepository.findById(id).get();
		if (contact == null) {
			throw new NotFoundException(404, "Thông tin hỗ trợ  Không tồn tại!!");
		}

		return contact;
	}
}
