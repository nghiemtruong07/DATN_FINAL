package com.PCThanhCong.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.PCThanhCong.constants.RoleEnum;
import com.PCThanhCong.constants.StatusCodeEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "`users`")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "fullname", nullable = false, unique = true)
    private  String fullName;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone", nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(name = "`role`")
    private String role;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "`status`", nullable = false)
    private StatusCodeEnum status;

    @Column(name = "avatar",nullable = false,length = 500)
    private String avatar;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Cart cart;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private  Order order;

    @PrePersist
    public void prePersist() {
        if (this.role == null)
            this.role = RoleEnum.CLIENT;
        if(this.status == null)
            this.status = StatusCodeEnum.NOT_ACTIVE;
        this.password = new BCryptPasswordEncoder().encode(this.password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public StatusCodeEnum getStatus() {
        return status;
    }

    public void setStatus(StatusCodeEnum status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
