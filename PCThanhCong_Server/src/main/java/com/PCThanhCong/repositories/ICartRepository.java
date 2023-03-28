package com.PCThanhCong.repositories;

import com.PCThanhCong.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart, Integer> {

    Cart findCartByUserId(Integer id);



}
