package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.OrderRequest;
import com.courses.server.dto.request.OrderRequestAdmin;
import com.courses.server.dto.request.ProductOrderRequest;
import com.courses.server.dto.response.OrderDTO;
import com.courses.server.entity.Order;
import com.courses.server.entity.OrderPackage;
import com.courses.server.services.OrderService;
import com.courses.server.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody ProductOrderRequest req) {
        Authen.check();
        orderService.addProductOrder(req);

        return ResponseEntity.ok(new MessageResponse("Thêm sản phẩm khỏi giỏ hàng thành công"));
    }

    @DeleteMapping("/remove-from-cart")
    public ResponseEntity<?> removeFromCart(@RequestParam(name = "id", required = true) Long id) {
        Authen.check();
        orderService.deleteProductOrders(id);
        return ResponseEntity.ok(new MessageResponse("Xóa sản phẩm khỏi giỏ hàng thành công"));
    }

    @DeleteMapping("/remove-order")
    public ResponseEntity<?> removexOrder(@RequestParam(name = "id", required = true) Long id) {
        Authen.check();
        orderService.deleteProductOrders(id, true);
        return ResponseEntity.ok(new MessageResponse("Hủy đơn hàng thành công"));
    }

    @DeleteMapping("/remove-product-from-order")
    public ResponseEntity<?> removeProductFromOrder(@RequestParam(name = "id", required = true) Long id) {
        Authen.check();
        orderService.deleteProductOrders(id, false);
        return ResponseEntity.ok(new MessageResponse("Xóa sản phẩm khỏi đơn hàng thành công"));
    }

    @GetMapping("/cart")
    public ResponseEntity<?> listCart() {
        Authen.check();
        List<OrderPackage> response = orderService.store();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrderRequest req, HttpServletRequest request) {
        orderService.createNoLogin(req, request);

        return ResponseEntity.ok(new MessageResponse("Tạo đơn hàng thanh công"));
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody OrderRequestAdmin req, HttpServletRequest request) {
        orderService.createAdmin(req, request);

        return ResponseEntity.ok(new MessageResponse("Tạo đơn hàng thanh công"));
    }

    @PutMapping("/updateOrderAdmin")
    public ResponseEntity<?> UpdateOrderAdmin(@RequestBody OrderRequestAdmin req, HttpServletRequest request,
            @RequestParam("id") Long id) {
        orderService.UpdateOrderAdmin(req, request, id);

        return ResponseEntity.ok(new MessageResponse("Cập nhật đơn hàng tahfnh công"));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Order> pageOrder = orderService.manageGetList(paging);

        List<OrderDTO> orderDTOS = pageOrder.getContent().stream().map(order -> new OrderDTO(order))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", orderDTOS);
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-order")
    public ResponseEntity<?> listOrder(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Order> pageOrder = orderService.getListOrder(paging);

        List<OrderDTO> orderDTOS = pageOrder.getContent().stream().map(order -> new OrderDTO(order))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", orderDTOS);
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-order-cancel")
    public ResponseEntity<?> listOrderCancel(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Order> pageOrder = orderService.getListOrderCancel(paging);

        List<OrderDTO> orderDTOS = pageOrder.getContent().stream().map(order -> new OrderDTO(order))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", orderDTOS);
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-order-process")
    public ResponseEntity<?> listOrderProcess(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Order> pageOrder = orderService.getListOrderProcess(paging);

        List<OrderDTO> orderDTOS = pageOrder.getContent().stream().map(order -> new OrderDTO(order))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", orderDTOS);
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") Long id) {
        Authen.check();
        Order order = orderService.getDetail(id);

        return ResponseEntity.ok(new OrderDTO(order));
    }

    @PutMapping("/update-status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> updateStatus(@RequestParam("id") Long id,
            @RequestParam("status") Integer status,
            HttpServletRequest request) {
        Authen.check();
        orderService.updateStatus(id, status, request);

        return ResponseEntity.ok(new MessageResponse("Cập nhật đơn hàng thành công"));
    }

    @PutMapping("/pay")
    public ResponseEntity<?> pay(@RequestParam("codeCoupon") String codeCoupon, HttpServletRequest request) {
        Authen.check();

        orderService.pay(codeCoupon, request);

        return ResponseEntity.ok(new MessageResponse("Đăng ký khóa học thành công"));
    }

    @GetMapping("/list-off")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> listCoursesOff(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Param("status") Integer status,
            @Param("packageId") Long packageId,
            @Param("keyword") String keyword) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Order> pageOrder = orderService.getListOrderOffline(status, keyword, paging);
        List<OrderDTO> orderDTOS = pageOrder.getContent().stream().map(o -> new OrderDTO(o))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", orderDTOS);
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-onli")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> listCoursesOnli(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Param("status") Integer status,
            @Param("packageId") Long packageId,
            @Param("keyword") String keyword) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Order> pageOrder = orderService.getListOrderOnline(status, keyword, paging);
        List<OrderDTO> orderDTOS = pageOrder.getContent().stream().map(o -> new OrderDTO(o))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", orderDTOS);
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-confirm")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> listCoursesConfirm(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Param("packageId") Long packageId,
            @Param("keyword") String keyword) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Order> pageOrder = orderService.getListOrdered(3, keyword, paging);
        List<OrderDTO> orderDTOS = pageOrder.getContent().stream().map(o -> new OrderDTO(o))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", orderDTOS);
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-cancel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORTER')")
    public ResponseEntity<?> listCoursesCancel(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Param("packageId") Long packageId,
            @Param("keyword") String keyword) {
        Authen.check();
        Pageable paging = PageRequest.of(page, size);

        Page<Order> pageOrder = orderService.getListOrdered(4, keyword, paging);
        List<OrderDTO> orderDTOS = pageOrder.getContent().stream().map(o -> new OrderDTO(o))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", orderDTOS);
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());

        return ResponseEntity.ok(response);
    }
}