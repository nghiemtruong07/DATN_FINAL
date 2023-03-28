package com.PCThanhCong.services;

import com.PCThanhCong.entity.Product;
import com.PCThanhCong.entity.ProductImage;
import com.PCThanhCong.forms.CreateProductImageForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IProductImageService {

    List<ProductImage> createProductImages(List<CreateProductImageForm> images , Product productId);

    void deleteByProductId(Integer productId);

    List<ProductImage> createOrUpdateMany(Product product, MultipartFile[] files);
}
