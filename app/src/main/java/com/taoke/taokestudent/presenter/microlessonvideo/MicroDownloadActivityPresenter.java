package com.taoke.taokestudent.presenter.microlessonvideo;

import android.app.Activity;

import com.taoke.taokestudent.activity.microlessonvideo.MicroDownloadActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.MicroDownloadRecord;
import com.taoke.taokestudent.entity.MicroLessonDetailListItem;
import com.taoke.taokestudent.entity.MicroLessonDetailListResponseData;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.List;

/**
 * 微课视频下载界面主导器
 */
public class MicroDownloadActivityPresenter extends BaseActivityPresenter {
    /* 微课视频下载界面 */
    private MicroDownloadActivity activity;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    public MicroDownloadActivityPresenter(MicroDownloadActivity activity) {
        super(activity);
        this.activity = activity;
        this.microLessonVideoModel = new MicroLessonVideoModel();
    }

    /**
     * 加载视频详情中的收费课程播放地址
     *
     * @param uid       用户id
     * @param subjectId 课程ID
     * @param listItem  视频
     */
    public void loadDetailChargeLessonVideoURL(String uid, String subjectId, final MicroLessonDetailListItem listItem) {
        // 加载微课视频列表
        microLessonVideoModel.loadDetailChargeLessonVideoURL(uid, subjectId, listItem.getId(),
                new ListCallBack<String>() {
                    @Override
                    public void onSuccess(List<String> datas) {
                        // 设置视频URL
                        listItem.setUrlArr(datas);
                        activity.setVideoUrls(listItem);
                    }

                    @Override
                    public void onFail(Exception e) {
                        InfoUtils.showInfo(activity, e.getMessage());
                    }
                });
    }

    /**
     * 加载下载列表数据
     *
     * @param tid     课程id
     * @param isBuyed 是否购买
     * @param p       页码
     */
    public void loadDownloadListDatas(String tid, boolean isBuyed, int p) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 根据购买状况加载数据
        if (isBuyed) {
            // 加载收费列表
            microLessonVideoModel.loadDetailChargeLessonList(tid, p, new DownloadObjectCallBack());
        } else {
            // 加载免费列表
            microLessonVideoModel.loadDetailFreeLessonList(tid, p, new DownloadObjectCallBack());
        }
    }

    /**
     * 加载下载列表的回调
     */
    private class DownloadObjectCallBack implements ObjectCallBack<MicroLessonDetailListResponseData> {

        @Override
        public void onSuccess(MicroLessonDetailListResponseData data) {
            // 关闭显示对话框
            closeWaitDialog();
            // 设置下载状态
            if (data != null && data.getList() != null && data.getList().size() > 0) {
                List<MicroLessonDetailListItem> list = data.getList();
                for (MicroLessonDetailListItem item : list) {
                    List<MicroDownloadRecord> records = MicroDownloadRecord.find(MicroDownloadRecord.class, "videoId=?", item.getId());
                    if (records != null && records.size() > 0) {
                        MicroDownloadRecord record = records.get(0);
                        item.setType(record.getType());
                    } else {
                        item.setType("");
                    }
                }
            }

            // 设置数据
            activity.setData(data);
        }

        @Override
        public void onFail(Exception e) {
            // 关闭显示对话框
            closeWaitDialog();
            //
            InfoUtils.showInfo(activity, e.getMessage());
        }
    }

}
