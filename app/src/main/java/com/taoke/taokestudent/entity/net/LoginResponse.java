package com.taoke.taokestudent.entity.net;

/**
 * 登录的响应
 */
public class LoginResponse extends BaseResponse {
    private String data;

    public LoginResponse() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
