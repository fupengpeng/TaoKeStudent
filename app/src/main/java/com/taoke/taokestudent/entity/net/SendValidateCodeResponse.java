package com.taoke.taokestudent.entity.net;

/**
 * 发送验证码的响应
 */
public class SendValidateCodeResponse extends BaseResponse {
    private String data;

    public SendValidateCodeResponse() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
