package com.PCThanhCong.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ProductImages")
public class ProductImage implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "image_Url",nullable = false)
    private String imageUrl;


    @Column(name = "imagePublicId" , length = 500)
    private String imagePublicId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_Id")
    private Product product;


    @Column(name = "created_At")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp //Deafault now
    private Date createdAt;

    @Column(name = "updated_At")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp //Deafault now
    private Date updatedAt;

    public ProductImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
