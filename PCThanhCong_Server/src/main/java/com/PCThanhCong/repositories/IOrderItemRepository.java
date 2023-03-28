package com.PCThanhCong.repositories;

import com.PCThanhCong.constants.StatusOrderItem;
import com.PCThanhCong.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderItemRepository extends JpaRepository<OrderItem,Integer> {

    @Query("select u from OrderItem u where u.status = ?1")
    List<OrderItem> findOrderItemsByStatusIs(StatusOrderItem statusOrderItem);

    @Query("select u from OrderItem u where u.status = 'Complete' and MONTH(u.createdDate) = ?1")
    List<OrderItem> findOrderItemsByReceivedDateAndStatus(int month);

    @Query("select u from OrderItem u where u.status = 'Complete' and YEAR (u.receivedDate) = ?1")
    List<OrderItem> findOrderItemsByReceivedDateAfterAndStatus(int year);

    OrderItem findOrderItemById(Integer id);

    void deleteById(Integer id);

}
