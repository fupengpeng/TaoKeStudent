package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.MicroLessonDetailListResponseData;

/**
 * 加载微课视频详情中的免费课程列表的响应
 */
public class MicroLessonDetailListResponse extends BaseResponse {
    private MicroLessonDetailListResponseData data;

    public MicroLessonDetailListResponseData getData() {
        return data;
    }

    public void setData(MicroLessonDetailListResponseData data) {
        this.data = data;
    }
}
