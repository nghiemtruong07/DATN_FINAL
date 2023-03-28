package com.PCThanhCong.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "`PRODUCT_RATES`" )
public class ProductRate implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_Id",nullable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_Id",nullable = false)
    private Product product;

    @Column(name = "`value`",nullable = false)
    private short value;

    @Column(name = "`comment`",length = 1000, nullable = false)
    private String comment;

    @Column(name = "created_At")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "update_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

}
