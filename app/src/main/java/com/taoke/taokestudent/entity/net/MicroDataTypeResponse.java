package com.taoke.taokestudent.entity.net;

import com.taoke.taokestudent.entity.MicroDataTypeItem;

import java.util.List;

/**
 * 微课视频资源分类的响应
 */
public class MicroDataTypeResponse extends BaseResponse {
    private List<MicroDataTypeItem> data;

    public List<MicroDataTypeItem> getData() {
        return data;
    }

    public void setData(List<MicroDataTypeItem> data) {
        this.data = data;
    }
}
