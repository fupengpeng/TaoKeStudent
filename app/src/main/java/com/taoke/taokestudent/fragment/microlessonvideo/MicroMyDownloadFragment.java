package com.taoke.taokestudent.fragment.microlessonvideo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.MainActivity;
import com.taoke.taokestudent.activity.microlessonvideo.MicroMyDownloadVideoListActivity;
import com.taoke.taokestudent.adapter.MyDownloadListAdapter;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.MicroLessonDetailListItem;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.fragment.BaseFragment;
import com.taoke.taokestudent.presenter.microlessonvideo.MicroMyDownloadFragmentPresenter;
import com.taoke.taokestudent.service.MicroDownloadService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 微课视频-我的下载
 */
public class MicroMyDownloadFragment extends BaseFragment {
    /* 下载列表 */
    @BindView(R.id.lv_my_download_list)
    ListView lvMyDownloadList;
    /* 空时提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;

    /* 数据源 */
    private List<String> list;
    /* Adapter */
    private MyDownloadListAdapter adapter;

    /* 主导器 */
    private MicroMyDownloadFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_micro_my_download);
        presenter = new MicroMyDownloadFragmentPresenter(this);
        // 绑定数据
        bindData();
        // 加载数据
        presenter.loadFolderListDatas();

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // 加载数据
            presenter.loadFolderListDatas();
        }
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        list = new ArrayList<>();
        adapter = new MyDownloadListAdapter(getActivity(), list);
        lvMyDownloadList.setAdapter(adapter);
    }

    /**
     * 设置数据
     *
     * @param datas
     */
    public void setData(List<String> datas) {
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
     * 打开侧滑菜单
     */
    @OnClick(R.id.iv_menu)
    public void openLeftMenu() {
        ((MainActivity) getActivity()).openLeftMenu();
    }

    /**
     * 打开文件夹
     */
    @OnItemClick(R.id.lv_my_download_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), MicroMyDownloadVideoListActivity.class);
        intent.putExtra(Consts.IntentExtraKey.DOWNLOAD_FOLDER, list.get(position));
        startActivity(intent);
    }
}
