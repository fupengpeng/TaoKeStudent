package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.OnlineExerciseBaseClassItem;

import java.util.List;

/**
 * 在线做题基础分类的响应
 */
public class OnlineExerciseBaseClassResponse extends BaseResponse {
    private List<OnlineExerciseBaseClassItem> data;

    public List<OnlineExerciseBaseClassItem> getData() {
        return data;
    }

    public void setData(List<OnlineExerciseBaseClassItem> data) {
        this.data = data;
    }
}
