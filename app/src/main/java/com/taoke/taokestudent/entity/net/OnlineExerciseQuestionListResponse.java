package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.OnlineExerciseQuestion;

import java.util.List;

/**
 * 在线做题试题列表的响应
 */
public class OnlineExerciseQuestionListResponse extends BaseResponse {
    private List<OnlineExerciseQuestion> data;

    public List<OnlineExerciseQuestion> getData() {
        return data;
    }

    public void setData(List<OnlineExerciseQuestion> data) {
        this.data = data;
    }
}
