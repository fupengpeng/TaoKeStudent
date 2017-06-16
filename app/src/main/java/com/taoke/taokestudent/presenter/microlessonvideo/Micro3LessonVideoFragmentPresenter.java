package com.taoke.taokestudent.presenter.microlessonvideo;

import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.MicroBaseClassRecord;
import com.taoke.taokestudent.entity.MicroLessonListResponseData;
import com.taoke.taokestudent.entity.MicroLessonVideoBaseClass;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.fragment.microlessonvideo.Micro3LessonVideoFragment;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.presenter.BaseFragmentPresenter;

import java.util.List;

/**
 * 微课视频-微课视频-微课视频界面的主导器
 */
public class Micro3LessonVideoFragmentPresenter extends BaseFragmentPresenter {
    /* 微课视频-微课视频-微课视频界面 */
    private Micro3LessonVideoFragment fragment;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    public Micro3LessonVideoFragmentPresenter(Micro3LessonVideoFragment fragment) {
        super(fragment);
        this.fragment = fragment;
        microLessonVideoModel = new MicroLessonVideoModel();
    }

    /**
     * 加载微课视频列表
     */
    public void loadMicroLessonVideoList(MicroLessonVideoBaseClass baseClass, int p) {
        User user = MyApplication.getInstance().getUser();
        String uid = "";
        if (user != null) {
            uid = user.getUid();
        }

        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载微课视频列表
        microLessonVideoModel.loadLessonList(baseClass, uid, p,
                new ObjectCallBack<MicroLessonListResponseData>() {
                    @Override
                    public void onSuccess(MicroLessonListResponseData data) {
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
