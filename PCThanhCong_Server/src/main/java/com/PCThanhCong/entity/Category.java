package com.PCThanhCong.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.PCThanhCong.constants.StatusCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "categories")
public class Category implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`name`",length = 255,nullable = false)
    private String name;

    @Column(name = "`status`")
    private StatusCodeEnum status;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Product> products;

    @PrePersist
    public void PrePersist(){
        if(this.status == null)
            this.status = StatusCodeEnum.ACTIVE;
    }

}
