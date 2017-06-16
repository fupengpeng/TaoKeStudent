package com.taoke.taokestudent.presenter.personalcenter;

import com.taoke.taokestudent.activity.personalcenter.ConnectionAccountActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.entity.ConnectionAccount;
import com.taoke.taokestudent.model.UserModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;

import java.util.List;

/**
 * 关联账号界面主导器
 */
public class ConnectionAccountActivityPresenter extends BaseActivityPresenter {
    /* 关联账号界面 */
    private ConnectionAccountActivity activity;
    /* 用户业务 */
    private UserModel userModel;

    public ConnectionAccountActivityPresenter(ConnectionAccountActivity activity) {
        super(activity);
        this.activity = activity;
        userModel = new UserModel();
    }

    /**
     * 获取家长列表
     */
    public void loadConnectionAccountList() {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 登录
        userModel.loadConnectionAccountList(MyApplication.getInstance().getUser().getUid(),
                new ListCallBack<ConnectionAccount>() {
                    @Override
                    public void onSuccess(List<ConnectionAccount> datas) { // 登录成功
                        // 关闭等待对话框
                        closeWaitDialog();
                       // 设置数据
                        activity.setData(datas);
                    }

                    @Override
                    public void onFail(Exception e) { // 登录失败
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 设置数据
                        activity.setData(null);
                    }
                });
    }
}
