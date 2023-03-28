package com.PCThanhCong.services.Impl;

import com.PCThanhCong.base.BasePagination;
import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.entity.Product;
import com.PCThanhCong.entity.ProductRate;
import com.PCThanhCong.entity.User;
import com.PCThanhCong.forms.CreateProductRateForm;
import com.PCThanhCong.repositories.IProductRateRepository;
import com.PCThanhCong.repositories.IProductRepository;
import com.PCThanhCong.repositories.IUserRepository;
import com.PCThanhCong.services.IProductRateService;
import com.PCThanhCong.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRateService extends BasePagination<ProductRate, IProductRateRepository> implements IProductRateService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IProductRateRepository productRateRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    public ProductRateService(IProductRateRepository productRateRepository){
        super(productRateRepository);
    }
    @Override
    public ProductRate findOne(GenericSpecification<ProductRate> specification) {
        return productRateRepository.findOne(specification).orElse(null);
    }

    @Override
    public ProductRate create(CreateProductRateForm createProductRateForm) {
        Product product = productRepository.findById(createProductRateForm.getProductId()).orElse(null);
        User user = userRepository.findById(createProductRateForm.getUserId()).orElse(null);

        ProductRate review = modelMapper.map(createProductRateForm, ProductRate.class);
        review.setProduct(product);
        review.setUser(user);

        return productRateRepository.save(review);
    }

    @Override
    public PaginateDTO<ProductRate> getList(Integer page, Integer perPage, GenericSpecification<ProductRate> specification) {
        return this.paginate(page, perPage, specification);
    }
}
