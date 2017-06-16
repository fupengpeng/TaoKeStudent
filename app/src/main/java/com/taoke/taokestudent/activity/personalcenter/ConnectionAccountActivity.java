package com.taoke.taokestudent.activity.personalcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.ConnectionAccountListAdapter;
import com.taoke.taokestudent.adapter.MyDownloadVideoListAdapter;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.entity.ConnectionAccount;
import com.taoke.taokestudent.presenter.personalcenter.ConnectionAccountActivityPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关联账号界面
 */
public class ConnectionAccountActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 绑定 */
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    /* 关联账号列表 */
    @BindView(R.id.lv_connection_account_list)
    ListView lvConnectionAccountList;
    /* 没有数据时的提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;

    /* 数据源 */
    private List<ConnectionAccount> list;
    /* Adapter */
    private ConnectionAccountListAdapter adapter;

    /* 主导器 */
    private ConnectionAccountActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_connection_account);
        presenter = new ConnectionAccountActivityPresenter(this);
        // 订阅
        EventBus.getDefault().register(this);
        // 初始化
        init();
        // 标题
        tvTitleText.setText(getString(R.string.connection_account));
        tvTitleRight.setText(getString(R.string.bind_account));
        tvTitleRight.setVisibility(View.VISIBLE);

        // 加载数据
        presenter.loadConnectionAccountList();
    }

    /**
     * 初始化
     */
    private void init() {
        // 绑定数据
        bindView();
    }

    /**
     * 绑定数据
     */
    private void bindView() {
        list = new ArrayList<>();
        adapter = new ConnectionAccountListAdapter(this, list);
        lvConnectionAccountList.setAdapter(adapter);
    }

    /**
     * 设置数据
     *
     * @param datas
     */
    public void setData(List<ConnectionAccount> datas) {
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
     * 绑定账号
     */
    @OnClick(R.id.tv_title_right)
    public void bindAccount() {
        startActivity(BindAccountActivity.class);
    }

    /**
     * 接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataEvent(DataEvent dataEvent) {
        switch (dataEvent.getType()) {
            case Consts.EventType.EVENT_UPDATE_CONNECTION_ACCOUNT: // 更新关联账号
                // 加载数据
                presenter.loadConnectionAccountList();
                break;
        }
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
        // 解除订阅
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
