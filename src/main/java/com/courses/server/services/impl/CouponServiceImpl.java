package com.courses.server.services.impl;

import com.courses.server.dto.request.CouponRequest;
import com.courses.server.entity.Coupon;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.CouponRepository;
import com.courses.server.services.CouponService;
import com.courses.server.utils.RandomCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponRepository couponRepository;

    @Override
    public void create(CouponRequest req) {
        if (req.getQuantity() == null || req.getDiscountRate() == null || req.getStatus() == null || req.getValidFrom() == null
                || req.getValidTo() == null) {
            throw new BadRequestException(400, "Please input full info");
        }

        String code = RandomCode.getAlphaNumericString(8).toUpperCase();

        Coupon coupon = new Coupon(code ,req.getQuantity(), req.getDiscountRate(), req.getStatus(), req.getValidFrom(), req.getValidTo());

        couponRepository.save(coupon);
    }

    @Override
    public void update(Long id, CouponRequest req) {
        Coupon coupon = null;
        try {
            coupon = couponRepository.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (coupon == null) {
            throw new NotFoundException(404, "Coupon  Không tồn tại");
        }
        if (req.getDiscountRate() != null)
            coupon.setDiscountRate(req.getDiscountRate());
        if (req.getQuantity() != null)
            coupon.setQuantity(req.getQuantity());
        if (req.getStatus() != null)
            coupon.setStatus(req.getStatus());
        if (req.getValidFrom() != null)
            coupon.setValidFrom(req.getValidFrom());
        if (req.getValidTo() != null)
            coupon.setValidTo(req.getValidTo());
        couponRepository.save(coupon);
    }

    @Override
    public void delete(Long id) {
        Coupon coupon = null;
        try {
            coupon = couponRepository.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (coupon == null) {
            throw new NotFoundException(404, "Coupon Không tồn tại");
        }
        couponRepository.delete(coupon);
    }

    @Override
    public Page<Coupon> getList(Pageable paging) {
        return couponRepository.findAll(paging);
    }

    @Override
    public Coupon getDetail(String code) {
        Coupon coupon = null;
        try {
            coupon = couponRepository.findByCodeAccess(code).orElse(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (coupon == null) {
            throw new NotFoundException(404, "Coupon  Không tồn tại");
        }

        return coupon;
    }
    @Override
    public Coupon getDetailById(Long id) {
        Coupon coupon = null;
        try {
            coupon = couponRepository.findById(id).orElse(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (coupon == null) {
            throw new NotFoundException(404, "Coupon  Không tồn tại");
        }

        return coupon;
    }
}
