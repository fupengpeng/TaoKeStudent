package com.taoke.taokestudent.presenter.personalcenter;

import com.taoke.taokestudent.activity.personalcenter.LoginActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.model.UserModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.SPUtils;

/**
 * 登录界面主导器
 */
public class LoginActivityPresenter extends BaseActivityPresenter {
    /* 登录界面 */
    private LoginActivity activity;
    /* 用户信息业务 */
    private UserModel userModel;

    public LoginActivityPresenter(LoginActivity activity) {
        super(activity);
        this.activity = activity;
        userModel = new UserModel();
    }

    /**
     * 登录
     *
     * @param account  账号
     * @param password 密码
     */
    public void login(final String account, String password) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.LOGIN);
        // 登录
        userModel.login(account, password, new ObjectCallBack<User>() {
            @Override
            public void onSuccess(User user) { // 登录成功
                // 关闭等待对话框
                closeWaitDialog();
                // 保存用户信息
                MyApplication.getInstance().setUser(user);
                // 发送登录成功事件
                postEvent(Consts.EventType.EVENT_LOGIN_SUCCESS, null);
                // 关闭登录界面
                activity.finish();
            }

            @Override
            public void onFail(Exception e) { // 登录失败
                // 关闭等待对话框
                closeWaitDialog();
                InfoUtils.showInfo(activity, e.getMessage());
            }
        });
    }
}
