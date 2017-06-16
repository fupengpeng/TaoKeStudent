package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.Order;

/**
 * 充值-生成支付订单的响应
 */
public class CreateOrderResponse extends BaseResponse {
    /* 支付订单 */
    private Order data;

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }
}
