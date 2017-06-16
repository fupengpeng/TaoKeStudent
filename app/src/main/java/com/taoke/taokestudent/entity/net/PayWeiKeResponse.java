package com.taoke.taokestudent.entity.net;

/**
 * 登录的响应
 */
public class PayWeiKeResponse extends BaseResponse {
    private String data;

    public PayWeiKeResponse() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
