package com.taoke.taokestudent.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 */
public class BaseActivity extends FragmentActivity {
    /* 用于解除ButterKnife绑定 */
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化
        init();
    }

    protected void onCreate(Bundle savedInstanceState, int layoutResId) {
        onCreate(savedInstanceState, layoutResId, false);
    }

    protected void onCreate(Bundle savedInstanceState, int layoutResId, boolean isFullScreen) {
        super.onCreate(savedInstanceState);
        if (isFullScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(layoutResId);
        // 绑定ButterKnife
        unbinder = ButterKnife.bind(this);
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        // 保存Activity
        MyApplication.getInstance().addActivity(this);
    }

    /**
     * 打开Activity
     *
     * @param cls 目标class
     */
    public void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    /**
     * 添加Fragment
     *
     * @param fragment    新增Fragment
     * @param containerId Fragment容器Id
     */
    public void addFragment(BaseFragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction()
                .add(containerId, fragment).commitAllowingStateLoss();
    }

    /**
     * 替换Fragment
     *
     * @param fragment    要被替换成的Fragment
     * @param containerId Fragment容器Id
     */
    public void replaceFragment(BaseFragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment).commitAllowingStateLoss();
    }

    /**
     * 显示Fragment
     *
     * @param containerId
     * @param oldFragment
     * @param newFragment
     */
    public void showFragment(int containerId, BaseFragment oldFragment, BaseFragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!newFragment.isAdded()) {
            transaction.add(containerId, newFragment);
        }
        if (oldFragment != null) {
            transaction.hide(oldFragment);
        }
        transaction.show(newFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏Fragment
     *
     * @param fragment 被隐藏的Fragment
     */
    public void hideFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(fragment)
                .commitAllowingStateLoss();
    }

    /**
     * 发送事件
     */
    public void postEvent(int type, Object data) {
        EventBus.getDefault().post(new DataEvent(type, data));
    }

    @Override
    protected void onDestroy() {
        // 移除保存的Activity
        MyApplication.getInstance().removeActivity(this);
        super.onDestroy();
        // 解除ButterKnife绑定
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
