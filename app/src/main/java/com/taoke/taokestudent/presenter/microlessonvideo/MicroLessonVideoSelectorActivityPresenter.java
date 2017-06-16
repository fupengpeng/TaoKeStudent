package com.taoke.taokestudent.presenter.microlessonvideo;

import com.taoke.taokestudent.activity.microlessonvideo.MicroLessonVideoSelectorActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.entity.MicorLessonVideoBaseClassItem;
import com.taoke.taokestudent.entity.MicroDataTypeItem;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.presenter.BaseActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 微课视频选择器主导器
 */
public class MicroLessonVideoSelectorActivityPresenter extends BaseActivityPresenter {
    /* 微课视频选择器界面 */
    private MicroLessonVideoSelectorActivity activity;
    /* 微课视频业务 */
    private MicroLessonVideoModel microLessonVideoModel;

    public MicroLessonVideoSelectorActivityPresenter(MicroLessonVideoSelectorActivity activity) {
        super(activity);
        this.activity = activity;
        microLessonVideoModel = new MicroLessonVideoModel();
    }

    /**
     * 加载分类
     */
    public void loadDataType(){
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载基础分类
        microLessonVideoModel.getDataType(new ListCallBack<MicroDataTypeItem>() {
            @Override
            public void onSuccess(List<MicroDataTypeItem> datas) {
                // 关闭对话框
                closeWaitDialog();
                // 设置基础分类数据
                activity.setDataType(datas);
            }

            @Override
            public void onFail(Exception e) {
                // 关闭对话框
                closeWaitDialog();
                InfoUtils.showInfo(activity, e.getMessage());
            }
        });
    }

    /**
     * 加载模块
     *
     * @param fid   父级id
     * @param level 级别
     */
    public void loadMokuai(final String fid, final String level) {
        // 加载基础分类
        microLessonVideoModel.getMokuai(fid, new ListCallBack<MicorLessonVideoBaseClassItem>() {
            @Override
            public void onSuccess(List<MicorLessonVideoBaseClassItem> datas) {
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
                MicorLessonVideoBaseClassItem item = new MicorLessonVideoBaseClassItem();
                item.setId(0L);
                item.setFid(fid);
                item.setName("全部");
                List<MicorLessonVideoBaseClassItem> datas = new ArrayList<MicorLessonVideoBaseClassItem>();
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
        microLessonVideoModel.getBasicClass(fid, level, new ListCallBack<MicorLessonVideoBaseClassItem>() {
            @Override
            public void onSuccess(List<MicorLessonVideoBaseClassItem> datas) {
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
