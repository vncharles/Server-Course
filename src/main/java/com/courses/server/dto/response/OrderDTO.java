package com.courses.server.dto.response;

import com.courses.server.entity.*;
import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private UserDTO user;
    private Customer customer;
    private ClassDTO aClass;
    private Coupon coupon;
    private UserDTO supporter;
    private double totalCost;
    private double totalDiscount;
    private String created_date;
    private String update_date;
    private String code;
    private String note;
    private int status;
    private List<OrderPackage> orderPackages;

    public OrderDTO(Order order) {
        this.id = order.getId();
        if (order.getUser() != null)
            this.user = new UserDTO(order.getUser());
        if (order.getCustomer() != null)
            this.customer = order.getCustomer();
        if (order.getAClass() != null)
            this.aClass = new ClassDTO(order.getAClass());
        if (order.getCoupon() != null)
            this.coupon = order.getCoupon();
        if (order.getSupporter() != null)
            this.supporter = new UserDTO(order.getSupporter());
        this.code = order.getCode();
        this.note = order.getNote();
        this.totalCost = order.getTotalCost();
        this.totalDiscount = order.getTotalDiscount();
        this.orderPackages = order.getOrderPackages();
        this.created_date = order.getCreatedDate();
        this.update_date = order.getUpdatedDate();
        this.status = order.getStatus();
    }
}