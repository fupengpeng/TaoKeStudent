package com.taoke.taokestudent.presenter.microlessonvideo;

import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.MicroLessonDetailListResponseData;
import com.taoke.taokestudent.fragment.microlessonvideo.Micro3DetailFreeFragment;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.presenter.BaseFragmentPresenter;

/**
 * 微课视频-微课视频-微课视频 详情-免费课程界面的主导器
 */
public class Micro3DetailFreeFragmentPresenter extends BaseFragmentPresenter {
    /* 微课视频-微课视频-微课视频 详情-免费课程 */
    private Micro3DetailFreeFragment fragment;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    public Micro3DetailFreeFragmentPresenter(Micro3DetailFreeFragment fragment) {
        super(fragment);
        this.fragment = fragment;
        microLessonVideoModel = new MicroLessonVideoModel();
    }

    /**
     * 加载微课视频详情中的免费列表
     */
    public void loadMicroDetailFreeList(String subjectId, int p) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载微课视频列表
        microLessonVideoModel.loadDetailFreeLessonList(subjectId, p,
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
