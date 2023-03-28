package com.PCThanhCong.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cartItems")
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount",nullable = false)
    private Integer amount;

    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "cart_Id")
    private Cart cart;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "product_Id")
    private Product product;

    public CartItem(Integer amount, Cart cart, Product product) {
        this.amount = amount;
        this.cart = cart;
        this.product = product;
    }
}
