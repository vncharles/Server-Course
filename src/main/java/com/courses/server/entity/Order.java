package com.courses.server.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data
@AllArgsConstructor
public class Order extends BaseDomain{

    private double totalCost;
    private double totalDiscount;
    private int status;
	private String code;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Class aClass;

    @ManyToOne
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "supporter_id", referencedColumnName = "id")
    private User supporter;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderPackage> orderPackages;

    private String note;
    
    public Order() {
        if(orderPackages==null) orderPackages = new ArrayList<>();
    }

    public void increaseTotalCost(double price) {
        this.totalCost += price;
    }

    public void decreaseTotalCost(double price) {
        this.totalCost -= price;
    }

    public void increaseDiscount(double price) {
        this.totalDiscount += price;
    }

    public void decreaseDiscount(double price) {
        this.totalDiscount -= price;
    }
}