package com.github.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "food_items")
@Data
public class FoodItems {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name = "food_name", length = 100)
    private String foodName;
    @Column(name = "food_description", length = 250)
    private String description;
    @Column(name = "food_cost")
    private Integer price;
    @Column(name = "food_category", length = 100)
    private String category;
    @Column(name = "half_price")
    private Integer halfPrice;

}
