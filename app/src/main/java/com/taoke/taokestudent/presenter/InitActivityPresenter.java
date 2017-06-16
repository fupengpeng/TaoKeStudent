package com.taoke.taokestudent.presenter;

import android.os.AsyncTask;

import com.taoke.taokestudent.activity.InitActivity;
import com.taoke.taokestudent.activity.MainActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.MicorLessonVideoBaseClassItem;
import com.taoke.taokestudent.entity.MicroDataTypeItem;
import com.taoke.taokestudent.entity.MicroDownloadRecord;
import com.taoke.taokestudent.entity.MicroLessonVideoBaseClass;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClassItem;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.model.UserModel;

import java.util.List;

/**
 * 初始化界面主导器
 */
public class InitActivityPresenter extends BaseActivityPresenter {
    /* Activity */
    private InitActivity activity;
    /* 用户业务 */
    private UserModel userModel;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    /* 微课视频当前基础分类(保存各级的id) */
    MicroLessonVideoBaseClass baseClass;

    public InitActivityPresenter(InitActivity activity) {
        super(activity);
        this.activity = activity;
        userModel = new UserModel();
        microLessonVideoModel = new MicroLessonVideoModel();
        baseClass = new MicroLessonVideoBaseClass();
    }

    /**
     * 清除下载残留信息
     */
    public void clearDownloadBreak() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                // 下载记录
                List<MicroDownloadRecord> records = MicroDownloadRecord.find(MicroDownloadRecord.class,
                        "type=? or type=? or type=?", Consts.DownloadType.DOWNLOADING,
                        Consts.DownloadType.START, Consts.DownloadType.WAIT);
                for (MicroDownloadRecord record : records) {
                    record.setType(Consts.DownloadType.STOP);
                    record.save();
                }

                // 在线做题分类记录
                OnlineExerciseBaseClassItem.deleteAll(OnlineExerciseBaseClassItem.class);

                return null;
            }
        }.execute();
    }

    /**
     * 加载微课视频基础分类信息
     */
    public void loadMicroBaseClass() {
        // 清空数据库中保存的微课视频基础分类信息
        MicorLessonVideoBaseClassItem.deleteAll(MicorLessonVideoBaseClassItem.class);
        MicroDataTypeItem.deleteAll(MicroDataTypeItem.class);

        // 加载学段信息
        microLessonVideoModel.getBasicClass("1", Consts.BaseClassLevel.XDLV,
                new ListCallBack<MicorLessonVideoBaseClassItem>() {
                    @Override
                    public void onSuccess(List<MicorLessonVideoBaseClassItem> datas) {
                        baseClass.setXueduan("" + datas.get(0).getId());
                        // 加载年级
                        loadMicroNianji("" + datas.get(0).getId());
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
    }

    /**
     * 加载年级
     *
     * @param fid
     */
    private void loadMicroNianji(String fid) {
        microLessonVideoModel.getBasicClass(fid, Consts.BaseClassLevel.NJLV,
                new ListCallBack<MicorLessonVideoBaseClassItem>() {
                    @Override
                    public void onSuccess(List<MicorLessonVideoBaseClassItem> datas) {
                        baseClass.setNianji("" + datas.get(0).getId());
                        // 加载科目
                        loadMicroKemu("" + datas.get(0).getId());
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
    }

    /**
     * 加载科目
     *
     * @param fid
     */
    private void loadMicroKemu(String fid) {
        microLessonVideoModel.getBasicClass(fid, Consts.BaseClassLevel.XKLV,
                new ListCallBack<MicorLessonVideoBaseClassItem>() {
                    @Override
                    public void onSuccess(List<MicorLessonVideoBaseClassItem> datas) {
                        baseClass.setKemu("" + datas.get(0).getId());
                        // 加载版本
                        loadMicroBanben("" + datas.get(0).getId());
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
    }

    /**
     * 加载版本
     *
     * @param fid
     */
    private void loadMicroBanben(String fid) {
        microLessonVideoModel.getBasicClass(fid, Consts.BaseClassLevel.BBLV,
                new ListCallBack<MicorLessonVideoBaseClassItem>() {
                    @Override
                    public void onSuccess(List<MicorLessonVideoBaseClassItem> datas) {
                        baseClass.setBanben("" + datas.get(0).getId());
                        // 加载模块
                        loadMicroMokuai("" + datas.get(0).getId());
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
    }

    /**
     * 加载模块
     *
     * @param fid
     */
    private void loadMicroMokuai(String fid) {
        microLessonVideoModel.getMokuai(fid, new ListCallBack<MicorLessonVideoBaseClassItem>() {
            @Override
            public void onSuccess(List<MicorLessonVideoBaseClassItem> datas) {
                baseClass.setMokuai("" + datas.get(0).getId());
                // 加载资源分类
                loadMicroDataType();
            }

            @Override
            public void onFail(Exception e) {
            }
        });
    }

    /**
     * 加载资源分类
     */
    private void loadMicroDataType() {
        microLessonVideoModel.getDataType(new ListCallBack<MicroDataTypeItem>() {
            @Override
            public void onSuccess(List<MicroDataTypeItem> datas) {
                baseClass.setDataType(datas.get(0).getType());
                // 保存基础分类
                MyApplication.getInstance().setMicroLessonVideoBaseClass(baseClass);
                // 初始化用户信息
                activity.initUser();
            }

            @Override
            public void onFail(Exception e) {
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
                // 保存用户信息
                MyApplication.getInstance().setUser(user);
                // 发送登录成功事件
                postEvent(Consts.EventType.EVENT_LOGIN_SUCCESS, null);
                // 启动主界面
                activity.startActivity(MainActivity.class);
                activity.finish();
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }
}
