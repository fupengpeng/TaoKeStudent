package com.taoke.taokestudent.fragment.microlessonvideo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.microlessonvideo.Micro3LessonVideoDetailActivity;
import com.taoke.taokestudent.activity.personalcenter.LoginActivity;
import com.taoke.taokestudent.adapter.MicroDetailListAdapter;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.MicroLessonDetailListItem;
import com.taoke.taokestudent.entity.MicroLessonDetailListResponseData;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.fragment.BaseFragment;
import com.taoke.taokestudent.presenter.microlessonvideo.Micro3DetailChargeFragmentPresenter;
import com.taoke.taokestudent.presenter.microlessonvideo.Micro3DetailFreeFragmentPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 微课视频-微课视频-微课视频 详情-收费课程
 */
public class Micro3DetailChargeFragment extends BaseFragment {

    /* 详情列表 */
    @BindView(R.id.lv_micro_3_detail_list)
    ListView lvMicro3DetailChargeList;
    /* 加载更多 */
    @BindView(R.id.ll_load_more)
    LinearLayout llLoadMore;
    /* 空时提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;

    /* 数据源 */
    private List<MicroLessonDetailListItem> list;
    /* Adapter */
    private MicroDetailListAdapter adapter;

    /* 当前页或要显示的页 */
    private int currentPage = 1;
    /* 总页数 */
    private int totalPage = 0;
    /* 主导器 */
    private Micro3DetailChargeFragmentPresenter presenter;
    /* 详情界面 */
    private Micro3LessonVideoDetailActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载界面
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_micro_3_detail_list);
        presenter = new Micro3DetailChargeFragmentPresenter(this);
        // 初始化
        init();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        activity = (Micro3LessonVideoDetailActivity) getActivity();
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
        adapter = new MicroDetailListAdapter(getActivity(), list);
        lvMicro3DetailChargeList.setAdapter(adapter);
    }

    /**
     * 刷新数据
     */
    private void refreshData(int page) {
        presenter.loadMicroDetailChargeList(activity.getMicroLessonVideoListItem().getId(), page);
    }

    /**
     * 加载更多数据
     */
    @OnClick(R.id.ll_load_more)
    public void loadMoreDatas() {
        // 判断是否还有更多数据
        if (currentPage == totalPage) {
            InfoUtils.showInfo(getActivity(), getString(R.string.no_more_data));
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

        totalPage = Integer.parseInt(data.getTotalPage());
        // 显示或隐藏加载更多
        llLoadMore.setVisibility(currentPage == totalPage ? View.GONE : View.VISIBLE);

        if (currentPage == 1) {
            list.clear();
        }

        list.addAll(data.getList());
        adapter.notifyDataSetChanged();
    }

    /**
     * 播放
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.lv_micro_3_detail_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 验证用户是否登录
        User user = MyApplication.getInstance().getUser();
        if (user == null) {
            startActivity(LoginActivity.class);
            return;
        }

        // 验证是否购买
        if (Consts.MicroPayType.UNPAY.equals(activity.getMicroLessonVideoListItem().getPayly())
                && !MyApplication.getInstance().getUser().isFree()) {
            InfoUtils.showInfo(activity, "请购买课程后观看");
            return;
        }

        // 获取播放地址
        presenter.loadDetailChargeLessonVideoURL(user.getUid(),
                activity.getMicroLessonVideoListItem().getId(),
                list.get(position).getId(),
                list.get(position).getName());
    }

    /**
     * 设置视频播放地址数据
     *
     * @param name  视频名
     * @param datas 视频播放地址数据
     */
    public void setVideoUrlData(String name, List<String> datas) {
        activity.play(name, datas);
    }

}
