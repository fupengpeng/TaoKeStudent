package com.taoke.taokestudent.presenter.microlessonvideo;

import android.os.AsyncTask;

import com.taoke.taokestudent.activity.microlessonvideo.MicroMyDownloadVideoListActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.MicroDownloadRecord;
import com.taoke.taokestudent.entity.MicroDownloadVideoUrl;
import com.taoke.taokestudent.entity.MicroLessonDetailListItem;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的下载视频列表界面主导器
 */
public class MicroMyDownloadVideoListActivityPresenter extends BaseActivityPresenter {
    /* 我的下载视频列表界面 */
    private MicroMyDownloadVideoListActivity activity;

    public MicroMyDownloadVideoListActivityPresenter(MicroMyDownloadVideoListActivity activity) {
        super(activity);
        this.activity = activity;
    }

    /**
     * 删除选中项
     *
     * @param deleteList
     */
    public void deleteCheckedItem(List<MicroLessonDetailListItem> deleteList) {
        // 删除记录
        for (MicroLessonDetailListItem item : deleteList) {
            // 删除记录
            List<MicroDownloadRecord> records = MicroDownloadRecord.find(MicroDownloadRecord.class,
                    "videoId=?", item.getId());
            for (MicroDownloadRecord record : records) {
                record.delete();
            }
            // 删除URL记录
            List<MicroDownloadVideoUrl> urlRecords = MicroDownloadVideoUrl.find(MicroDownloadVideoUrl.class,
                    "videoId=?", item.getId());
            for (MicroDownloadVideoUrl record : urlRecords) {
                record.delete();
            }
            // 删除文件
            File dirFile = new File(Consts.DOWNLOAD_BASE_PATH + item.getParentName() + "/" + item.getName() + "/");
            if (dirFile.exists()) {
                File[] files = dirFile.listFiles();
                for (File file : files) {
                    file.delete();
                }
                dirFile.delete();
            }
        }
        // 加载视频列表数据
        loadVideoListDatas(deleteList.get(0).getParentName());
    }

    /**
     * 加载视频列表数据
     *
     * @param folderName
     */
    public void loadVideoListDatas(final String folderName) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载文件夹列表数据
        new AsyncTask<String, String, List<MicroLessonDetailListItem>>() {
            @Override
            protected List<MicroLessonDetailListItem> doInBackground(String... params) {
                // 加载文件夹列表
                List<MicroDownloadRecord> records = MicroDownloadRecord.find(MicroDownloadRecord.class,
                        "PARENT_NAME=?", folderName);

                List<MicroLessonDetailListItem> list = new ArrayList<>();
                for (MicroDownloadRecord record : records) {
                    MicroLessonDetailListItem item = new MicroLessonDetailListItem();
                    item.setId(record.getVideoId());
                    item.setName(record.getVideoName());
                    item.setParentName(record.getParentName());
                    item.setType(record.getType());
                    // 加载视频URL
                    List<MicroDownloadVideoUrl> urls = MicroDownloadVideoUrl.find(MicroDownloadVideoUrl.class,
                            "videoId=?", record.getVideoId());
                    List<String> arrUrl = new ArrayList<String>();
                    for (MicroDownloadVideoUrl url : urls) {
                        arrUrl.add(url.getVideoURL());
                    }
                    item.setUrlArr(arrUrl);
                    list.add(item);
                }

                return list;
            }

            @Override
            protected void onPostExecute(List<MicroLessonDetailListItem> list) {
                // 关闭对话框
                closeWaitDialog();
                // 设置数据
                activity.setData(list);
            }
        }.execute();
    }
}
