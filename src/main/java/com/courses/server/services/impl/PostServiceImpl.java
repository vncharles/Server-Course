package com.courses.server.services.impl;

import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.PostReq;
import com.courses.server.entity.Post;
import com.courses.server.entity.Setting;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.PostRepository;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.FileService;
import com.courses.server.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private SettingRepository settingRepository;

	@Override
	public Post getById(Long id) {
		Post post = postRepository.findById(id).get();
		if (post == null) {
			throw new NotFoundException(404, "Post  Không tồn tại!");
		}
		post.increaseView();
		postRepository.save(post);
		return post;
	}

	@Override
	public Page<Post> getAll(Pageable paging, Params params, boolean isGuest) throws IOException {
		Setting role = null;
		Authentication auth = null;
		if (!isGuest) {
			auth = SecurityContextHolder.getContext().getAuthentication();
			for (GrantedAuthority rolee : auth.getAuthorities()) {
				role = settingRepository.findByValueAndType(rolee.getAuthority(), 1).get();
			}
		}
		if (isGuest || role.getSetting_value().equals("ROLE_ADMIN") || role.getSetting_value().equals("ROLE_MANAGER") || role.getSetting_value().equals("ROLE_MARKETER")) {
			if (params.getStatus() == -1 && params.getCategory() == 0) {
				if (params.getKeyword() == null) {
					return postRepository.findAll(paging);
				} else {
					return postRepository.findAllByTitleAndBrefInfo(params.getKeyword(), paging);
				}
			} else {
				if (params.getCategory() != 0 && params.getStatus() == -1) {
					if (params.getKeyword() == null) {
						System.out.println(params.getCategory());
						return postRepository.findAllByCategory(params.getCategory(), paging);
					} else {
						return postRepository.findAllByTitleAndBrefInfoAndCategory(params.getKeyword(),
								params.getCategory(), paging);
					}
				} else if (params.getCategory() == 0 && params.getStatus() != -1) {
					if (params.getKeyword() == null) {
						return postRepository.findAllByStatus(params.getStatus(), paging);
					} else {
						return postRepository.findAllByTitleAndBrefInfoAndStatus(params.getKeyword(),
								params.getStatus(), paging);
					}
				} else {
					if (params.getKeyword() == null) {
						return postRepository.findAllByStatusAndCategory(params.getStatus(), params.getCategory(),
								paging);
					} else {
						return postRepository.findAllByStatusAndTitleAndBrefInfoAndCategory(params.getKeyword(),
								params.getStatus(), params.getCategory(), paging);
					}
				}
			}
		} else {
			User author = userRepository.findByUsername(auth.getName());
			if (params.getStatus() == -1 && params.getCategory() == 0) {
				if (params.getKeyword() == null) {
					return postRepository.findAllByAuthor(author.getId(), paging);
				} else {
					return postRepository.findAllByAuthorAndTitleAndBrefInfo(params.getKeyword(), author.getId(),
							paging);
				}
			} else {
				if (params.getCategory() != 0 && params.getStatus() == -1) {
					if (params.getKeyword() == null) {
						return postRepository.findAllByAuthorAndCategory(params.getCategory(), author.getId(), paging);
					} else {
						return postRepository.findAllByAuthorAndTitleAndBrefInfoAndCategory(params.getKeyword(),
								params.getCategory(), author.getId(), paging);
					}
				} else if (params.getCategory() == 0 && params.getStatus() != -1) {
					if (params.getKeyword() == null) {
						return postRepository.findAllByAuthorAndStatus(params.getStatus(), author.getId(), paging);
					} else {
						return postRepository.findAllByAuthorAndTitleAndBrefInfoAndStatus(params.getKeyword(),
								params.getStatus(), author.getId(), paging);
					}
				} else {
					if (params.getKeyword() == null) {
						return postRepository.findAllByAuthorAndStatusAndCategory(params.getStatus(),
								params.getCategory(), author.getId(), paging);
					} else {
						return postRepository.findAllByAuthorAndStatusAndTitleAndBrefInfoAndCategory(
								params.getKeyword(), params.getStatus(), params.getCategory(), author.getId(), paging);
					}

				}
			}
		}
	}

	@Override
	public Page<Post> getAllView(Pageable pageable) {
		return postRepository.findAllByStatus(2, pageable);
	}

	@Override
	public List<Post> getTopView(int top) {
		return postRepository.findTopByOrderByViewsDesc(top);
	}

	@Override
	public List<Post> getTopRecent(int top) {
		return postRepository.findTopRecent(top);
	}

	@Override
	public void create(PostReq postReq, MultipartFile image) {
		if (postReq.getTitle() == null) {
			throw new BadRequestException(400, "Title is require");
		}
		if (postReq.getTitle().length() < 7) {
			throw new BadRequestException(400, "Title mut be at least 7 characters");
		}
		if (postReq.getBody() == null) {
			throw new BadRequestException(400, "Body is require");
		}
		if (postReq.getBrefInfo() == null) {
			throw new BadRequestException(400, "BrefInfo is require");
		}
		if (postReq.getAuthorId() == null) {
			throw new BadRequestException(400, "Id user is require");
		}

		// System.out.println("ID: " + postReq.getAuthorId());
		User user = null;
		try {
			user = userRepository.findById(postReq.getAuthorId()).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (user == null) {
			throw new NotFoundException(404, "User  Không tồn tại!");
		}

		String fileName = fileService.storeFile(image);

		Setting category = null;
		try {
			category = settingRepository.findById(postReq.getCategoryId()).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (category == null) {
			throw new NotFoundException(404, "Category  Không tồn tại");
		}

		Post post = new Post();
		post.setTitle(postReq.getTitle());
		post.setBody(postReq.getBody());
		post.setBrefInfo(postReq.getBrefInfo());
		post.setThumnailUrl(fileName);
		post.setAuthor(user);
		post.setCategory(category);
		post.setStatus(0);
		post.setViews(0);

		postRepository.save(post);
	}

	@Override
	public void update(Long id, PostReq postReq, MultipartFile image) {
		Post post = postRepository.findById(id).get();
		if (post == null) {
			throw new NotFoundException(404, "Post  Không tồn tại!");
		}

		if (postReq.getTitle() != null) {
			post.setTitle(postReq.getTitle());
		}
		if (postReq.getBody() != null) {
			post.setBody(postReq.getBody());
		}
		if (postReq.getBrefInfo() != null) {
			post.setBrefInfo(postReq.getBrefInfo());
		}
		if (postReq.getAuthorId() != null) {
			User user = userRepository.findById(postReq.getAuthorId()).get();
			if (user == null) {
				throw new NotFoundException(404, "User  Không tồn tại!");
			}
			post.setAuthor(user);
		}
		if (postReq.getCategoryId() != null) {
			Setting category = null;
			try {
				category = settingRepository.findById(postReq.getCategoryId()).get();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (category == null) {
				throw new NotFoundException(404, "Category  Không tồn tại");
			}

			post.setCategory(category);
		}
		if (postReq.getStatus() >= 0 && postReq.getStatus() <= 4) {
			post.setStatus(postReq.getStatus());
		}
		if (image != null) {
			String fileName = fileService.storeFile(image);
			post.setThumnailUrl(fileName);
		}

		postRepository.save(post);
	}

	@Override
	public void delete(Long id) {
		Post post = postRepository.findById(id).get();
		if (post == null) {
			throw new NotFoundException(404, "Post  Không tồn tại!");
		}

		postRepository.delete(post);
	}

	@Override
	public void uploadImagePost(Long id, MultipartFile image) throws IOException {
		Post post = postRepository.findById(id).get();
		if (post == null) {
			throw new NotFoundException(404, "Post  Không tồn tại!");
		}

		String fileName = fileService.storeFile(image);

		post.setThumnailUrl(fileName);
		post.setUpdatedDate(new Timestamp(new Date().getTime()).toString());
		postRepository.save(post);
	}
}
