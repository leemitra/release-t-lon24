package com.github.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "food_item")
    private FoodItems foodItems;

    @Column(name = "food_name", length = 100)
    private String foodName;

    @Column(name = "food_price")
    private Long foodPrice;

    @Column(name = "total_quantity")
    private Integer totalQuantity;
}
