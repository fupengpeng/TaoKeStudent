package com.taoke.taokestudent.entity;

import java.util.List;

/**
 * 习题微课章节
 */
public class ExerciseChapter {
    /* 章节id */
    private String id;
    /* 章节父id */
    private String fid;
    /* 章节名 */
    private String name;
    /* 章节级别 */
    private String level;
    /* 模块id */
    private String mokuai_id;
    // 是否免费 1:是 2：否
    private String is_free;
    /* 习题列表 */
    private List<ExerciseClass> childList;

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

    public String getMokuai_id() {
        return mokuai_id;
    }

    public void setMokuai_id(String mokuai_id) {
        this.mokuai_id = mokuai_id;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public List<ExerciseClass> getChildList() {
        return childList;
    }

    public void setChildList(List<ExerciseClass> childList) {
        this.childList = childList;
    }
}
