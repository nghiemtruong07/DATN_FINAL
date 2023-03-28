package com.PCThanhCong.services.Impl;

import com.PCThanhCong.base.BasePagination;
import com.PCThanhCong.constants.StatusCodeEnum;
import com.PCThanhCong.dto.create.CreateCategoryDTO;
import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.dto.update.UpdateCategoryDTO;
import com.PCThanhCong.entity.Category;
import com.PCThanhCong.entity.Product;
import com.PCThanhCong.repositories.ICategoryRepository;
import com.PCThanhCong.services.ICategoryService;
import com.PCThanhCong.services.IProductService;
import com.PCThanhCong.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BasePagination<Category, ICategoryRepository> implements ICategoryService {
    @Autowired
    private ICategoryRepository repository;

    @Autowired
    private IProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CategoryService(ICategoryRepository repository){
        super(repository);
    }
    @Override
    public PaginateDTO<Category> getList(Integer page, Integer perPage, GenericSpecification<Category> specification) {
        return this.paginate(page, perPage, specification);
    }

    @Override
    public Category getCategoryById(Integer id) {
        return repository.findCategoryById(id);
    }

    @Override
    public Category create(CreateCategoryDTO categoryDTO) throws Exception {
        Category oldCategory = repository.findByName(categoryDTO.getName());
        if(oldCategory != null)
            throw new Exception("Category has already exists !");
        Category newCategory = modelMapper.map(categoryDTO, Category.class);
        return repository.save(newCategory);
    }

    @Override
    public Category update(UpdateCategoryDTO categoryDTO, Category currentCategory) throws Exception {
        Category updated = modelMapper.map(categoryDTO, Category.class);
        if(repository.findByName(categoryDTO.getName()) != null)
            throw new Exception("Category has already exists !");
        modelMapper.map(updated, currentCategory);
        return repository.save(currentCategory);
    }

    @Override
    public void deleteById(Integer categoryId) throws Exception {
        Category category = repository.findCategoryById(categoryId);
        if(category == null)
            throw new Exception("Not found category");
        if(!category.getProducts().isEmpty())
            throw new Exception("Cannot delete category");
        repository.delete(category);
    }

    @Override
    public void lockCategory(Integer id) {
        Category category = repository.findCategoryById(id);
        category.setStatus(StatusCodeEnum.NOT_ACTIVE);
        for (Product product: category.getProducts()) {
            productService.lockProductStatus(product.getId());
        }
        repository.save(category);
    }

    @Override
    public void unLockCategory(Integer id) {
        Category category = repository.findCategoryById(id);
        category.setStatus(StatusCodeEnum.ACTIVE);
        for (Product product: category.getProducts()) {
            productService.unLockProductStatus(product.getId());
        }
        repository.save(category);
    }

    @Override
    public boolean existedByName(String name) {
        return repository.existsByName(name);
    }
}
