package com.taoke.taokestudent.presenter.onlineexercise;

import com.taoke.taokestudent.activity.onlineexercise.OnlineExerciseReportActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.OnlineExerciseTestResultResponseData;
import com.taoke.taokestudent.model.OnlineExerciseModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

/**
 * 在线做题结果报告界面主导器
 */
public class OnlineExerciseReportActivityPresenter extends BaseActivityPresenter {
    /* 在线做题结果报告界面 */
    private OnlineExerciseReportActivity activity;
    /* 在线做题业务 */
    private OnlineExerciseModel onlineExerciseModel;

    public OnlineExerciseReportActivityPresenter(OnlineExerciseReportActivity activity) {
        super(activity);
        this.activity = activity;
        this.onlineExerciseModel = new OnlineExerciseModel();
    }

    /**
     * 获取答题报告
     *
     * @param recordId
     */
    public void getTestReport(String recordId) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.REPORT_LOADING);
        // 获取答题报告
        onlineExerciseModel.getTestReport(recordId, new ObjectCallBack<OnlineExerciseTestResultResponseData>() {
            @Override
            public void onSuccess(OnlineExerciseTestResultResponseData data) {
                // 关闭对话框
                closeWaitDialog();
                // 设置数据
                activity.setData(data);
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
