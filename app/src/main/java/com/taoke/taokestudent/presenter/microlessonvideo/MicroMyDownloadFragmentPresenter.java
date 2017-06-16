package com.taoke.taokestudent.presenter.microlessonvideo;

import android.os.AsyncTask;

import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.MicroDownloadRecord;
import com.taoke.taokestudent.fragment.microlessonvideo.MicroMyDownloadFragment;
import com.taoke.taokestudent.presenter.BaseFragmentPresenter;
import com.taoke.taokestudent.util.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的下载界面的主导器
 */
public class MicroMyDownloadFragmentPresenter extends BaseFragmentPresenter {
    /* 我的下载界面 */
    private MicroMyDownloadFragment fragment;

    public MicroMyDownloadFragmentPresenter(MicroMyDownloadFragment fragment) {
        super(fragment);
        this.fragment = fragment;
    }

    /**
     * 加载文件夹列表数据
     */
    public void loadFolderListDatas() {
        // 显示等待对话框
        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
        // 加载文件夹列表数据
        new AsyncTask<String, String, List<String>>() {
            @Override
            protected List<String> doInBackground(String... params) {
                // 加载文件夹列表
                List<MicroDownloadRecord> records = MicroDownloadRecord.find(MicroDownloadRecord.class,
                        null, null, "PARENT_NAME", "TIME DESC", null);

                List<String> list = new ArrayList<String>();
                for (MicroDownloadRecord record : records) {
                    list.add(record.getParentName());
                }

                return list;
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                // 关闭对话框
                closeWaitDialog();
                // 设置数据
                fragment.setData(strings);
            }
        }.execute();
    }
}
