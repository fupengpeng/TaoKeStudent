package com.taoke.taokestudent.presenter.microlessonvideo;

import com.taoke.taokestudent.activity.microlessonvideo.Micro3LessonVideoDetailActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

/**
 * 微课详情界面主导器
 */
public class Micro3LessonVideoDetailActivityPresenter extends BaseActivityPresenter {
    /* 微课详情界面 */
    private Micro3LessonVideoDetailActivity activity;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    public Micro3LessonVideoDetailActivityPresenter(Micro3LessonVideoDetailActivity activity) {
        super(activity);
        this.activity = activity;
        microLessonVideoModel = new MicroLessonVideoModel();
    }

    /**
     * 购买课程
     *
     * @param uid 用户ID
     * @param tid 本次购买的资源ID
     */
    public void buyLesson(String uid, final String tid) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.BUY_LESSONING);
        // 加载微课视频列表
        microLessonVideoModel.payWeiKe(uid, tid, new ObjectCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                // 关闭等待对话框
                closeWaitDialog();
                // 设置已购买
                activity.setBuyed(tid);
                // 更新用户信息
                MyApplication.getInstance().updateUserInfo();
                // 发送已购买课程的事件
                postEvent(Consts.EventType.EVENT_BUY_LESSON_SUCCESS, tid);
            }

            @Override
            public void onFail(Exception e) {
                // 关闭等待对话框
                closeWaitDialog();
                InfoUtils.showInfo(activity, e.getMessage());
            }
        });
    }


}
