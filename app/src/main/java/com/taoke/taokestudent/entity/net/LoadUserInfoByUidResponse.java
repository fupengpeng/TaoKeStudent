package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.User;

/**
 * 根据UID获取用户信息的响应
 */
public class LoadUserInfoByUidResponse extends BaseResponse {
    private User data;

    public LoadUserInfoByUidResponse() {
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
