package com.taoke.taokestudent.entity.net;

/**
 * 注册的响应
 */
public class RegisterResponse extends BaseResponse {
    private String data;

    public RegisterResponse() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
