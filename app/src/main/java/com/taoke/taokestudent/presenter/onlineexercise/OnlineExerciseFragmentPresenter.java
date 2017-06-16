package com.taoke.taokestudent.presenter.onlineexercise;

import android.os.AsyncTask;

import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClass;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClassItem;
import com.taoke.taokestudent.fragment.onlineexercise.OnlineExerciseFragment;
import com.taoke.taokestudent.model.OnlineExerciseModel;
import com.taoke.taokestudent.presenter.BaseFragmentPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.List;

/**
 * 在线做题Fragment主导器
 */
public class OnlineExerciseFragmentPresenter extends BaseFragmentPresenter {
    /* 在线做题Fragment */
    private OnlineExerciseFragment fragment;
    /* 在线做题业务 */
    private OnlineExerciseModel onlineExerciseModel;

    /* 在线做题分类 */
    private OnlineExerciseBaseClass baseClass;

    public OnlineExerciseFragmentPresenter(OnlineExerciseFragment fragment) {
        super(fragment);
        this.fragment = fragment;
        this.onlineExerciseModel = new OnlineExerciseModel();
    }

    /**
     * 加载单元列表数据
     *
     * @param fid 父级ID
     */
    public void loadChapterListDatas(String fid) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);

        onlineExerciseModel.getMokuai(fid, new ListCallBack<OnlineExerciseBaseClassItem>() {
            @Override
            public void onSuccess(List<OnlineExerciseBaseClassItem> datas) {
                // 关闭等待对话框
                closeWaitDialog();
                // 设置数据
                fragment.setUnitListDatas(datas);
            }

            @Override
            public void onFail(Exception e) {
                // 关闭等待对话框
                closeWaitDialog();
                // 设置数据
                fragment.setUnitListDatas(null);
            }
        });
    }

    /**
     * 加载分类数据
     */
    public void loadClass() {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);

        // 加载分类数据
        new AsyncTask<String, String, OnlineExerciseBaseClass>() {
            @Override
            protected OnlineExerciseBaseClass doInBackground(String... params) {
                // 查询本地数据库
                List<OnlineExerciseBaseClass> baseClassRecords = OnlineExerciseBaseClass.listAll(OnlineExerciseBaseClass.class);

                if (baseClassRecords == null || baseClassRecords.size() == 0) {
                    return null;
                } else {
                    return baseClassRecords.get(0);
                }
            }

            @Override
            protected void onPostExecute(OnlineExerciseBaseClass onlineExerciseBaseClass) {
                if (onlineExerciseBaseClass == null) {
                    // 加载学段分类
                    baseClass = new OnlineExerciseBaseClass();
                    loadXueduanClass();
                } else {
                    // 关闭等待对话框
                    closeWaitDialog();
                    // 设置数据
                    fragment.setClassData(onlineExerciseBaseClass);
                }
            }
        }.execute();
    }

    /**
     * 加载学段分类
     */
    private void loadXueduanClass() {
        // 加载学段分类
        onlineExerciseModel.getBasicClass(Consts.BASE_CLASS_XUEDUAN_FID, Consts.BaseClassLevel.XDLV,
                new ListCallBack<OnlineExerciseBaseClassItem>() {
                    @Override
                    public void onSuccess(List<OnlineExerciseBaseClassItem> datas) {
                        // 设置数据
                        baseClass.setXueduan("" + datas.get(0).getId());
                        // 加载年级分类
                        loadNianjiClass(baseClass.getXueduan());
                    }

                    @Override
                    public void onFail(Exception e) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 显示错误信息
                        InfoUtils.showInfo(fragment.getActivity(), e.getMessage());
                    }
                });
    }

    /**
     * 加载年级分类
     */
    private void loadNianjiClass(String fid) {
        // 加载年级分类
        onlineExerciseModel.getBasicClass(fid, Consts.BaseClassLevel.NJLV,
                new ListCallBack<OnlineExerciseBaseClassItem>() {
                    @Override
                    public void onSuccess(List<OnlineExerciseBaseClassItem> datas) {
                        // 设置数据
                        baseClass.setNianji("" + datas.get(0).getId());
                        // 加载学科分类
                        loadXuekeClass(baseClass.getNianji());
                    }

                    @Override
                    public void onFail(Exception e) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 显示错误信息
                        InfoUtils.showInfo(fragment.getActivity(), e.getMessage());
                    }
                });
    }

    /**
     * 加载学科分类
     */
    private void loadXuekeClass(String fid) {
        // 加载学科分类
        onlineExerciseModel.getBasicClass(fid, Consts.BaseClassLevel.XKLV,
                new ListCallBack<OnlineExerciseBaseClassItem>() {
                    @Override
                    public void onSuccess(List<OnlineExerciseBaseClassItem> datas) {
                        // 设置数据
                        baseClass.setKemu("" + datas.get(0).getId());
                        // 加载版本分类
                        loadBanbenClass(baseClass.getKemu());
                    }

                    @Override
                    public void onFail(Exception e) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 显示错误信息
                        InfoUtils.showInfo(fragment.getActivity(), e.getMessage());
                    }
                });
    }

    /**
     * 加载版本分类
     */
    private void loadBanbenClass(String fid) {
        //  加载版本分类
        onlineExerciseModel.getBasicClass(fid, Consts.BaseClassLevel.BBLV,
                new ListCallBack<OnlineExerciseBaseClassItem>() {
                    @Override
                    public void onSuccess(List<OnlineExerciseBaseClassItem> datas) {
                        // 设置数据
                        baseClass.setBanben("" + datas.get(0).getId());
                        // 加载模块分类
                        loadMokuaiClass(baseClass.getBanben());
                    }

                    @Override
                    public void onFail(Exception e) {
                        // 关闭等待对话框
                        closeWaitDialog();
                        // 显示错误信息
                        InfoUtils.showInfo(fragment.getActivity(), e.getMessage());
                    }
                });
    }

    /**
     * 加载模块分类
     */
    private void loadMokuaiClass(String fid) {
        //  加载模块分类
        onlineExerciseModel.getMokuai(fid, new ListCallBack<OnlineExerciseBaseClassItem>() {
            @Override
            public void onSuccess(List<OnlineExerciseBaseClassItem> datas) {
                // 设置数据
                baseClass.setMokuai("" + datas.get(0).getId());
                fragment.setClassData(baseClass);
            }

            @Override
            public void onFail(Exception e) {
                // 关闭等待对话框
                closeWaitDialog();
                // 显示错误信息
                InfoUtils.showInfo(fragment.getActivity(), e.getMessage());
            }
        });
    }


}
