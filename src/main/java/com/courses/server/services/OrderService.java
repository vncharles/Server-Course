package com.courses.server.services;

import com.courses.server.dto.request.ProductOrderRequest;
import com.courses.server.dto.request.OrderRequest;
import com.courses.server.dto.request.OrderRequestAdmin;
import com.courses.server.entity.Order;
import com.courses.server.entity.OrderPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface OrderService {
    void addProductOrder(ProductOrderRequest req);

    void deleteProductOrders(Long id);

    void deleteProductOrders(Long id, Boolean isOrder);

    List<OrderPackage> store();

    void createNoLogin(OrderRequest req, HttpServletRequest request);

    void createAdmin(OrderRequestAdmin req, HttpServletRequest request);

    void UpdateOrderAdmin(OrderRequestAdmin req, HttpServletRequest request, Long id);

    Page<Order> manageGetList(Pageable paging);

    Page<Order> getListOrder(Pageable pageable);

    Page<Order> getListOrderCancel(Pageable pageable);

    Page<Order> getListOrderProcess(Pageable pageable);

    Order getDetail(Long id);

    void updateStatus(Long id, Integer status, HttpServletRequest request);

    void pay(String code,HttpServletRequest request);

    Page<Order> getListOrderOffline(Integer status, String keyword, Pageable paging);

    Page<Order> getListOrderOnline(Integer status, String keyword, Pageable paging);

    Page<Order> getListOrdered(Integer status, String keyword, Pageable paging);
}