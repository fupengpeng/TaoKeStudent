package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.ConnectionAccount;

import java.util.List;

/**
 * 获取家长列表的响应
 */
public class LoadConnectionAccountResponse extends BaseResponse {
    private List<ConnectionAccount>  data;

    public List<ConnectionAccount> getData() {
        return data;
    }

    public void setData(List<ConnectionAccount> data) {
        this.data = data;
    }
}
