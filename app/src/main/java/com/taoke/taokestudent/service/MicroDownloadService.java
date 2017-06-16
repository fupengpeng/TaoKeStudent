package com.taoke.taokestudent.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.entity.MicroDownloadRecord;
import com.taoke.taokestudent.entity.MicroDownloadVideoUrl;
import com.taoke.taokestudent.entity.MicroLessonDetailListItem;
import com.taoke.taokestudent.model.DownloadModel;
import com.taoke.taokestudent.util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * 微课视频下载服务
 */
public class MicroDownloadService extends Service {
    /* 下载业务 */
    private DownloadModel downloadModel;

    public MicroDownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downloadModel = new DownloadModel();

        // 注册
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //
        MicroLessonDetailListItem listItem = intent.getParcelableExtra(Consts.IntentExtraKey.MICRO_DOWNLOAD);
        if (listItem != null) {
            // 下载
            // 下载路径
            String pathDir = Consts.DOWNLOAD_BASE_PATH + listItem.getParentName() + "/" + listItem.getName() + "/";
            List<String> urlArr = listItem.getUrlArr();

            for (int i = 0; i < urlArr.size(); i++) {
                downloadModel.downloadMicroVideo(urlArr.get(i), pathDir, listItem.getName() + ".mp4",
                        Integer.parseInt(listItem.getId()) * 10 + i);

                // 保存URL
                MicroDownloadVideoUrl videoUrl = new MicroDownloadVideoUrl();
                videoUrl.setVideoId(listItem.getId());
                videoUrl.setVideoURL(urlArr.get(i));
                videoUrl.save();
            }

            // 保存数据到数据库
            MicroDownloadRecord record = new MicroDownloadRecord();
            record.setVideoId(listItem.getId());
            record.setVideoName(listItem.getName());
            record.setParentName(listItem.getParentName());
            record.setTime(System.currentTimeMillis());
            record.setType(Consts.DownloadType.WAIT);
            record.save();

        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(DataEvent dataEvent) {
        switch (dataEvent.getType()) {
            case Consts.EventType.EVENT_DOWNLOAD_START: // 开始下载
                ArrayList<Integer> data = (ArrayList<Integer>) dataEvent.getData();
                int what = data.get(0);
                if(what%10 !=0){
                    break;
                }
                // 修改记录
                List<MicroDownloadRecord> records = MicroDownloadRecord.find(MicroDownloadRecord.class, "videoId=?", "" + what/10);
                if (records != null && records.size() > 0) {
                    MicroDownloadRecord record = records.get(0);
                    record.setType(Consts.DownloadType.DOWNLOADING);
                    record.save();
                }
                break;
            case Consts.EventType.EVENT_DOWNLOAD_FINISH: // 下载完成
                what = (Integer) dataEvent.getData();
                if(what%10 !=0){
                    break;
                }
                // 修改记录
                records = MicroDownloadRecord.find(MicroDownloadRecord.class, "videoId=?", "" + what/10);
                if (records != null && records.size() > 0) {
                    MicroDownloadRecord record = records.get(0);
                    record.setType(Consts.DownloadType.FINISH);
                    record.save();
                }
                break;
            case Consts.EventType.EVENT_DOWNLOAD_ERROR: // 下载失败
                what = (Integer) dataEvent.getData();
                if(what%10 !=0){
                    break;
                }
                // 修改记录
                records = MicroDownloadRecord.find(MicroDownloadRecord.class, "videoId=?", "" + what/10);
                if (records != null && records.size() > 0) {
                    MicroDownloadRecord record = records.get(0);
                    record.setType(Consts.DownloadType.ERROR);
                    record.save();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        // 注销
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
