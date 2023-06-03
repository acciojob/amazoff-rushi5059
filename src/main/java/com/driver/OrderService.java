package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository Orderepo;


    public void addOrder(Order order) {
        Orderepo.addOrder(order);
    }

    public void addPartner(String partnerId) {
        Orderepo.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        Orderepo.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return Orderepo.getOrderByID(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return Orderepo.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return Orderepo.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return Orderepo.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders(List<String> orders) {
        return Orderepo.getAllOrders(orders);
    }

    public Integer getCountOfUnassignedOrders() {
        return Orderepo.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        // should convert time here  string to int
        return Orderepo.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        // return int to string which is done in repo only
        return Orderepo.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerId(String partnerId) {
        Orderepo.deletePartnerId(partnerId);
    }

    public void deleteOrderById(String orderId) {
        Orderepo.deleteOrderById(orderId);
    }
}