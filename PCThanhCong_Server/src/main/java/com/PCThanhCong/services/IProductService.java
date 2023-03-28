package com.PCThanhCong.services;

import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.entity.Product;
import com.PCThanhCong.forms.CreateProductForm;
import com.PCThanhCong.forms.UpdateProductForm;
import com.PCThanhCong.specifications.GenericSpecification;

public interface IProductService{


    public PaginateDTO<Product> getAllProducts(Integer page, Integer perPage, GenericSpecification<Product> specification);

    public Product getProductById(Integer id);

    public Product getProductByTitle(String title);

    Product createProduct(CreateProductForm form);

    void updateProduct(Integer id, UpdateProductForm form);

    void updateProductAmount(Product product , Integer amount);

    void unLockProductStatus(Integer id);

    void lockProductStatus(Integer id);

    boolean existsProductByTitle(String title);

    long getProductCount();
}
