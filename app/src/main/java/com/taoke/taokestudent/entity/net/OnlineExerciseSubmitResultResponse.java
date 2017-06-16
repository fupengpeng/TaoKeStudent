package com.taoke.taokestudent.entity.net;

/**
 * 在线做题上传答题结果的响应
 */
public class OnlineExerciseSubmitResultResponse extends BaseResponse {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
