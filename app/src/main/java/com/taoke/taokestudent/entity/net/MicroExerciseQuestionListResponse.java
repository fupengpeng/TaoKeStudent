package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.MicroExerciseQuestionListResponseData;

/**
 * 习题微课习题列表的响应
 */
public class MicroExerciseQuestionListResponse extends BaseResponse {
    private MicroExerciseQuestionListResponseData data;

    public MicroExerciseQuestionListResponseData getData() {
        return data;
    }

    public void setData(MicroExerciseQuestionListResponseData data) {
        this.data = data;
    }
}
