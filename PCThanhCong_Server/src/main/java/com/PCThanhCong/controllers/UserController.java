package com.PCThanhCong.controllers;

import com.PCThanhCong.base.BaseController;
import com.PCThanhCong.constants.Common;
import com.PCThanhCong.dto.UserChangePasswordDTO;
import com.PCThanhCong.dto.pagination.PaginateDTO;
import com.PCThanhCong.dto.update.UpdateUserDTO;
import com.PCThanhCong.entity.User;
import com.PCThanhCong.exceptions.AppException;
import com.PCThanhCong.exceptions.NotFoundException;
import com.PCThanhCong.services.IUserService;
import com.PCThanhCong.specifications.GenericSpecification;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "Authorization")
@CrossOrigin("*")
public class UserController extends BaseController<User> {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> getAllUser(@RequestParam(name = "page", required = false) Integer page,
                                        @RequestParam(name = "perPage", required = false) Integer perPage,
                                        HttpServletRequest request){
        GenericSpecification<User> specification = new GenericSpecification<User>().getBasicQuery(request);
        PaginateDTO<User> paginateUsers = userService.getList(page, perPage, specification);
        return this.resPagination(paginateUsers);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication) || @userAuthorizer.isYourself(authentication, #userId)")
    public ResponseEntity<?> getUserById(@PathVariable(name = "userId") Integer userId){
        User user = userService.findById(userId);
        if(user == null)
            throw new NotFoundException(Common.MSG_NOT_FOUND);
        return this.resSuccess(user);
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication) || @userAuthorizer.isYourself(authentication, #userId)")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateUserDTO userDTO,
                                        @PathVariable("userId") Integer userId){
        User user = userService.findById(userId);
        if(user == null)
            throw new NotFoundException(Common.MSG_NOT_FOUND);

        User savedUser = userService.update(userDTO, user);
        return this.resSuccess(savedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
        User user = userService.findById(userId);
        if (user == null) {
            throw new NotFoundException(Common.MSG_NOT_FOUND);
        }

        if (user.getOrder().getOrderItems().size() != 0) {
            throw new AppException(Common.MSG_DELETE_FAIL);
        }

        userService.deleteById(userId);

        return new ResponseEntity<>(Common.MSG_DELETE_SUCCESS, HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid UserChangePasswordDTO userChangePasswordDTO, HttpServletRequest request) {
        User requestedUser = (User) request.getAttribute("user");

        User user = userService.findByUsername(requestedUser.getUsername());

        if (!passwordEncoder.matches(userChangePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new AppException(Common.MSG_OLD_PASSWORD_INVALID);
        }

        user.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));

        User updatedUser = userService.updateUser(user);

        return this.resSuccess(updatedUser);
    }
}
