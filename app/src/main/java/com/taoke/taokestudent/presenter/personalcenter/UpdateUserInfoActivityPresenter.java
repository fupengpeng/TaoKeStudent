package com.taoke.taokestudent.presenter.personalcenter;

import com.taoke.taokestudent.activity.personalcenter.UpdateUserInfoActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.model.UserModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

/**
 * 用户信息界面主导器
 */
public class UpdateUserInfoActivityPresenter extends BaseActivityPresenter {
    /* 用户信息界面 */
    private UpdateUserInfoActivity activity;
    /* 用户业务 */
    private UserModel userModel;

    public UpdateUserInfoActivityPresenter(UpdateUserInfoActivity activity) {
        super(activity);
        this.activity = activity;
        userModel = new UserModel();
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     */
    public void updateUserInfo(final User user) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.UPDATE_USER_INFO);
        // 修改用户信息
        userModel.updateUserInfo(user, new ObjectCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                // 重新获取用户信息
                loadUserInfoByUid(user.getUid());
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
     * 根据Uid加载用户信息
     *
     * @param uid uid
     */
    public void loadUserInfoByUid(String uid) {
        userModel.loadUserInfoByUid(uid, new ObjectCallBack<User>() {
            @Override
            public void onSuccess(User user) {
                // 关闭等待对话框
                closeWaitDialog();
                // 保存用户信息
                MyApplication.getInstance().setUser(user);
                // 发送更新用户信息事件
                postEvent(Consts.EventType.EVENT_UPDATE_USER, null);
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
