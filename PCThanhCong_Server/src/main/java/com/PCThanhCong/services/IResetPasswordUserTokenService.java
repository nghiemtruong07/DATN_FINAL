package com.PCThanhCong.services;

import com.PCThanhCong.entity.ResetPasswordUserToken;

public interface IResetPasswordUserTokenService {
    void deleteResetPasswordTokenByUserId(Integer userId);

    void createResetPasswordUserToken(ResetPasswordUserToken resetPasswordUserToken);

    void deleteById(Integer id);
}
