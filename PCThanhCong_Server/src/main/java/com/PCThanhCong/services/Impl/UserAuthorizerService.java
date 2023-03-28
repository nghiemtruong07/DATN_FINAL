package com.PCThanhCong.services.Impl;

import com.PCThanhCong.constants.RoleEnum;
import com.PCThanhCong.services.IUserAuthorizerService;
import com.PCThanhCong.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service("userAuthorizer")
public class UserAuthorizerService implements IUserAuthorizerService {
    @Autowired
    private IUserService userService;

    @Override
    public boolean isAdmin(Authentication authentication) {
        return Arrays.toString(authentication.getAuthorities().toArray()).contains(RoleEnum.ADMIN);
    }

    @Override
    public boolean isClient(Authentication authentication) {
        return Arrays.toString(authentication.getAuthorities().toArray()).contains(RoleEnum.CLIENT);
    }

    @Override
    public boolean isYourself(Authentication authentication, Integer userId) {
        User userAuth = (User) authentication.getPrincipal();
        com.PCThanhCong.entity.User user = userService.findByUsername(userAuth.getUsername());
        if (!Objects.equals(user.getId(), userId)) {
            throw new AccessDeniedException("Access denied");
        }
        return true;
    }
}
