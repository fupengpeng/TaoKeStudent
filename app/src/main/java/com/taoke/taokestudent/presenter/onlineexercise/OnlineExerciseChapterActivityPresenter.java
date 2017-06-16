package com.taoke.taokestudent.presenter.onlineexercise;

import com.taoke.taokestudent.activity.onlineexercise.OnlineExerciseChapterActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.entity.OnlineExerciseChapter;
import com.taoke.taokestudent.entity.OnlineExerciseQuestion;
import com.taoke.taokestudent.model.OnlineExerciseModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线做题章节界面主导器
 */
public class OnlineExerciseChapterActivityPresenter extends BaseActivityPresenter {
    /* 在线做题章节界面 */
    private OnlineExerciseChapterActivity activity;
    /* 在线做题业务 */
    private OnlineExerciseModel onlineExerciseModel;

    public OnlineExerciseChapterActivityPresenter(OnlineExerciseChapterActivity activity) {
        super(activity);
        this.activity = activity;
        this.onlineExerciseModel = new OnlineExerciseModel();
    }

    public void loadQuestionList(String fid, int num) {
        // 打开等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载章节列表
        onlineExerciseModel.getQuestionList(fid, num, new ListCallBack<OnlineExerciseQuestion>() {
            @Override
            public void onSuccess(List<OnlineExerciseQuestion> datas) {
                // 关闭对话框
                closeWaitDialog();
                // 设置数据
                activity.setQuestionListDatas((ArrayList<OnlineExerciseQuestion>) datas);
            }

            @Override
            public void onFail(Exception e) {
                // 关闭对话框
                closeWaitDialog();
                // 显示错误信息
                InfoUtils.showInfo(activity, "您选择的知识点没有题进行练习");
            }
        });
    }

    /**
     * 加载章节列表
     *
     * @param fid 父级单元id
     */
    public void loadChapterList(String fid) {
        // 打开等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载章节列表
        onlineExerciseModel.getChapterList(fid, new ListCallBack<OnlineExerciseChapter>() {
            @Override
            public void onSuccess(List<OnlineExerciseChapter> datas) {
                // 关闭对话框
                closeWaitDialog();
                // 设置数据
                activity.setChapterListDatas(datas);
            }

            @Override
            public void onFail(Exception e) {
                // 关闭对话框
                closeWaitDialog();
                // 设置数据
                activity.setChapterListDatas(null);
            }
        });
    }
}
