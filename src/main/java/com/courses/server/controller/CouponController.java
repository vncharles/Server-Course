package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.CouponRequest;
import com.courses.server.entity.Coupon;
import com.courses.server.services.CouponService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> create(@RequestBody CouponRequest req) {
        Authen.check();
        couponService.create(req);

        return ResponseEntity.ok(new MessageResponse("Tạo mã giảm giá thành công"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> update(@RequestParam("id")Long id,
                                    @RequestBody CouponRequest req) {
        Authen.check();
        couponService.update(id, req);

        return ResponseEntity.ok(new MessageResponse("Cập nhật mã giảm giá thành công"));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> delete(@RequestParam("id")Long id) {
        Authen.check();
        couponService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Xóa mã giảm giá thành công"));
    }

    @GetMapping("")
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10")int size) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Coupon> pageCoupon = couponService.getList(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageCoupon.getContent());
        response.put("currentPage", pageCoupon.getNumber());
        response.put("totalItems", pageCoupon.getTotalElements());
        response.put("totalPages", pageCoupon.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> detailCode(@PathVariable("code")String code) {
        Coupon coupon = couponService.getDetail(code);

        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> detailById(@PathVariable("id")Long id) {
        Coupon coupon = couponService.getDetailById(id);

        return ResponseEntity.ok(coupon);
    }
}
