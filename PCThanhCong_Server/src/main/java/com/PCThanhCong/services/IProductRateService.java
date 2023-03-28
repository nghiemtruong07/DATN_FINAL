package com.PCThanhCong.services;

import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.entity.ProductRate;
import com.PCThanhCong.forms.CreateProductRateForm;
import com.PCThanhCong.specifications.GenericSpecification;

public interface IProductRateService {
    ProductRate findOne(GenericSpecification<ProductRate> specification);

    ProductRate create(CreateProductRateForm createProductRateForm);

    PaginateDTO<ProductRate> getList(Integer page, Integer perPage, GenericSpecification<ProductRate> specification);
}
