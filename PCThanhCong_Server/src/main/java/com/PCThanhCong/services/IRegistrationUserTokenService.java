package com.PCThanhCong.services;

import com.PCThanhCong.entity.User;

public interface IRegistrationUserTokenService {
    void createNewRegistrationUserToken(User user, final String jwt);
}
