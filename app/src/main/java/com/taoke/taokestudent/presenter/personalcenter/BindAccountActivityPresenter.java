package com.taoke.taokestudent.presenter.personalcenter;

import com.taoke.taokestudent.activity.personalcenter.BindAccountActivity;
import com.taoke.taokestudent.activity.personalcenter.RegisterActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.model.UserModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

/**
 * 绑定账号界面主导器
 */
public class BindAccountActivityPresenter extends BaseActivityPresenter {
    /* 绑定账号界面 */
    private BindAccountActivity activity;
    /* 用户信息业务 */
    private UserModel userModel;

    public BindAccountActivityPresenter(BindAccountActivity activity) {
        super(activity);
        this.activity = activity;
        userModel = new UserModel();
    }

    /**
     * 绑定账号
     *
     * @param phoneNumber 手机号
     * @param password    密码
     */
    public void bindAccount(String phoneNumber, String password) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.BINDING);
        // 绑定账号
        userModel.bindAccount(MyApplication.getInstance().getUser().getUid(), phoneNumber, password,
                new ObjectCallBack<String>() {
                    @Override
                    public void onSuccess(String data) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        InfoUtils.showInfo(activity, data);
                        activity.finish();
                        // 发送更新关联账号事件
                        postEvent(Consts.EventType.EVENT_UPDATE_CONNECTION_ACCOUNT, null);
                    }

                    @Override
                    public void onFail(Exception e) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        InfoUtils.showInfo(activity, e.getMessage());
                    }
                });
    }

    /**
     * 发送验证码
     *
     * @param phoneNumber 手机号
     */
    public void sendValidateCode(String phoneNumber) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.SEND_VALIDATE_CODE);
        // 发送验证码
        userModel.sendValidateCode(phoneNumber, new ObjectCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                // 关闭等待对话框
                closeWaitDialog();
                // 设置验证码
                activity.setValidateCode(data);
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
