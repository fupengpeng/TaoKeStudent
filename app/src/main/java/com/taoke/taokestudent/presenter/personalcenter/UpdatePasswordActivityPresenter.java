package com.taoke.taokestudent.presenter.personalcenter;

import com.taoke.taokestudent.activity.personalcenter.UpdatePasswordActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.model.UserModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

/**
 * 修改密码界面主导器
 */
public class UpdatePasswordActivityPresenter extends BaseActivityPresenter {
    /* Activity */
    private UpdatePasswordActivity activity;
    /* 用户业务 */
    private UserModel userModel;

    public UpdatePasswordActivityPresenter(UpdatePasswordActivity activity) {
        super(activity);
        this.activity = activity;
        userModel = new UserModel();
    }

    /**
     * 修改密码
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     */
    public void updatePassword(String oldPwd, String newPwd) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.UPDATE_USER_INFO);
        // 修改密码
        userModel.updatePassword(MyApplication.getInstance().getUser().getUid(),
                oldPwd, newPwd, new ObjectCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        InfoUtils.showInfo(activity, data);
                        activity.finish();
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
