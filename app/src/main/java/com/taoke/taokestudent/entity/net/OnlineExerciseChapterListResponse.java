package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.OnlineExerciseChapter;

import java.util.List;

/**
 * 在线做题获取章节列表的响应
 */
public class OnlineExerciseChapterListResponse extends BaseResponse {
    private List<OnlineExerciseChapter> data;

    public List<OnlineExerciseChapter> getData() {
        return data;
    }

    public void setData(List<OnlineExerciseChapter> data) {
        this.data = data;
    }
}
