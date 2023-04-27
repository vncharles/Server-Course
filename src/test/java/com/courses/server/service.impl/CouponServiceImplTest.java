package com.courses.server.service.impl;

import com.courses.server.dto.request.CouponRequest;
import com.courses.server.entity.Coupon;
import com.courses.server.repositories.CouponRepository;
import com.courses.server.services.impl.CouponServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CouponServiceImplTest {

  @Mock
  private CouponRepository couponRepository;

  @InjectMocks
  private CouponServiceImpl service;

  @Test
  public void create() {
    CouponRequest req = new CouponRequest();
    req.setQuantity(1);
    req.setDiscountRate(1);
    req.setStatus(Boolean.TRUE);
    req.setValidFrom(new Date());
    req.setValidTo(new Date());

    service.create(req);
    Mockito.verify(couponRepository).save(Mockito.any());
  }

  @Test
  public void update() {
    CouponRequest req = new CouponRequest();
    req.setQuantity(1);
    req.setDiscountRate(1);
    req.setStatus(Boolean.TRUE);
    req.setValidFrom(new Date());
    req.setValidTo(new Date());

    Mockito.when(couponRepository.findById(Mockito.any())).thenReturn(Optional.of(new Coupon()));

    service.update(1L, req);
    Mockito.verify(couponRepository).save(Mockito.any());
  }
}
