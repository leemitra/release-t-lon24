package com.github.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "one_user_group")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fist_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;
    @Column(name = "present_location", length = 100)
    private String location;

}