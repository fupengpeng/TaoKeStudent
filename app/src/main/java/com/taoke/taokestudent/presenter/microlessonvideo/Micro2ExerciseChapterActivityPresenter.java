package com.taoke.taokestudent.presenter.microlessonvideo;

import com.taoke.taokestudent.activity.microlessonvideo.Micro2ExerciseChapterActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.entity.ExerciseChapter;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.List;

/**
 * 习题微课章节界面主导器
 */
public class Micro2ExerciseChapterActivityPresenter extends BaseActivityPresenter {
    /* 习题微课章节界面 */
    private Micro2ExerciseChapterActivity activity;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    public Micro2ExerciseChapterActivityPresenter(Micro2ExerciseChapterActivity activity) {
        super(activity);
        this.activity = activity;
        this.microLessonVideoModel = new MicroLessonVideoModel();
    }

    /**
     * 加载习题微课-课程详情-章节列表
     */
    public void loadExerciseChapterList(String subjectId) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载微课视频列表
        microLessonVideoModel.loadExerciseChapterList(subjectId, new ListCallBack<ExerciseChapter>() {
            @Override
            public void onSuccess(List<ExerciseChapter> datas) {
                // 关闭等待对话框
                closeWaitDialog();
                // 设置数据
                activity.setData(datas);
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
