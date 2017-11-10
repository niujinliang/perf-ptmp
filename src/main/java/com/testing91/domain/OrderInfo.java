package com.testing91.domain;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/4/9 0009.
 */
public class OrderInfo {
    private int id;
    private String orderName;
    private String orderDesc;
    private String orderStatus;
    public OrderInfo(){}
    public OrderInfo(String oderName, String oderDesc, String orderStatus, Timestamp applyTime) {
        this.orderName = oderName;
        this.orderDesc = oderDesc;
        this.orderStatus = orderStatus;
        this.applyTime = applyTime;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", applyTime=" + applyTime +
                '}';
    }

    private Timestamp applyTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }
}
