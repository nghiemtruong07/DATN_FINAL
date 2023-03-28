package com.PCThanhCong.repositories;

import com.PCThanhCong.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductImagesRepository extends JpaRepository<ProductImage , Integer> {
    ProductImage findByImageUrl(String imageUrl);

    void deleteByProductId(Integer productId);
}
