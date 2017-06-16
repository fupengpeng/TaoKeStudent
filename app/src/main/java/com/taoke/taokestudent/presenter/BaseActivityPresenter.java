package com.taoke.taokestudent.presenter;

import android.app.Activity;
import android.app.Dialog;

import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.util.DialogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 主导器基类
 */
public class BaseActivityPresenter {
    /* 对话框工具类 */
    public DialogUtils dialogUtils = null;
    /* 网络请求时的等待对话框 */
    private Dialog dialog = null;

    public BaseActivityPresenter(Activity activity) {
        // 创建对话框工具类
        dialogUtils = new DialogUtils(activity);
    }

    /**
     * 发送事件
     */
    public void postEvent(int type, Object data) {
        EventBus.getDefault().post(new DataEvent(type, data));
    }

    /**
     * 显示等待对话框
     *
     * @param message 提示信息
     */
    public void showWaitDialog(String message) {
        if (dialogUtils != null && (dialog == null || !dialog.isShowing())) {
            dialog = dialogUtils.showLoading(message);
        }
    }

    /**
     * 关闭等待对话框
     */
    public void closeWaitDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
