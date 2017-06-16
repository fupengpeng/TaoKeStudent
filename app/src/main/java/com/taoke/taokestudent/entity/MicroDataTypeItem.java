package com.taoke.taokestudent.entity;

import com.orm.SugarRecord;

/**
 * 微课视频资源分类项
 */
public class MicroDataTypeItem extends SugarRecord {
    /* 类型 */
    private String type;
    /* 类型名 */
    private String name;

    public MicroDataTypeItem() {
    }

    public MicroDataTypeItem(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
