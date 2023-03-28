package com.PCThanhCong.services.Impl;

import com.PCThanhCong.entity.RegistrationUserToken;
import com.PCThanhCong.entity.User;
import com.PCThanhCong.repositories.IRegistrationUserTokenRepository;
import com.PCThanhCong.services.IRegistrationUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationUserTokenService implements IRegistrationUserTokenService {
    @Autowired
    private IRegistrationUserTokenRepository registrationUserTokenRepository;

    @Override
    public void createNewRegistrationUserToken(User user, final String jwt) {

        RegistrationUserToken tokenEntity = new RegistrationUserToken(user, jwt);
        registrationUserTokenRepository.save(tokenEntity);
    }
}
