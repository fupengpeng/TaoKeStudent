package com.taoke.taokestudent.entity;

import com.orm.SugarRecord;

/**
 * 在线做题基础分类项
 */
public class OnlineExerciseBaseClassItem extends SugarRecord {
    /* 父ID */
    private String fid;
    /* 名称 */
    private String name;

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
}
