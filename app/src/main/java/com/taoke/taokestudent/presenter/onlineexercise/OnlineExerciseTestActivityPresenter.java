package com.taoke.taokestudent.presenter.onlineexercise;

import android.app.Activity;

import com.taoke.taokestudent.activity.onlineexercise.OnlineExerciseTestActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.OnlineExerciseQuestion;
import com.taoke.taokestudent.model.OnlineExerciseModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.List;

/**
 * 在线做题答题界面主导器
 */
public class OnlineExerciseTestActivityPresenter extends BaseActivityPresenter {
    /* 在线做题答题界面 */
    private OnlineExerciseTestActivity activity;
    /* 在线做题业务 */
    private OnlineExerciseModel onlineExerciseModel;

    public OnlineExerciseTestActivityPresenter(OnlineExerciseTestActivity activity) {
        super(activity);
        this.activity = activity;
        this.onlineExerciseModel = new OnlineExerciseModel();
    }

    /**
     * 上传答题结果
     *
     * @param startTime 答题开始时间
     * @param useTime   答题用时
     * @param questions 试题集
     * @param answers   答案集
     */
    public void submitResult(long startTime, String useTime, List<OnlineExerciseQuestion> questions, List<String> answers) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.SUBMIT_RESULT);

        // 用户id
        String uid = MyApplication.getInstance().getUser().getUid();
        // 题目数量
        int num = questions.size();
        // 题目id
        StringBuffer sbId = new StringBuffer();
        // 用户答案
        StringBuffer sbAnswer = new StringBuffer();

        for (int i = 0; i < num; i++) {
            sbId.append(questions.get(i).getId() + ",");
            sbAnswer.append(answers.get(i) + ",");
        }
        // 去除最后一个“，”
        if (sbId.length() > 0) {
            sbId.deleteCharAt(sbId.length() - 1);
            sbAnswer.deleteCharAt(sbAnswer.length() - 1);
        }

        // 上传答题结果
        onlineExerciseModel.submitResult(uid, num, startTime, useTime, sbId.toString(), sbAnswer.toString(),
                new ObjectCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        // 关闭对话框
                        closeWaitDialog();
                        // 设置答题记录ID
                        activity.setTestRecord(data);
                    }

                    @Override
                    public void onFail(Exception e) {
                        // 关闭对话框
                        closeWaitDialog();
                        // 显示错误信息
                        InfoUtils.showInfo(activity, e.getMessage());
                    }
                });
    }
}
