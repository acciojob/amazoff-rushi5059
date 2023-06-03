package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

@Repository
public class OrderRepository {
    //      orderId,dpartnerId
    HashMap<String, String> idb = new HashMap<>();
    //      orderId, Object4 OrderDetails
    HashMap<String,Order> orderdb = new HashMap<>();
    //      dpartnerId, Oject4 Deliverydetails
    HashMap<String, DeliveryPartner> partnerdb = new HashMap<>();
    //      dpartnerId, List of Order
    HashMap<String, List<String>> dpolist = new HashMap<>();

    // in db add me return nhi hoga
    public void addOrder(Order order){
        orderdb.put(order.getId(),order); // store orderId followed by its relevant data
    }
    public void addPartner(String partnerId) {
        DeliveryPartner dp = new DeliveryPartner();
        partnerdb.put(partnerId,dp);  // store partnerId followed by its relevant data
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        // check order exist or not -order is theere in godown and partner also
        // check order is assigned or not
        if (orderdb.containsKey(orderId) && partnerdb.containsKey(partnerId));
        List<String> list = dpolist.getOrDefault(partnerId, new ArrayList<>());
        list.add(orderId);
        dpolist.put(partnerId, list);
        idb.put(orderId, partnerId);
        // increase the no of orders of  partner
        // DeliveryPartner deliveryPartner =  partnerDb
    }

    public Order getOrderByID(String orderId) {
        for(String s : orderdb.keySet()){
            if(s.equals(orderId)){
                return orderdb.get(s);
            }
        }
        return null;
        // or 1 liner
        // return orderdb.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        if(partnerdb.containsKey(partnerId)){
            return partnerdb.get(partnerId);
        }
        return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        int orders = dpolist.getOrDefault(partnerId, new ArrayList<>()).size();
        return orders;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> orders = dpolist.getOrDefault(partnerId,new ArrayList<>());
        return orders;
        // i liner - return dpolist.get(partnerId).size();
    }

    public List<String> getAllOrders(List<String> orders) {
        List<String> allorder = new ArrayList<>();
        for(String sb : orderdb.keySet()){
            allorder.add(sb);
        }
        return allorder;
    }

    public Integer getCountOfUnassignedOrders() {
        // total order - assigned order
        int countOrders = orderdb.size()-idb.size();
        return countOrders;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {

        String [] sep = time.split(":");
        int deliveryTime = (Integer.parseInt(sep[0])*60)+ (Integer.parseInt(sep[1]));

        int ordercount =0;
        List<String> l = dpolist.get(partnerId);    // get the orderlist partnerId have

        for (String s : l) {        // iterate over the list
            Order order = orderdb.get(s);
            if (order.getDeliveryTime() > deliveryTime) {
                ordercount++;
            }
        }
        return ordercount;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        //return in HH:MM format
        String str="00:00";
        int max=0;

        List<String>list=  dpolist.getOrDefault(partnerId,new ArrayList<>());
        if(list.size()==0) return str;
        for(String s: list){
            Order currentOrder = orderdb.get(s);
            max=Math.max(max,currentOrder.getDeliveryTime());
        }
        //convert int to string (140-> 02:20)
        /* 1 liner -
        String HH = String.valueOf(max/60);
        String MM = String.valueOf(max%60);

        */
        int hr=max/60;
        int min=max%60;

        // to check the time is less than
        if(hr<10){
            str="0"+hr+":";
        }else{
            str=hr+":";
        }

        if(min<10){
            str+="0"+min;
        }
        else{ str+=min;
        }
        return str;

    }

    public String deletePartnerId(String partnerId) {
        partnerdb.remove(partnerId);

        List<String> listofOrder = dpolist.getOrDefault(partnerId, new ArrayList<>());
        ListIterator<String> itr = listofOrder.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            idb.remove(s);
        }
        dpolist.remove(partnerId);
        return "Deleted";
    }

    public String deleteOrderById(String orderId) {
        //Delete an order and the corresponding partner should be unassigned
        orderdb.remove(orderId);
        String partnerId = idb.get(orderId);
        dpolist.remove(orderId);
        List<String> list = dpolist.get(partnerId);

        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            if (s.equals(orderId)) {
                itr.remove();
            }
        }
        dpolist.put(partnerId, list);
        return "Deleted";
    }
}