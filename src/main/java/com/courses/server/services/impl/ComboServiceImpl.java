package com.courses.server.services.impl;

import com.courses.server.dto.request.ComboPackageRequest;
import com.courses.server.dto.request.ComboRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.entity.Combo;
import com.courses.server.entity.ComboPackage;
import com.courses.server.entity.Package;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.ComboPackageRepository;
import com.courses.server.repositories.ComboRepository;
import com.courses.server.repositories.PackageRepository;
import com.courses.server.services.ComboService;
import com.courses.server.services.FileService;

import io.jsonwebtoken.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Service
public class ComboServiceImpl implements ComboService {
    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private PackageRepository packageRepository;
    
    @Autowired
	private FileService fileService;

    @Autowired
    private ComboPackageRepository comboPackageRepository;

    @Override
    public void create(ComboRequest req,  MultipartFile image) {
        if(req.getTitle()==null){
            throw new BadRequestException(400, "Title is required");
        }
        if(req.getDescription()==null){
            throw new BadRequestException(400, "Title is required");
        }

        Combo combo = new Combo(req.getTitle(), req.getDescription());
        Set<ComboPackage> packages = new HashSet<>();
        for(ComboPackageRequest cpReq: req.getPackages()) {
            Package _package = null;
            try {
                _package = packageRepository.findById(cpReq.getPackageId()).get();
            } catch (Exception ex) {ex.printStackTrace();}
            if(_package==null) {
                throw new NotFoundException(404, "Package  Không tồn tại");
            }

            packages.add(new ComboPackage(combo, _package, cpReq.getSalePrice()));
        }
		String fileName = fileService.storeFile(image);
        combo.setImage(fileName);
        combo.setComboPackages(packages);
        comboRepository.save(combo);
    }

    @Override
    public void update(Long id, ComboRequest req,  MultipartFile image) {
        Combo combo = comboRepository.findById(id).get();
        if(combo==null) {
            throw new NotFoundException(404, "Combo  Không tồn tại");
        }

        if(req.getTitle()!=null) {
            combo.setTitle(req.getTitle());
        }
        if(req.getDescription()!=null) {
            combo.setDescription(req.getDescription());
        }

        if(req.getPackages().size()>0) {
            for(ComboPackageRequest cpReq: req.getPackages()) {
                boolean fl = true;
                for(ComboPackage cp: combo.getComboPackages()) {
                    if(cpReq.getPackageId()==cp.get_package().getId()) {
                        cp.setSalePrice(cpReq.getSalePrice());
                        try {
                            comboPackageRepository.save(cp);
                        } catch (Exception ex) {ex.printStackTrace();}
                        fl = false;
                        break;
                    }
                }
                if(fl) {
                    Package _package = null;
                    try {
                        _package = packageRepository.findById(cpReq.getPackageId()).get();
                    } catch (Exception ex) {ex.printStackTrace();}
                    if(_package==null) {
                        throw new NotFoundException(404, "Package  Không tồn tại");
                    }

                    combo.getComboPackages().add(new ComboPackage(combo, _package, cpReq.getSalePrice()));
                }
            }

            for (ComboPackage cp: combo.getComboPackages()) {
                boolean fl = true;
                for(ComboPackageRequest cpReq: req.getPackages()) {
                    if(cp.get_package().getId()==cpReq.getPackageId()){
                        fl = false;
                        break;
                    }
                }
                if(fl) {
                    combo.getComboPackages().remove(cp);
                    try {
                        comboPackageRepository.delete(cp);
                    } catch (Exception ex) {ex.printStackTrace();}
                }
            }
        }

        if (image != null) {
			String fileName = fileService.storeFile(image);
			combo.setImage(fileName);
		}

        comboRepository.save(combo);
    }

    @Override
    public void delete(Long id) {
        Combo combo = comboRepository.findById(id).get();
        if(combo==null) {
            throw new NotFoundException(404, "Combo  Không tồn tại");
        }

        comboRepository.delete(combo);
    }

    @Override
    public Page<Combo> getList(Pageable pageable, Params params) throws IOException {
    	if (params.getKeyword() == null) {
			    return comboRepository.findAll(pageable);
		} else {
			return comboRepository.findAllByTitleContainingOrDescriptionContaining(params.getKeyword(),params.getKeyword(), pageable);
		}
    }

    @Override
    public Combo getDetail(Long id) {
        Combo combo = comboRepository.findById(id).get();
        if(combo==null) {
            throw new NotFoundException(404, "Combo  Không tồn tại");
        }
        return combo;
    }
}