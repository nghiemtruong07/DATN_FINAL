package com.PCThanhCong.base;


import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.dto.pagination.PaginationDTO;
import com.PCThanhCong.specifications.GenericSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public class BasePagination<E, R extends JpaRepository<E, ?> & JpaSpecificationExecutor<E>> {
    private R repository;

    public BasePagination() {
    }

    public BasePagination(R repository) {
        this.repository = repository;
    }

    public PaginateDTO<E> paginate(Integer page, Integer perPage) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (perPage == null || perPage <= 0) {
            perPage = 10;
        }

        Page<E> pageData = this.repository.findAll(PageRequest.of(page, perPage, Sort.by("id").descending()));

        PaginationDTO.Pagination pagination = new PaginationDTO.Pagination(page, perPage, pageData.getTotalPages(), pageData.getTotalElements());
        return new PaginateDTO<>(pageData, pagination);
    }

    public PaginateDTO<E> paginate(Integer page, Integer perPage, GenericSpecification<E> specification) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (perPage == null || perPage <= 0) {
            perPage = 10;
        }

        Page<E> pageData = this.repository.findAll(specification,
                PageRequest.of(page - 1, perPage, specification.getSort()));
        PaginationDTO.Pagination pagination = new PaginationDTO.Pagination(page, perPage, pageData.getTotalPages(), pageData.getTotalElements());
        return new PaginateDTO<>(pageData, pagination);
    }
}
