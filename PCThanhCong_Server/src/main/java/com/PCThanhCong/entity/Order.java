package com.PCThanhCong.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "user_Id",nullable = false)
    private User user;


    @Column(name = "amount")
    private Integer amount;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
