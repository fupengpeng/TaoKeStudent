package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.MicroLessonListResponseData;

/**
 * 加载微课视频课程列表的响应
 */
public class MicroLessonListResponse extends BaseResponse {
    private MicroLessonListResponseData data;

    public MicroLessonListResponseData getData() {
        return data;
    }

    public void setData(MicroLessonListResponseData data) {
        this.data = data;
    }
}
