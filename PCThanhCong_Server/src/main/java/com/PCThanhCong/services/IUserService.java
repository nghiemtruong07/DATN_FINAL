package com.PCThanhCong.services;

import com.PCThanhCong.dto.SignUpDTO;
import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.dto.update.UpdateUserDTO;
import com.PCThanhCong.entity.User;
import com.PCThanhCong.specifications.GenericSpecification;

public interface IUserService  {
    PaginateDTO<User> getList(Integer page, Integer perPage, GenericSpecification<User> specification);

    User findByEmail(String email);

    User findByUsername(String username);

    User create(SignUpDTO signUpDTO);

    void activeUser(String token);

    User updateUser(User user);

    void create(User user);

    User findById(Integer userId);

    User update(UpdateUserDTO updateUserDTO, User currentUser);

    void deleteById(Integer userId);
}
