package com.taoke.taokestudent.entity;

import java.util.List;

/**
 * 在线做题章节
 */
public class OnlineExerciseChapter {
    private String id;
    private String fid;
    private String name;
    private String level;
    private List<OnlineExerciseChapter> childList;
    /* 标识是否被选中 */
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<OnlineExerciseChapter> getChildList() {
        return childList;
    }

    public void setChildList(List<OnlineExerciseChapter> childList) {
        this.childList = childList;
    }
}
