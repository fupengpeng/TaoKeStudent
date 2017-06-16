package com.taoke.taokestudent.entity;

/**
 * 习题微课习题列表项
 */
public class MicroExerciseQuestionListItem {
    /* id */
    private String id;
    /* 题干 */
    private String topic;
    /* 答案 */
    private String answer;
    /* 解析 */
    private String resolve;
    /* 视频地址 */
    private String viewUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getResolve() {
        return resolve;
    }

    public void setResolve(String resolve) {
        this.resolve = resolve;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }
}
