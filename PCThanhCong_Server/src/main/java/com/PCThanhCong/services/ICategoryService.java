package com.PCThanhCong.services;

import com.PCThanhCong.dto.create.CreateCategoryDTO;
import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.dto.update.UpdateCategoryDTO;
import com.PCThanhCong.entity.Category;
import com.PCThanhCong.specifications.GenericSpecification;

public interface ICategoryService {
    PaginateDTO<Category> getList(Integer page, Integer perPage, GenericSpecification<Category> specification);
    Category getCategoryById(Integer id);
    Category create(CreateCategoryDTO categoryDTO) throws Exception;

    Category update(UpdateCategoryDTO categoryDTO, Category currentCategory) throws Exception;

    void deleteById(Integer categoryId) throws Exception;

    void lockCategory(Integer id);

    void unLockCategory(Integer id);

    boolean existedByName(String name);
}
