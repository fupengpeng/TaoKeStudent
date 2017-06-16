package com.taoke.taokestudent.entity.net;

/**
 * 更新用户信息响应
 */
public class UpdateUserInfoResponse extends BaseResponse{
    private String data;

    public UpdateUserInfoResponse() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
