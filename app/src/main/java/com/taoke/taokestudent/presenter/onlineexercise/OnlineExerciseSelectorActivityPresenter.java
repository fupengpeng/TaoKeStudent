package com.taoke.taokestudent.presenter.onlineexercise;

import com.taoke.taokestudent.activity.onlineexercise.OnlineExercsieSelectorActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClassItem;
import com.taoke.taokestudent.model.OnlineExerciseModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线做题筛选器主导器
 */
public class OnlineExerciseSelectorActivityPresenter extends BaseActivityPresenter {
    /* 在线做题筛选器界面 */
    private OnlineExercsieSelectorActivity activity;
    /* 在线做题业务 */
    private OnlineExerciseModel onlineExerciseModel;

    public OnlineExerciseSelectorActivityPresenter(OnlineExercsieSelectorActivity activity) {
        super(activity);
        this.activity = activity;
        onlineExerciseModel = new OnlineExerciseModel();
    }

    /**
     * 加载模块
     *
     * @param fid   父级id
     * @param level 级别
     */
    public void loadMokuai(final String fid, final String level) {
        // 加载基础分类
        onlineExerciseModel.getMokuai(fid, new ListCallBack<OnlineExerciseBaseClassItem>() {
            @Override
            public void onSuccess(List<OnlineExerciseBaseClassItem> datas) {
                if(Consts.BaseClassLevel.MKLV.equals(level)){
                    // 关闭对话框
                    closeWaitDialog();
                }
                // 设置基础分类数据
                activity.setBaseData(datas, level);
            }

            @Override
            public void onFail(Exception e) {
                // 关闭对话框
                closeWaitDialog();
                //InfoUtils.showInfo(activity, e.getMessage());
                OnlineExerciseBaseClassItem item = new OnlineExerciseBaseClassItem();
                item.setId(0L);
                item.setFid(fid);
                item.setName("全部");
                List<OnlineExerciseBaseClassItem> datas = new ArrayList<>();
                datas.add(item);
                // 设置基础分类数据
                activity.setBaseData(datas, level);
            }
        });
    }

    /**
     * 加载基础分类
     *
     * @param fid   父级id
     * @param level 级别
     */
    public void loadBaseClass(String fid, final String level) {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载基础分类
        onlineExerciseModel.getBasicClass(fid, level, new ListCallBack<OnlineExerciseBaseClassItem>() {
            @Override
            public void onSuccess(List<OnlineExerciseBaseClassItem> datas) {
                // 设置基础分类数据
                activity.setBaseData(datas, level);
            }

            @Override
            public void onFail(Exception e) {
                // 关闭对话框
                closeWaitDialog();
                InfoUtils.showInfo(activity, e.getMessage());
            }
        });
    }
}
