package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.MicorLessonVideoBaseClassItem;

import java.util.List;

/**
 * 获取微课视频基础分类的响应
 */
public class MicroLessonVideoBaseClassResponse extends BaseResponse {
    private List<MicorLessonVideoBaseClassItem> data;

    public List<MicorLessonVideoBaseClassItem> getData() {
        return data;
    }

    public void setData(List<MicorLessonVideoBaseClassItem> data) {
        this.data = data;
    }
}
