package com.PCThanhCong.controllers;

import com.PCThanhCong.base.BaseController;
import com.PCThanhCong.constants.Common;
import com.PCThanhCong.dto.create.CreateCategoryDTO;
import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.dto.update.UpdateCategoryDTO;
import com.PCThanhCong.entity.Category;
import com.PCThanhCong.exceptions.NotFoundException;
import com.PCThanhCong.services.ICategoryService;
import com.PCThanhCong.specifications.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/categories")
@CrossOrigin("*")
public class CategoryController extends BaseController<Category> {
    @Autowired
    private ICategoryService categoryService;



    @GetMapping
    public ResponseEntity<?> getList(@RequestParam(name = "page",required = false) Integer page,
                                     @RequestParam(name = "perPage", required = false) Integer perPage,
                                     HttpServletRequest request){
        GenericSpecification<Category> specification = new GenericSpecification<Category>().getBasicQuery(request);
        PaginateDTO<Category> paginateCategories = categoryService.getList(page, perPage, specification);
        return this.resPagination(paginateCategories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getById(@PathVariable(name = "categoryId") Integer categoryId,
                                     HttpServletRequest request) {
        Category category = categoryService.getCategoryById(categoryId);
        if(category == null)
            throw new NotFoundException(Common.MSG_NOT_FOUND);
        return this.resSuccess(category);
    }

    @PatchMapping("/{categoryId}")
//    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> update(@RequestBody @Valid UpdateCategoryDTO categoryDTO,
                                    @PathVariable(name = "categoryId") Integer categoryId) throws Exception {
        Category category = categoryService.getCategoryById(categoryId);
        categoryService.update(categoryDTO , category);
        if(category == null)
            throw new NotFoundException(Common.MSG_NOT_FOUND);
        return this.resSuccess(category);
    }

    @PostMapping
//    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> create(@RequestBody @Valid CreateCategoryDTO categoryDTO) throws Exception {
        categoryService.create(categoryDTO);
        return new ResponseEntity<>(Common.MSG_CREATED_SUCCESSFUL_201, HttpStatus.CREATED);
    }


    @DeleteMapping("/{categoryId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> delete(@PathVariable(name = "categoryId") Integer categoryId) throws Exception {
        categoryService.deleteById(categoryId);
        return new ResponseEntity<>(Common.MSG_DELETE_SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/lockCategory/{categoryId}")
//    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> lockCategory(@PathVariable(name = "categoryId") Integer categoryId) throws Exception {
        categoryService.lockCategory(categoryId);
        return new ResponseEntity<>("lock this category success", HttpStatus.OK);
    }

    @PutMapping("/unLockCategory/{categoryId}")
//    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> unLockCategory(@PathVariable(name = "categoryId") Integer categoryId) throws Exception {
        categoryService.unLockCategory(categoryId);
        return new ResponseEntity<>("unlock this category success", HttpStatus.OK);
    }


    @GetMapping("/existed/{name}")
    public ResponseEntity<?> isExistByName(@PathVariable("name") String name){
        return new ResponseEntity<>(categoryService.existedByName(name), HttpStatus.CREATED);
    }

}
