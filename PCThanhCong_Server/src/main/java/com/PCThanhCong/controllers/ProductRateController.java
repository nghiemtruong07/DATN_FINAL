package com.PCThanhCong.controllers;

import com.PCThanhCong.base.BaseController;
import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.entity.Product;
import com.PCThanhCong.entity.ProductRate;
import com.PCThanhCong.entity.User;
import com.PCThanhCong.exceptions.NotFoundException;
import com.PCThanhCong.filters.ProductRateFilter;
import com.PCThanhCong.forms.CreateProductRateForm;
import com.PCThanhCong.services.IProductRateService;
import com.PCThanhCong.services.IProductService;
import com.PCThanhCong.specifications.GenericSpecification;
import com.PCThanhCong.specifications.SearchCriteria;
import com.PCThanhCong.specifications.SearchOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/product-rates")
public class ProductRateController extends BaseController<ProductRate> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IProductRateService productRateService;

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<?> getListProductRatesByProductId(
            ProductRateFilter filter,
            HttpServletRequest request) {

        GenericSpecification<ProductRate> specification = new GenericSpecification<ProductRate>().getBasicQuery(request);
        specification.add(new SearchCriteria("product", filter.getProductId(), SearchOperation.EQUAL));

        PaginateDTO<ProductRate> paginateProductRates = productRateService.getList(filter.getPage(), filter.getPerPage(), specification);

        return this.resPagination(paginateProductRates);
    }

    @GetMapping("/detail")
    @PreAuthorize("@userAuthorizer.isClient(authentication)")
    public ResponseEntity<?> getProductRateByProductId(ProductRateFilter filter, HttpServletRequest request) {
        User requestedUser = (User) request.getAttribute("user");

        GenericSpecification<ProductRate> specification = new GenericSpecification<ProductRate>().getBasicQuery(request);
        specification.add(new SearchCriteria("product", filter.getProductId(), SearchOperation.EQUAL));
        specification.add(new SearchCriteria("user", requestedUser.getId(), SearchOperation.EQUAL));

        ProductRate productRate = productRateService.findOne(specification);

        return this.resSuccess(productRate);
    }

    @PostMapping
    @PreAuthorize("@userAuthorizer.isClient(authentication)")
    public ResponseEntity<?> createReview(@RequestBody @Valid CreateProductRateForm createProductRateForm, HttpServletRequest request) throws Exception {
        User requestUser = (User) request.getAttribute("user");

        Product  product = productService.getProductById(createProductRateForm.getProductId());
        if(product == null)
            throw new NotFoundException("Not found this product");

        GenericSpecification<ProductRate> specification = new GenericSpecification<ProductRate>().getBasicQuery(request);
        specification.add((new SearchCriteria("product", createProductRateForm.getProductId(), SearchOperation.EQUAL)));
        specification.add(new SearchCriteria("user", createProductRateForm.getUserId(), SearchOperation.EQUAL));

        ProductRate review = productRateService.findOne(specification);

        if(review != null)
            throw new Exception("You has already review");

        createProductRateForm.setUserId(requestUser.getId());

        ProductRate newReview = productRateService.create(createProductRateForm);

        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }
}
