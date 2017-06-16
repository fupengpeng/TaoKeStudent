package com.taoke.taokestudent.presenter.personalcenter;

import com.taoke.taokestudent.activity.personalcenter.RegisterActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.model.UserModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

/**
 * 注册界面主导器
 */
public class RegisterActivityPresenter extends BaseActivityPresenter {
    /* 注册界面 */
    private RegisterActivity activity;
    /* 用户信息业务 */
    private UserModel userModel;

    public RegisterActivityPresenter(RegisterActivity activity) {
        super(activity);
        this.activity = activity;
        userModel = new UserModel();
    }

    /**
     * 注册
     *
     * @param phoneNumber 手机号
     * @param password    密码
     */
    public void register(String phoneNumber, String password) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.REGISTER);
        // 注册
        userModel.register(phoneNumber, password, new ObjectCallBack<String>() {
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
                activity.setRegisterValidateCode(data);
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
