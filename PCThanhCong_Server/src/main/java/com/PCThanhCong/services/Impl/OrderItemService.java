package com.PCThanhCong.services.Impl;

import com.PCThanhCong.constants.StatusOrderItem;
import com.PCThanhCong.entity.OrderItem;
import com.PCThanhCong.repositories.IOrderItemRepository;
import com.PCThanhCong.services.IOrderItemService;
import com.PCThanhCong.utils.ExcelHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderItemService implements IOrderItemService {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Autowired
    private IOrderItemRepository repository;

    @Autowired
    private ExcelHandler excelHandler;


    @Override
    public List<OrderItem> getAllOrderItemsByStatus(StatusOrderItem status) {
        return repository.findOrderItemsByStatusIs(status);
    }

    @Override
    public OrderItem getOrderItemById(Integer id) {
        return repository.findOrderItemById(id);
    }

    @Override
    public Integer getMonthlyRevenue(int month) {
        List<OrderItem> orderItems = repository.findOrderItemsByReceivedDateAndStatus(month);
        Integer revenue = 0;
        for (OrderItem item : orderItems) {
            revenue += item.getProduct().getPromotionPrice() * item.getAmount();
        }
        return revenue;
    }

    @Override
    public void createOrderItems(OrderItem orderItem) {
        repository.save(orderItem);
    }

    @Override
    public void updateOrderItemStatus(Integer id, StatusOrderItem status) {
        OrderItem orderItem = repository.findOrderItemById(id);
        if (orderItem.getStatus() == StatusOrderItem.Processing && status == StatusOrderItem.Processed) {
            orderItem.setStatus(StatusOrderItem.Processed);
        } else if (orderItem.getStatus() == StatusOrderItem.Processed && status == StatusOrderItem.Delivering) {
            orderItem.setStatus(StatusOrderItem.Delivering);
            dateFormat.format(orderItem.getCreatedDate());
            Calendar c = Calendar.getInstance();
            c.setTime(orderItem.getCreatedDate());
            c.add(Calendar.DATE, 3);
            Date currentDatePlusOne = c.getTime();
            dateFormat.format(currentDatePlusOne);
            orderItem.setReceivedDate(currentDatePlusOne);
        } else if (orderItem.getStatus() == StatusOrderItem.Delivering && status == StatusOrderItem.Complete) {
            orderItem.setStatus(StatusOrderItem.Complete);
        }
        repository.save(orderItem);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Map<Integer, Integer> getReportByYear(Integer year) {
        List<OrderItem> orderItemList = repository.findOrderItemsByReceivedDateAfterAndStatus(year);
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> response = new HashMap<>();
        for (OrderItem item : orderItemList) {
            list.add(item.getReceivedDate().getMonth());
        }
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < list.size(); j++) {
                if(i == list.get(j)){
                    int fee = orderItemList.get(j).getAmount() * orderItemList.get(j).getProduct().getPromotionPrice();
                    response.put(i + 1, fee);
                }
            }
        }
        return response;
    }

    @Override
    public String getBase64ReportByYear(Integer year) throws Exception {
        if(year == null){
            throw new Exception("khong co du lieu");
        }
        List<OrderItem> orderItemList = repository.findOrderItemsByReceivedDateAfterAndStatus(year);
        if(orderItemList.size() == 0){
            throw new Exception("khong co du lieu");
        }
        String base64 = excelHandler.writeReportToExcel(orderItemList);
        return base64;
    }
}
