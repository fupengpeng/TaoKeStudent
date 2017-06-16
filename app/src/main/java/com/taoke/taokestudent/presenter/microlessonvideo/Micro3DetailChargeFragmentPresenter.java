package com.taoke.taokestudent.presenter.microlessonvideo;

import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.MicroLessonDetailListResponseData;
import com.taoke.taokestudent.fragment.microlessonvideo.Micro3DetailChargeFragment;
import com.taoke.taokestudent.fragment.microlessonvideo.Micro3DetailFreeFragment;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.presenter.BaseFragmentPresenter;

import java.util.List;

/**
 * 微课视频-微课视频-微课视频 详情-收费课程界面的主导器
 */
public class Micro3DetailChargeFragmentPresenter extends BaseFragmentPresenter {
    /* 微课视频-微课视频-微课视频 详情-收费课程 */
    private Micro3DetailChargeFragment fragment;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    public Micro3DetailChargeFragmentPresenter(Micro3DetailChargeFragment fragment) {
        super(fragment);
        this.fragment = fragment;
        microLessonVideoModel = new MicroLessonVideoModel();
    }

    /**
     * 加载视频详情中的收费课程播放地址
     *
     * @param uid       用户id
     * @param subjectId 课程ID
     * @param subId     视频id
     * @param name      视频名
     */
    public void loadDetailChargeLessonVideoURL(String uid, String subjectId, String subId, final String name) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载微课视频列表
        microLessonVideoModel.loadDetailChargeLessonVideoURL(uid, subjectId, subId,
                new ListCallBack<String>() {
                    @Override
                    public void onSuccess(List<String> datas) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 设置播放地址数据
                        fragment.setVideoUrlData(name, datas);
                    }

                    @Override
                    public void onFail(Exception e) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 设置播放地址数据
                        fragment.setVideoUrlData(name, null);
                    }
                });
    }

    /**
     * 加载微课视频详情中的收费列表
     */
    public void loadMicroDetailChargeList(String subjectId, int p) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载微课视频列表
        microLessonVideoModel.loadDetailChargeLessonList(subjectId, p,
                new ObjectCallBack<MicroLessonDetailListResponseData>() {
                    @Override
                    public void onSuccess(MicroLessonDetailListResponseData data) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 设置数据
                        fragment.setData(data);
                    }

                    @Override
                    public void onFail(Exception e) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 设置数据
                        fragment.setData(null);
                    }
                });
    }
}
