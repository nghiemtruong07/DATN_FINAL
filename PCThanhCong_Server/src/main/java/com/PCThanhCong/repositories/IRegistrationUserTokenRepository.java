package com.PCThanhCong.repositories;

import com.PCThanhCong.entity.RegistrationUserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRegistrationUserTokenRepository extends JpaRepository<RegistrationUserToken, Integer> {

    RegistrationUserToken findByToken(String token);
}
