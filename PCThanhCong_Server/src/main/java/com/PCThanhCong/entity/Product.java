package com.PCThanhCong.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.PCThanhCong.constants.StatusCodeProductEnum;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`title`",length = 255,nullable = false)
    private String title;

    @Column(name = "`descriptions`",length = 1000,nullable = false)
    private String descriptions;

    @Column(name = "originalPrice",nullable = false)
    private Integer originalPrice;

    @Column(name = "promotionPrice",nullable = false)
    private Integer promotionPrice;

    @Column(name = "`created_Date`")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

//    @Column
//    private Float avgRate;

    @Column(name = "amount",nullable = false)
    private Integer amount;

    @Column(name = "`status`" , columnDefinition = "1")
    private StatusCodeProductEnum status;

    public Product(String title, String descriptions, int originalPrice, int promotionPrice, Integer amount) {
        this.title = title;
        this.descriptions = descriptions;
        this.originalPrice = originalPrice;
        this.promotionPrice = promotionPrice;
        this.amount = amount;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "categoryId",nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    @Cascade(value = {org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    @Cascade(value = {org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<ProductRate> productRatesList;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cascade(value = {org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<CartItem> cartItemList;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Cascade(value = {org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<OrderItem> orderItems;

    @PrePersist
    public void PrePersist(){
        if(this.status == null)
            this.status = StatusCodeProductEnum.OPENING;
        if(this.promotionPrice == null)
            this.promotionPrice = originalPrice;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Integer promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public StatusCodeProductEnum getStatus() {
        return status;
    }

    public void setStatus(StatusCodeProductEnum status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<ProductRate> getProductRatesList() {
        return productRatesList;
    }

    public void setProductRatesList(List<ProductRate> productRatesList) {
        this.productRatesList = productRatesList;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
