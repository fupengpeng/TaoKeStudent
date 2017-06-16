package com.taoke.taokestudent.activity.microlessonvideo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.MicroDetailListAdapter;
import com.taoke.taokestudent.adapter.MicroDownloadListAdapter;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.entity.MicroDownloadRecord;
import com.taoke.taokestudent.entity.MicroLessonDetailListItem;
import com.taoke.taokestudent.entity.MicroLessonDetailListResponseData;
import com.taoke.taokestudent.entity.MicroLessonVideoListItem;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.presenter.microlessonvideo.MicroDownloadActivityPresenter;
import com.taoke.taokestudent.service.MicroDownloadService;
import com.taoke.taokestudent.util.InfoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 微课视频下载
 */
public class MicroDownloadActivity extends BaseActivity {
    /* 下载列表 */
    @BindView(R.id.lv_micro_down_list)
    ListView lvMicroDownList;
    /* 加载更多 */
    @BindView(R.id.ll_load_more)
    LinearLayout llLoadMore;
    /* 空时提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;

    /* 数据源 */
    private List<MicroLessonDetailListItem> list;
    /* Adapter */
    private MicroDownloadListAdapter adapter;

    /* 当前页或要显示的页 */
    private int currentPage = 1;
    /* 总页数 */
    private int totalPage = 0;

    /* 课程 */
    private MicroLessonVideoListItem item;

    /* 主导器 */
    private MicroDownloadActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_micro_download);
        presenter = new MicroDownloadActivityPresenter(this);
        // 订阅
        EventBus.getDefault().register(this);
        // 获取参数
        item = getIntent().getParcelableExtra(Consts.IntentExtraKey.MICRO_DETAIL);
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 绑定数据
        bindData();
        // 刷新数据
        refreshData(1);
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        list = new ArrayList<>();
        adapter = new MicroDownloadListAdapter(this, list);
        lvMicroDownList.setAdapter(adapter);
    }

    /**
     * 刷新数据
     */
    private void refreshData(int page) {
        // 判读是否购买
        boolean isBuyed = false;
        User user = MyApplication.getInstance().getUser();
        if (Consts.MicroPayType.PAY.equals(item.getPayly())
                || (user != null && user.isFree())) {
            isBuyed = true;
        }

        presenter.loadDownloadListDatas(item.getId(), isBuyed, page);
    }

    /**
     * 加载更多数据
     */
    @OnClick(R.id.ll_load_more)
    public void loadMoreDatas() {
        // 判断是否还有更多数据
        if (currentPage == totalPage) {
            InfoUtils.showInfo(this, getString(R.string.no_more_data));
            return;
        }

        currentPage++;
        refreshData(currentPage);
    }

    /**
     * 设置数据
     */
    public void setData(MicroLessonDetailListResponseData data) {
        // 判断是否存在数据
        if (data == null || data.getList() == null || data.getList().size() == 0) {
            // 显示提示
            tvDataEmpty.setVisibility(View.VISIBLE);
            // 清空现有数据
            list.clear();
            adapter.notifyDataSetChanged();
            // 隐藏加载更多
            llLoadMore.setVisibility(View.GONE);
            return;
        }

        // 隐藏提示
        tvDataEmpty.setVisibility(View.GONE);

        // 显示或隐藏加载更多
        totalPage = Integer.parseInt(data.getTotalPage());
        llLoadMore.setVisibility(currentPage == totalPage ? View.GONE : View.VISIBLE);

        // 刷新数据
        if (currentPage == 1) {
            list.clear();
        }
        list.addAll(data.getList());
        adapter.notifyDataSetChanged();
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

    /**
     * 下载
     */
    @OnItemClick(R.id.lv_micro_down_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MicroLessonDetailListItem listItem = list.get(position);
        // 判断是否可以下载
        if (!TextUtils.isEmpty(listItem.getType())
                && !Consts.DownloadType.STOP.equals(listItem.getType())
                && !Consts.DownloadType.ERROR.equals(listItem.getType())
                && !Consts.DownloadType.FINISH.equals(listItem.getType())) {
            return;
        }

        listItem.setParentName(item.getName());
        // 等待下载
        listItem.setType(Consts.DownloadType.WAIT);
        adapter.notifyDataSetChanged();
        // 判读是否有播放地址
        List<String> urlArr = listItem.getUrlArr();
        if (urlArr != null && urlArr.size() > 0) {
            Intent intent = new Intent(this, MicroDownloadService.class);
            intent.putExtra(Consts.IntentExtraKey.MICRO_DOWNLOAD, listItem);
            startService(intent);
        } else {
            // 获取播放地址
            User user = MyApplication.getInstance().getUser();
            if (user == null) {
                // 等待失败
                listItem.setType(Consts.DownloadType.ERROR);
                adapter.notifyDataSetChanged();
                return;
            }
            presenter.loadDetailChargeLessonVideoURL(user.getUid(), item.getId(), listItem);
        }
    }

    /**
     * 设置视频URL
     *
     * @param listItem
     */
    public void setVideoUrls(MicroLessonDetailListItem listItem) {
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


    @Override
    protected void onDestroy() {
        // 取消订阅
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
