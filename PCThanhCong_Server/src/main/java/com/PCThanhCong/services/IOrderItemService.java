package com.PCThanhCong.services;

import com.PCThanhCong.constants.StatusOrderItem;
import com.PCThanhCong.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface IOrderItemService {

    List<OrderItem> getAllOrderItemsByStatus(StatusOrderItem status);

    OrderItem getOrderItemById(Integer id);

    Integer getMonthlyRevenue(int month);

    void createOrderItems(OrderItem orderItem);

    void updateOrderItemStatus(Integer id , StatusOrderItem status);

    void deleteById(Integer id);

    Map<Integer, Integer> getReportByYear(Integer year);

    String getBase64ReportByYear(Integer year) throws Exception;

}
