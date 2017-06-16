package com.taoke.taokestudent.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Column;

/**
 * 微课视频下载的视频URL
 */
public class MicroDownloadVideoUrl extends SugarRecord {
    /* 视频id */
    @Column(name = "videoId", unique = true)
    private String videoId;
    /* 视频URL */
    private String videoURL;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
