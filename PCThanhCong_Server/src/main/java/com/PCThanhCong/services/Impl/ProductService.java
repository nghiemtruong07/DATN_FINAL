package com.PCThanhCong.services.Impl;

import com.PCThanhCong.base.BasePagination;
import com.PCThanhCong.constants.StatusCodeEnum;
import com.PCThanhCong.constants.StatusCodeProductEnum;
import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.entity.Product;
import com.PCThanhCong.forms.CreateProductForm;
import com.PCThanhCong.forms.UpdateProductForm;
import com.PCThanhCong.repositories.IProductRepository;
import com.PCThanhCong.services.ICategoryService;
import com.PCThanhCong.services.IProductImageService;
import com.PCThanhCong.services.IProductService;
import com.PCThanhCong.specifications.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BasePagination<Product, IProductRepository> implements IProductService {


    @Autowired
    private IProductRepository repository;

    @Autowired
    private IProductImageService productImageService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    public ProductService(IProductRepository iProductRepository){
        super(iProductRepository);
    }


    @Override
    public PaginateDTO<Product> getAllProducts(Integer page, Integer perPage, GenericSpecification<Product> specification) {


        return this.paginate(page, perPage, specification);
    }

    @Override
    public Product getProductById(Integer id) {
        return repository.findProductById(id);
    }

    @Override
    public Product getProductByTitle(String title) {
        return repository.findProductByTitle(title);
    }

    @Override
    public Product createProduct(CreateProductForm form) {
        Product product = form.toEntity();
        product.setCategory(categoryService.getCategoryById(form.getCategoryId()));
        return repository.save(product);
    }

    @Override
    public void updateProduct(Integer id , UpdateProductForm form) {
            Product product = form.toEntity();
            product.setId(id);
            product.setCategory(categoryService.getCategoryById(repository.findProductById(id).getCategory().getId()));
            product.setCreatedDate(repository.findProductById(id).getCreatedDate());
            repository.save(product);
//            product.setProductImages(productImageService.createProductImages(form.getProductImages(), product));

    }

    @Override
    public void updateProductAmount(Product product, Integer amount) {
        product.setAmount(amount);
        repository.save(product);
    }


    @Override
    public void unLockProductStatus(Integer id) {
        Product product = repository.findProductById(id);
        if(product.getStatus() == StatusCodeProductEnum.CLOSED)
            product.setStatus(StatusCodeProductEnum.OPENING);
        repository.save(product);
        if(product.getCategory().getStatus() == StatusCodeEnum.NOT_ACTIVE){
            categoryService.unLockCategory(product.getCategory().getId());
            for (Product pro: product.getCategory().getProducts()) {
                if (pro.getId() != product.getId()) {
                    pro.setStatus(StatusCodeProductEnum.CLOSED);
                    repository.save(product);
                }
            }
        }

    }

    @Override
    public void lockProductStatus(Integer id) {
        Product product = repository.findProductById(id);
        if(product.getStatus() == StatusCodeProductEnum.OPENING)
            product.setStatus(StatusCodeProductEnum.CLOSED);
        repository.save(product);


    }

    @Override
    public boolean existsProductByTitle(String title) {
        return repository.existsProductByTitle(title);
    }

    @Override
    public long getProductCount() {
        return repository.count();
    }


}
