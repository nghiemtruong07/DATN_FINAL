package com.PCThanhCong.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_Id",nullable = false)
    @JsonManagedReference
    private User user;

    @Column(name = "amount")
    private Integer amount;


    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItemList;


    @PrePersist
    public void PrePersist(){
        this.amount = this.cartItemList.size();
    }

}
