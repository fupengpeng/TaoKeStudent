package com.taoke.taokestudent.entity;

/**
 * 习题微课习题
 */
public class ExerciseClass {
    /* 习题id */
    private String id;
    /* 习题父id */
    private String fid;
    /* 习题名 */
    private String name;
    /* 习题级别 */
    private String level;
    /* 模块id */
    private String mokuai_id;
    // 是否免费 1:是 2：否
    private String is_free;

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
}
