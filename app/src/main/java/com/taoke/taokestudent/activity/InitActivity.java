package com.taoke.taokestudent.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.presenter.InitActivityPresenter;
import com.taoke.taokestudent.util.SPUtils;
import com.taoke.taokestudent.util.ToastUtils;

/**
 * 初始化Activity
 */
public class InitActivity extends BaseActivity {

    /* 主导器 */
    private InitActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_init);
        // 创建主导器
        presenter = new InitActivityPresenter(this);
        // 加载微课视频基础分类信息
        presenter.loadMicroBaseClass();
        // 清除下载残留
        presenter.clearDownloadBreak();
    }

    /**
     * 初始化用户信息
     */
    public void initUser() {
        String uid = (String) SPUtils.get(this, Consts.SPKeys.UID_KEY, "");
        if (!TextUtils.isEmpty(uid)) {
            // 加载用户信息
            presenter.loadUserInfoByUid(uid);
        } else {
            startActivity(MainActivity.class);
            finish();
        }
    }
}
