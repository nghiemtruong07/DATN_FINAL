package com.PCThanhCong.services;

import com.PCThanhCong.entity.Cart;
import com.PCThanhCong.filters.AddCartParams;

public interface ICartService {
    Cart getCartByUserId(Integer id);

    void addCartItemToCart(AddCartParams params) throws Exception;

    void buyCartItem(Integer userId , Integer cartItemId);

    void buyListCartItems(Integer userId);

    Cart updateCartAmount(Integer amount , Cart cart);



}
