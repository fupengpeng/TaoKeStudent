package com.taoke.taokestudent.fragment.microlessonvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.MainActivity;
import com.taoke.taokestudent.activity.microlessonvideo.Micro2ExerciseChapterActivity;
import com.taoke.taokestudent.activity.microlessonvideo.Micro3LessonVideoDetailActivity;
import com.taoke.taokestudent.activity.microlessonvideo.MicroLessonVideoSelectorActivity;
import com.taoke.taokestudent.adapter.MicroLessonVideoListAdapter;
import com.taoke.taokestudent.adapter.MicroMyLessonListAdapter;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.entity.MicroBaseClassRecord;
import com.taoke.taokestudent.entity.MicroLessonListResponseData;
import com.taoke.taokestudent.entity.MicroLessonVideoBaseClass;
import com.taoke.taokestudent.entity.MicroLessonVideoListItem;
import com.taoke.taokestudent.fragment.BaseFragment;
import com.taoke.taokestudent.presenter.microlessonvideo.Micro3LessonVideoFragmentPresenter;
import com.taoke.taokestudent.presenter.microlessonvideo.MicroMyLessonFragmentPresenter;
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
 * 微课视频-我的课程
 */
public class MicroMyLessonFragment extends BaseFragment {
    /* 我的课程列表 */
    @BindView(R.id.lv_micro_my_lesson_list)
    ListView lvMicroMyLessonList;
    /* 空数据提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;
    /* 加载更多 */
    @BindView(R.id.ll_load_more)
    LinearLayout llLoadMore;

    /* 数据源 */
    private List<MicroLessonVideoListItem> list;
    /* Adapter */
    private MicroMyLessonListAdapter adapter;

    /* 当前页或要显示的页 */
    private int currentPage = 1;
    /* 总页数 */
    private int totalPage = 0;
    /* 主导器 */
    private MicroMyLessonFragmentPresenter presenter;
    /* 基础分类 */
    private MicroLessonVideoBaseClass baseClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//订阅
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_micro_my_lesson);
        presenter = new MicroMyLessonFragmentPresenter(this);
        // 初始化
        init();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        // 初始化基础分类
        baseClass = MicroBaseClassRecord.getMicroLessonVideoBaseClass(Consts.MicroBaseClassType.MY_LESSON);
        if (baseClass == null) {
            baseClass = new MicroLessonVideoBaseClass();
            baseClass.copy(MyApplication.getInstance().getMicroLessonVideoBaseClass());
        }
        baseClass.setDataType(Consts.MICRO_DATA_TYPE_EXERCISE);
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
        adapter = new MicroMyLessonListAdapter(getActivity(), list);
        lvMicroMyLessonList.setAdapter(adapter);
    }

    /**
     * 刷新数据
     */
    private void refreshData(int page) {
        presenter.loadMicroMyLessonList(baseClass, page);
        // 保存基础分类
        MicroBaseClassRecord.saveMicroLessonVideoBaseClass(Consts.MicroBaseClassType.MY_LESSON, baseClass);
    }

    /**
     * 设置数据
     */
    public void setData(MicroLessonListResponseData data) {
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
        for (MicroLessonVideoListItem item : data.getList()) {
            item.setPrice("￥" + item.getPrice());
            if (Consts.MICRO_DATA_TYPE_EXERCISE.equals(item.getType())) {
                item.setClanum("共" + item.getClanum() + "题");
            } else {
                item.setClanum("共" + item.getClanum() + "讲");
            }
        }

        // 设置已购买
        for (MicroLessonVideoListItem item : data.getList()) {
            item.setPayly(Consts.MicroPayType.PAY);
        }

        list.addAll(data.getList());
        adapter.notifyDataSetChanged();
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
     * 选择课程，进入详情
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.lv_micro_my_lesson_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        if (Consts.MICRO_DATA_TYPE_EXERCISE.equals(list.get(position).getType())) { // 习题微课
            intent = new Intent(getActivity(), Micro2ExerciseChapterActivity.class);
            intent.putExtra(Consts.IntentExtraKey.LESSON_ID, list.get(position).getId());
            intent.putExtra(Consts.IntentExtraKey.LESSON_NAME, list.get(position).getName());
        } else { // 微课视频
            intent = new Intent(getActivity(), Micro3LessonVideoDetailActivity.class);
            intent.putExtra(Consts.IntentExtraKey.MICRO_DETAIL, list.get(position));
        }
        startActivity(intent);
    }

    /**
     * 接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataEvent(DataEvent dataEvent) {
        switch (dataEvent.getType()) {
            case Consts.EventType.EVENT_LOGIN_SUCCESS: // 登录成功
            case Consts.EventType.EVENT_EXIT_ACCOUNT: // 退出账号
                // 刷新数据
                refreshData(1);
                break;
        }
    }


    /**
     * 打开侧滑菜单
     */
    @OnClick(R.id.iv_menu)
    public void openLeftMenu() {
        ((MainActivity) getActivity()).openLeftMenu();
    }

    /**
     * 筛选课程
     */
    @OnClick(R.id.iv_selector)
    public void selectLesson() {
        // 启动筛选课程界面
        Intent intent = new Intent(getActivity(), MicroLessonVideoSelectorActivity.class);
        intent.putExtra(Consts.IntentExtraKey.MICRO_BASE_CLASS, baseClass);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == MainActivity.RESULT_OK) {
                baseClass = data.getParcelableExtra(Consts.IntentExtraKey.MICRO_BASE_CLASS);
                baseClass.setDataType(Consts.MICRO_DATA_TYPE_EXERCISE);
                refreshData(1);
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);//解除订阅
        super.onDestroy();
    }
}
