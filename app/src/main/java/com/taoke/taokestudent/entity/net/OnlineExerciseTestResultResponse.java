package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.OnlineExerciseTestResultResponseData;

/**
 * 在线做题答题结果的响应
 */
public class OnlineExerciseTestResultResponse extends BaseResponse {
    private OnlineExerciseTestResultResponseData data;

    public OnlineExerciseTestResultResponseData getData() {
        return data;
    }

    public void setData(OnlineExerciseTestResultResponseData data) {
        this.data = data;
    }
}
