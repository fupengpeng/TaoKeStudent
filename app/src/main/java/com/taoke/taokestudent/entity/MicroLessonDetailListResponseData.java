package com.taoke.taokestudent.entity;

import java.util.List;

/**
 * 微课视频详情中的课程列表响应数据体
 */
public class MicroLessonDetailListResponseData {
    /* 总页数 */
    private String totalPage;
    private List<MicroLessonDetailListItem> list;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<MicroLessonDetailListItem> getList() {
        return list;
    }

    public void setList(List<MicroLessonDetailListItem> list) {
        this.list = list;
    }
}
