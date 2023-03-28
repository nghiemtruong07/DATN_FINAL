package com.PCThanhCong.services;

import com.PCThanhCong.entity.Order;

public interface IOrderService {

    Order getOrderByUserId(Integer userId);

    Order updateOrderAmount(Integer amount , Order order);



}
