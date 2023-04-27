package com.courses.server.services.impl;

import com.courses.server.dto.request.PackageReq;
import com.courses.server.dto.request.Params;
import com.courses.server.entity.Package;
import com.courses.server.entity.Subject;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.PackageRepository;
import com.courses.server.repositories.SubjectRepository;
import com.courses.server.services.FileService;
import com.courses.server.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {
	@Autowired
	private PackageRepository packageRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private FileService fileService;

	@Override
	public void create(PackageReq req, MultipartFile image) {
		System.out.println("Req: " + req);
		if (req.getTitle() == null) {
			throw new BadRequestException(400, "Title is required");
		}
		if (req.getExcerpt() == null) {
			throw new BadRequestException(400, "excerpt is required");
		}
		if (req.getDuration() == null) {
			throw new BadRequestException(400, "duration is required");
		}
		if (req.getStatus() == null) {
			throw new BadRequestException(400, "status is required");
		}
		if (req.getListPrice() == null) {
			throw new BadRequestException(400, "list_price is required");
		}
		if (req.getSalePrice() == null) {
			throw new BadRequestException(400, "sale_price is required");
		}

		Subject subject = null;
		try {
			subject = subjectRepository.findById(req.getSubjectId()).get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (subject == null) {
			throw new NotFoundException(404, "Subject  Không tồn tại");
		}
		String fileName = fileService.storeFile(image);

		Package _package = new Package(req.getTitle(), req.getExcerpt(), req.getDuration(), fileName, 0, req.getDescription(), req.getStatus(), req.getListPrice(), req.getSalePrice(), subject);
		packageRepository.save(_package);
	}

	@Override
	public void update(Long id, PackageReq req, MultipartFile image) {
		Package _package = packageRepository.findById(id).get();
		if (_package == null) {
			throw new NotFoundException(404, "Package  Không tồn tại");
		}

		if (req.getTitle() != null) {
			_package.setTitle(req.getTitle());
		}
		if (req.getExcerpt() != null) {
			_package.setExcerpt(req.getExcerpt());
		}
		if (req.getDuration() != null) {
			_package.setDuration(req.getDuration());
		}
		if (req.getDescription() != null) {
			_package.setDescription(req.getDescription());
		}
		if (req.getStatus() != null) {
			_package.setStatus(req.getStatus());
		}
		if (req.getListPrice() != null) {
			_package.setListPrice(req.getListPrice());
		}
		if (req.getSalePrice() != null) {
			_package.setSalePrice(req.getSalePrice());
		}

		if (req.getSubjectId() != null) {
			Subject subject = null;
			try {
				subject = subjectRepository.findById(req.getSubjectId()).get();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			if (subject == null) {
				throw new NotFoundException(404, "Subject  Không tồn tại");
			}
			_package.setSubject(subject);
		}

		if (image != null) {
			String fileName = fileService.storeFile(image);
			_package.setImage(fileName);
		}

		packageRepository.save(_package);
	}

	@Override
	public void delete(Long id) {
		Package _package = packageRepository.findById(id).get();
		if (_package == null) {
			throw new NotFoundException(404, "Package  Không tồn tại");
		}

		packageRepository.delete(_package);
	}

	@Override
	public Page<Package> getList(Pageable paging, Params params) throws IOException {
		if (params.getActive() == null && params.getCategory() == 0) {

			if (params.getKeyword() == null) {
				return packageRepository.findAll(paging);
			} else {
				return packageRepository.findAllByTitleOrDescription(params.getKeyword(), paging);
			}
		} else {
			if (params.getCategory() != 0 && params.getActive() == null) {
				if (params.getKeyword() == null) {
					return packageRepository.findAllBySubject(params.getCategory(), paging);
				} else {
					return packageRepository.findAllByTitleOrDescriptionAndSubject(params.getKeyword(),
							params.getCategory(), paging);
				}
			} else if (params.getCategory() == 0 && params.getActive() != null) {
				if (params.getKeyword() == null) {
					return packageRepository.findAllByStatus(params.getActive(), paging);
				} else {
					return packageRepository.findAllByTitleOrDescriptionAndStatus(params.getKeyword(),
							params.getActive(), paging);
				}
			} else {
				if (params.getKeyword() == null) {
					return packageRepository.findAllByStatusAndSubject(params.getActive(), params.getCategory(),
							paging);
				} else {
					return packageRepository.findAllByTitleOrDescriptionAndStatusAndSubject(params.getKeyword(),
							params.getActive(), params.getCategory(), paging);
				}
			}
		}
	}

	@Override
	public List<Package> getTopView(int top) {
		return packageRepository.findTopByOrderByViewsDesc(top);
	}

	@Override
	public Package getDetail(Long id) {
		Package _package = packageRepository.findById(id).get();
		if (_package == null) {
			throw new NotFoundException(404, "Package  Không tồn tại");
		}
		_package.increaseView();
		packageRepository.save(_package);
		return _package;
	}
}
