package com.taoke.taokestudent.application;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

import com.orm.SugarApp;
import com.taoke.taokestudent.MyEventBusIndex;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.crash.CrashHandler;
import com.taoke.taokestudent.entity.MicroLessonVideoBaseClass;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.model.UserModel;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.SPUtils;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Application
 * Created by zhangshuaishuai on 2017/2/17.
 */
public class MyApplication extends SugarApp {
    /* Application实例 */
    private static MyApplication instance;
    /* Activity集合，用于彻底退出应用 */
    private List<Activity> activities;

    /* 用户 */
    private User user;

    /* 微课视频当前基础分类 */
    private MicroLessonVideoBaseClass microLessonVideoBaseClass;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化实例
        instance = this;
        // 创建Activity集合
        activities = new ArrayList<>();

        //初始化NoHttp
        NoHttp.initialize(this);
        // 开始NoHttp的调试模式, 这样就能看到请求过程和日志（可选）
        Logger.setTag("jdlx");
        Logger.setDebug(true);

        // 创建未知异常处理类
        CrashHandler crashHandler = new CrashHandler(this);
        //通知android框架，出了异常，并且没加catch,android框架调crashHandler
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);

        // 初始化EventBus
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        EventBus.getDefault().register(this);

    }

    /**
     * 接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(DataEvent dataEvent) {
        switch (dataEvent.getType()) {
            case Consts.EventType.EVENT_LOGIN_SUCCESS: // 登录成功
                // 将uid保存到本地
                SPUtils.put(this, Consts.SPKeys.UID_KEY, user.getUid());
                break;
            case Consts.EventType.EVENT_EXIT_ACCOUNT: // 退出账号
                // 移除本地保存的Uid
                SPUtils.remove(this, Consts.SPKeys.UID_KEY);
                // 清空用户信息
                this.user = null;
                break;
        }
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public User getUser() {
        return user;
    }

    /**
     * 设置用户信息
     *
     * @param user 用户信息
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 结束进程
     */
    public void finishActivity() {
        // 关闭Activity
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        //结束进程
        Process.killProcess(Process.myPid());
    }

    /**
     * 向Activity集合中添加元素
     *
     * @param activity activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 从Activity集合中移除元素
     *
     * @param activity activity
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 获取Application实例
     *
     * @return Application实例
     */
    public static MyApplication getInstance() {
        return instance;
    }


    /**
     * 获取微课视频当前基础分类
     *
     * @return 微课视频当前基础分类
     */
    public MicroLessonVideoBaseClass getMicroLessonVideoBaseClass() {
        return microLessonVideoBaseClass;
    }

    /**
     * 设置微课视频当前基础分类
     *
     * @param microLessonVideoBaseClass 微课视频当前基础分类
     */
    public void setMicroLessonVideoBaseClass(MicroLessonVideoBaseClass microLessonVideoBaseClass) {
        this.microLessonVideoBaseClass = microLessonVideoBaseClass;
    }

    /**
     * 更新用户数据
     */
    public void updateUserInfo() {
        new UserModel().loadUserInfoByUid(user.getUid(), new ObjectCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                setUser(data);
                // 发送更新用户数据事件
                EventBus.getDefault().post(new DataEvent(Consts.EventType.EVENT_UPDATE_USER, null));
            }

            @Override
            public void onFail(Exception e) {
                InfoUtils.showInfo(instance, e.getMessage());
            }
        });
    }
}
