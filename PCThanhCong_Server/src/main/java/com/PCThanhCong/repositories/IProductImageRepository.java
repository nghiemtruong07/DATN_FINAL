package com.PCThanhCong.repositories;

import com.PCThanhCong.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
