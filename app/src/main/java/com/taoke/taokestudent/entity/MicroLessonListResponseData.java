package com.taoke.taokestudent.entity;

import java.util.List;

/**
 * 微课视频课程列表响应数据体
 */
public class MicroLessonListResponseData {
    /* 总页数 */
    private String totalPage;
    private List<MicroLessonVideoListItem> list;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<MicroLessonVideoListItem> getList() {
        return list;
    }

    public void setList(List<MicroLessonVideoListItem> list) {
        this.list = list;
    }
}
