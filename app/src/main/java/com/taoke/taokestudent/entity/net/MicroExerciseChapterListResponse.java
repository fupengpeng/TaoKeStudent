package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.ExerciseChapter;

import java.util.List;

/**
 * 习题微课章节列表的响应
 */
public class MicroExerciseChapterListResponse extends BaseResponse {
    private List<ExerciseChapter> data;

    public List<ExerciseChapter> getData() {
        return data;
    }

    public void setData(List<ExerciseChapter> data) {
        this.data = data;
    }
}
