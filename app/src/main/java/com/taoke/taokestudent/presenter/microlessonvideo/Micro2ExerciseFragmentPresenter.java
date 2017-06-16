package com.taoke.taokestudent.presenter.microlessonvideo;

import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.MicroLessonListResponseData;
import com.taoke.taokestudent.entity.MicroLessonVideoBaseClass;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.fragment.microlessonvideo.Micro2ExerciseFragment;
import com.taoke.taokestudent.fragment.microlessonvideo.Micro3LessonVideoFragment;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.presenter.BaseFragmentPresenter;

/**
 * 微课视频-微课视频-习题微课界面的主导器
 */
public class Micro2ExerciseFragmentPresenter extends BaseFragmentPresenter {
    /* 微课视频-微课视频-习题微课界面 */
    private Micro2ExerciseFragment fragment;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    public Micro2ExerciseFragmentPresenter(Micro2ExerciseFragment fragment) {
        super(fragment);
        this.fragment = fragment;
        microLessonVideoModel = new MicroLessonVideoModel();
    }

    /**
     * 加载习题微课列表
     */
    public void loadMicroExerciseList(MicroLessonVideoBaseClass baseClass, int p) {
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
