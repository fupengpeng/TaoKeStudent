package com.taoke.taokestudent.presenter.microlessonvideo;

import com.taoke.taokestudent.activity.microlessonvideo.Micro2ExerciseQuestionActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.MicroExerciseQuestionListResponseData;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;

/**
 * 习题微课习题界面主导器
 */
public class Micro2ExerciseQuestionActivityPresenter extends BaseActivityPresenter {
    /* 习题微课习题界面 */
    private Micro2ExerciseQuestionActivity activity;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    public Micro2ExerciseQuestionActivityPresenter(Micro2ExerciseQuestionActivity activity) {
        super(activity);
        this.activity = activity;
        microLessonVideoModel = new MicroLessonVideoModel();
    }

    public void loadExerciseQuestionList(String classId, int p){
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载微课视频列表
        microLessonVideoModel.loadExerciseQuestionList(classId, p,
                new ObjectCallBack<MicroExerciseQuestionListResponseData>() {
                    @Override
                    public void onSuccess(MicroExerciseQuestionListResponseData data) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 设置数据
                        activity.setData(data);
                    }

                    @Override
                    public void onFail(Exception e) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 设置数据
                        activity.setData(null);
                    }
                });
    }
}
