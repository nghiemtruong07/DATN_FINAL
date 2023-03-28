package com.PCThanhCong.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.PCThanhCong.constants.StatusOrderItem;
//import com.smartphoneshop.constants.StatusOrderItemConvert;
import com.PCThanhCong.constants.StatusOrderItemConvert;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "created_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp //default now
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;

    @Column(name = "received_Date",columnDefinition = "null")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date receivedDate;


    @Column(name = "amount" ,  nullable = false)
    private Integer amount;


    @Column(name = "`status`",columnDefinition = "Processing")
    @Convert(converter = StatusOrderItemConvert.class)
    private StatusOrderItem status;


    @ManyToOne
    @JoinColumn(name = "order_Id")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_Id")
    @JsonManagedReference
    private Product product;


    @PrePersist
    public void PrePersist(){
        if(this.status == null)
            this.status = StatusOrderItem.Processing;
    }

    public OrderItem(Integer amount, Order order, Product product) {
        this.amount = amount;
        this.order = order;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public StatusOrderItem getStatus() {
        return status;
    }

    public void setStatus(StatusOrderItem status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", receivedDate=" + receivedDate +
                ", amount=" + amount +
                ", status=" + status +
                ", order=" + order +
                ", product=" + product +
                '}';
    }
}
