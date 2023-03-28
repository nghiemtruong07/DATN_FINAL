package com.PCThanhCong.repositories;

import com.PCThanhCong.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order,Integer> {

    Order findOrderByUserId(Integer id);



}
