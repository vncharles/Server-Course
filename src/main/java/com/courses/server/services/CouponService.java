package com.courses.server.services;

import com.courses.server.dto.request.CouponRequest;
import com.courses.server.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CouponService {
    void create(CouponRequest req);

    void update(Long id, CouponRequest req);

    void delete(Long id);

    Page<Coupon> getList(Pageable paging);

    Coupon getDetail(String code);

    Coupon getDetailById(Long id);
}
