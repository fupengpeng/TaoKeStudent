package com.taoke.taokestudent.activity.microlessonvideo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.MyDownloadVideoListAdapter;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.entity.MicroLessonDetailListItem;
import com.taoke.taokestudent.entity.MicroLessonVideoListItem;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.presenter.microlessonvideo.MicroMyDownloadVideoListActivityPresenter;
import com.taoke.taokestudent.service.MicroDownloadService;
import com.taoke.taokestudent.util.DialogUtils;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.LogUtils;
import com.taoke.taokestudent.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 我的下载视频列表界面
 */
public class MicroMyDownloadVideoListActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 编辑 */
    @BindView(R.id.tv_title_edit)
    TextView tvTitleEdit;
    /* 视频列表 */
    @BindView(R.id.lv_my_download_video_list)
    ListView lvMyDownloadVideoList;
    /* 没有数据时的提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;
    /* 编辑栏 */
    @BindView(R.id.ll_edit_bar)
    LinearLayout llEditBar;
    /* 全选 */
    @BindView(R.id.tv_check_all)
    TextView tvCheckAll;

    /* 外层文件夹名 */
    private String folderName;

    /* 数据源 */
    private List<MicroLessonDetailListItem> list;
    /* Adapter */
    private MyDownloadVideoListAdapter adapter;
    /* 编辑状态 */
    private boolean isEdit = false;

    /* 主导器 */
    private MicroMyDownloadVideoListActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_micro_my_download_video_list);
        presenter = new MicroMyDownloadVideoListActivityPresenter(this);
        // 订阅
        EventBus.getDefault().register(this);
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 获取参数
        folderName = getIntent().getStringExtra(Consts.IntentExtraKey.DOWNLOAD_FOLDER);
        tvTitleText.setText(folderName);
        // 绑定数据
        bindView();
        // 加载数据
        presenter.loadVideoListDatas(folderName);
    }

    /**
     * 绑定数据
     */
    private void bindView() {
        list = new ArrayList<>();
        adapter = new MyDownloadVideoListAdapter(this, list);
        adapter.setEdit(isEdit);
        lvMyDownloadVideoList.setAdapter(adapter);
    }

    /**
     * 设置数据
     *
     * @param datas
     */
    public void setData(List<MicroLessonDetailListItem> datas) {
        // 判断是否存在数据
        if (datas == null || datas.size() == 0) {
            // 显示提示
            tvDataEmpty.setVisibility(View.VISIBLE);
            // 清空现有数据
            list.clear();
            adapter.notifyDataSetChanged();
            return;
        }

        // 隐藏提示
        tvDataEmpty.setVisibility(View.GONE);

        // 刷新数据
        list.clear();
        list.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    /**
     * 播放或下载
     */
    @OnItemClick(R.id.lv_my_download_video_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MicroLessonDetailListItem listItem = list.get(position);
        // 判断是否可以播放视频
        if (play(listItem)) {
            return;
        } else {
            // 下载视频
            download(listItem);
        }
    }

    /**
     * 播放视频
     */
    private boolean play(final MicroLessonDetailListItem listItem) {
        // 判断是否下载完成
        if (!Consts.DownloadType.FINISH.equals(listItem.getType())) {
            return false;
        }
        // 播放地址列表
        ArrayList<Uri> uriList = new ArrayList<>();
        //判断视频文件是否存在
        File dirPath = new File(Consts.DOWNLOAD_BASE_PATH + listItem.getParentName() + "/" + listItem.getName());
        if (dirPath.exists()) {
            File[] files = dirPath.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().toUpperCase().endsWith(".MP4")) {
                        return true;
                    }
                    return false;
                }
            });
            for (File file : files) {
                uriList.add(Uri.fromFile(file));
            }
            if (uriList.size() > 0) {
                Intent intent = new Intent(this, VideoPlayerActivity.class);
                intent.putExtra(Consts.IntentExtraKey.VIDEO_NAME, listItem.getName());
                intent.putParcelableArrayListExtra(Consts.IntentExtraKey.VIDEO_URI, uriList);
                startActivity(intent);
                return true;
            }
        }
        // 显示提示
        new DialogUtils(this).confirm("提示", "播放文件不存在，是否重新下载？",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        download(listItem);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return true;
    }

    /**
     * 下载视频
     *
     * @return
     */
    private void download(MicroLessonDetailListItem listItem) {
        // 判断是否可以下载
        if (!TextUtils.isEmpty(listItem.getType())
                && !Consts.DownloadType.STOP.equals(listItem.getType())
                && !Consts.DownloadType.ERROR.equals(listItem.getType())
                && !Consts.DownloadType.FINISH.equals(listItem.getType())) {
            return;
        }

        // 等待下载
        listItem.setType(Consts.DownloadType.WAIT);
        adapter.notifyDataSetChanged();
        // 下载
        Intent intent = new Intent(this, MicroDownloadService.class);
        intent.putExtra(Consts.IntentExtraKey.MICRO_DOWNLOAD, listItem);
        startService(intent);
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

    /**
     * 编辑
     */
    @OnClick(R.id.tv_title_edit)
    public void edit() {
        isEdit = !isEdit;
        if (isEdit) {
            tvTitleEdit.setText("完成");
            llEditBar.setVisibility(View.VISIBLE);
        } else {
            tvTitleEdit.setText("编辑");
            llEditBar.setVisibility(View.GONE);
            tvCheckAll.setText("全选");
        }

        adapter.setEdit(isEdit);
        adapter.notifyDataSetChanged();
    }

    /**
     * 全选
     */
    @OnClick(R.id.tv_check_all)
    public void checkAll() {
        if ("全选".equals(tvCheckAll.getText().toString())) {
            checkItem(true);
            tvCheckAll.setText("全不选");
        } else {
            checkItem(false);
            tvCheckAll.setText("全选");
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除选中项
     */
    @OnClick(R.id.tv_check_delete)
    public void deleteChecked() {
        // 获取选中项集合
        final List<MicroLessonDetailListItem> deleteList = new ArrayList<>();
        for (MicroLessonDetailListItem item : list) {
            if (item.isSelected()) {
                deleteList.add(item);
            }
        }
        // 判断是否有选中项
        if (deleteList.size() > 0) {
            new DialogUtils(this).confirm("提示", "是否删除选中项",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.deleteCheckedItem(deleteList);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
    }

    /**
     * 选中数据项
     *
     * @param isCheck
     */
    private void checkItem(boolean isCheck) {
        for (MicroLessonDetailListItem item : list) {
            item.setSelected(isCheck);
        }
    }

    /**
     * 接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(DataEvent dataEvent) {
        switch (dataEvent.getType()) {
            case Consts.EventType.EVENT_DOWNLOAD_START: // 开始下载
                ArrayList<Integer> data = (ArrayList<Integer>) dataEvent.getData();
                int what = data.get(0);
                if (what % 10 != 0) {
                    break;
                }
                // 遍历判断
                for (MicroLessonDetailListItem item : list) {
                    if (item.getId().equals("" + what / 10)) {
                        item.setType(Consts.DownloadType.START);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            case Consts.EventType.EVENT_DOWNLOAD_PROGRESS: // 开始进度
                data = (ArrayList<Integer>) dataEvent.getData();
                what = data.get(0);
                if (what % 10 != 0) {
                    break;
                }
                // 遍历判断
                for (MicroLessonDetailListItem item : list) {
                    if (item.getId().equals("" + what / 10)) {
                        item.setType("完成" + data.get(1) + "%");
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            case Consts.EventType.EVENT_DOWNLOAD_FINISH: // 下载完成
                what = (Integer) dataEvent.getData();
                if (what % 10 != 0) {
                    break;
                }
                // 遍历判断
                for (MicroLessonDetailListItem item : list) {
                    if (item.getId().equals("" + what / 10)) {
                        item.setType(Consts.DownloadType.FINISH);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            case Consts.EventType.EVENT_DOWNLOAD_ERROR: // 下载失败
                what = (Integer) dataEvent.getData();
                if (what % 10 != 0) {
                    break;
                }
                // 遍历判断
                for (MicroLessonDetailListItem item : list) {
                    if (item.getId().equals("" + what / 10)) {
                        item.setType(Consts.DownloadType.ERROR);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // 解除订阅
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
