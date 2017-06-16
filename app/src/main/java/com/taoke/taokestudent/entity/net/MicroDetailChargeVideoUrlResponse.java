package com.taoke.taokestudent.entity.net;

import java.util.List;

/**
 * 微课视频详情中的收费视频的播放地址的响应
 */
public class MicroDetailChargeVideoUrlResponse extends BaseResponse {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
