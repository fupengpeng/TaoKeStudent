package com.taoke.taokestudent.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Column;

/**
 * 微课视频下载记录
 */
public class MicroDownloadRecord extends SugarRecord {
    /* 视频id */
    @Column(name = "videoId", unique = true)
    private String videoId;
    /* 视频名 */
    private String videoName;
    /* 下载状态 */
    private String type;
    /* 下载时间 */
    private long time;
    /* 课程名 */
    private String parentName;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
