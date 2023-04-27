package com.courses.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity @Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "order_package")
public class OrderPackage extends BaseDomain{
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "package_id", referencedColumnName = "id")
    private Package _package;

    @OneToOne
    @JoinColumn(name = "combo_id", referencedColumnName = "id")
    private Combo _combo;

    private double packageCost;
    @JsonIgnore
    private String activationKey;
    private boolean isActivated;
}
