package com.taoke.taokestudent.entity;

/**
 * 支付订单
 */
public class Order {
    /* 订单ID */
    private String orderId;
    /* 请求字符串 */
    private String signString;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSignString() {
        return signString;
    }

    public void setSignString(String signString) {
        this.signString = signString;
    }
}
