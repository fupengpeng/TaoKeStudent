package com.taoke.taokestudent.entity;

import java.util.List;

/**
 * 习题微课习题列表响应数据体
 */
public class MicroExerciseQuestionListResponseData {
    /* 总页数 */
    private String totalPage;
    private List<MicroExerciseQuestionListItem> list;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<MicroExerciseQuestionListItem> getList() {
        return list;
    }

    public void setList(List<MicroExerciseQuestionListItem> list) {
        this.list = list;
    }
}
