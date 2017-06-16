package com.taoke.taokestudent.presenter.personalcenter;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;
import com.taoke.taokestudent.activity.personalcenter.RechargeActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.Order;
import com.taoke.taokestudent.model.UserModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.LogUtils;

/**
 * 支付界面主导器
 */
public class RechargeActivityPresenter extends BaseActivityPresenter {
    /* 支付界面 */
    private RechargeActivity activity;
    /* 用户业务 */
    private UserModel userModel;

    public RechargeActivityPresenter(RechargeActivity activity) {
        super(activity);
        this.activity = activity;
        userModel = new UserModel();
    }

    /**
     * 支付
     *
     * @param increase 充值金额
     * @param type     充值方式
     */
    public void pay(String increase, final String type) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.CREATE_ORDER);
        // 生成订单
        userModel.createOrder(MyApplication.getInstance().getUser().getUid(), increase, type,
                new ObjectCallBack<Order>() {
                    @Override
                    public void onSuccess(Order data) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 根据订单，调用支付客户端
                        if (Consts.PayType.ZFB.equals(type)) {
                            // 启动支付宝
                            startZFB(data.getSignString());
                        }
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
     * 启动支付宝支付
     */
    private void startZFB(final String signString) {

        LogUtils.i(signString);


        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(signString, true);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
