package com.taoke.taokestudent.model;

import android.util.Log;

import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.util.LogUtils;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

/**
 * 下载业务
 */
public class DownloadModel {
    /* 下载队列 */
    private static DownloadQueue downloadQueue = NoHttp.newDownloadQueue(3);

    public DownloadModel() {
    }

    /**
     * 下载微课视频
     */
    public void downloadMicroVideo(String url, String dirPath, String fileName, int what) {
        File file = new File(dirPath);
        if(!file.exists()){
            boolean b = file.mkdirs();
            Log.i("jdlx", dirPath+","+b);
        }
        //下载请求
        // url 下载地址。
        // fileFolder 保存的文件夹。
        // fileName 文件名。
        // isRange 是否断点续传下载。
        // isDeleteOld 如果发现存在同名文件，是否删除后重新下载，如果不删除，则直接下载成功。
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(url, dirPath, fileName, true, false);

        // what 区分下载。
        // downloadRequest 下载请求对象。
        // downloadListener 下载监听。
        downloadQueue.add(what, downloadRequest, new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception e) {
                // 发送下载失败事件
                EventBus.getDefault().post(new DataEvent(Consts.EventType.EVENT_DOWNLOAD_ERROR, what));
            }

            @Override
            public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
                //计算进度
                int progress = 0;
                if (allCount != 0) {
                    progress = (int) (beforeLength * 100 / allCount);
                }

                ArrayList<Integer> list = new ArrayList<>();
                list.add(what);
                list.add(progress);
                // 发送下载开始事件
                EventBus.getDefault().post(new DataEvent(Consts.EventType.EVENT_DOWNLOAD_START, list));
            }

            @Override
            public void onProgress(int what, int progress, long fileCount) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(what);
                list.add(progress);
                // 发送下载开始事件
                EventBus.getDefault().post(new DataEvent(Consts.EventType.EVENT_DOWNLOAD_PROGRESS, list));
            }

            @Override
            public void onFinish(int what, String s) {
                // 发送下载完成事件
                EventBus.getDefault().post(new DataEvent(Consts.EventType.EVENT_DOWNLOAD_FINISH, what));
            }

            @Override
            public void onCancel(int i) {

            }
        });
    }


}
