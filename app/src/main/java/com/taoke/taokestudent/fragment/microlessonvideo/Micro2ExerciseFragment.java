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
import com.taoke.taokestudent.activity.microlessonvideo.Micro2ExerciseChapterActivity;
import com.taoke.taokestudent.activity.microlessonvideo.Micro3LessonVideoDetailActivity;
import com.taoke.taokestudent.adapter.MicroLessonVideoListAdapter;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.entity.MicroBaseClassRecord;
import com.taoke.taokestudent.entity.MicroLessonListResponseData;
import com.taoke.taokestudent.entity.MicroLessonVideoBaseClass;
import com.taoke.taokestudent.entity.MicroLessonVideoListItem;
import com.taoke.taokestudent.presenter.microlessonvideo.Micro2ExerciseFragmentPresenter;
import com.taoke.taokestudent.presenter.microlessonvideo.Micro3LessonVideoFragmentPresenter;
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
 * 微课视频-微课视频-习题微课
 */
public class Micro2ExerciseFragment extends Micro2LessonVideoItemBaseFragment {
    /* 习题微课列表 */
    @BindView(R.id.lv_micro_exercise_list)
    ListView lvMicroExerciseList;
    /* 空数据提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;
    /* 加载更多 */
    @BindView(R.id.ll_load_more)
    LinearLayout llLoadMore;

    /* 数据源 */
    private List<MicroLessonVideoListItem> list;
    /* Adapter */
    private MicroLessonVideoListAdapter adapter;

    /* 当前页或要显示的页 */
    private int currentPage = 1;
    /* 总页数 */
    private int totalPage = 0;
    /* 主导器 */
    private Micro2ExerciseFragmentPresenter presenter;
    /* 基础分类 */
    private MicroLessonVideoBaseClass baseClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//订阅
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_micro_2_exercise);
        presenter = new Micro2ExerciseFragmentPresenter(this);
        // 初始化
        init();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        // 初始化基础分类
        baseClass = MicroBaseClassRecord.getMicroLessonVideoBaseClass(Consts.MicroBaseClassType.EXERCISE_LESSON);
        if (baseClass == null) {
            baseClass = new MicroLessonVideoBaseClass();
            baseClass.copy(MyApplication.getInstance().getMicroLessonVideoBaseClass());
        }
        // 分类为微课视频
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
        adapter = new MicroLessonVideoListAdapter(getActivity(), list);
        lvMicroExerciseList.setAdapter(adapter);
    }

    /**
     * 刷新数据
     */
    private void refreshData(int page) {
        presenter.loadMicroExerciseList(baseClass, page);
        // 保存基础分类
        MicroBaseClassRecord.saveMicroLessonVideoBaseClass(Consts.MicroBaseClassType.EXERCISE_LESSON, baseClass);
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
            item.setClanum("共" + item.getClanum() + "题");
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

    @Override
    public MicroLessonVideoBaseClass getMicroLessonVideoBaseClass() {
        return baseClass;
    }

    @Override
    public void setMicroLessonVideoBaseClass(MicroLessonVideoBaseClass baseClass) {
        this.baseClass = baseClass;
        // 分类为微课视频
        this.baseClass.setDataType(Consts.MICRO_DATA_TYPE_EXERCISE);
        // 刷新数据
        refreshData(1);
    }

    /**
     * 选择课程，进入详情
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.lv_micro_exercise_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), Micro2ExerciseChapterActivity.class);
        intent.putExtra(Consts.IntentExtraKey.LESSON_ID, list.get(position).getId());
        intent.putExtra(Consts.IntentExtraKey.LESSON_NAME, list.get(position).getName());
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
            case Consts.EventType.EVENT_BUY_LESSON_SUCCESS: // 购买课程成功
                // 刷新数据
                String tid = (String) dataEvent.getData();
                if (!TextUtils.isEmpty(tid)) {
                    for (MicroLessonVideoListItem item : list) {
                        if (tid.equals(item.getId())) {
                            item.setPayly(Consts.MicroPayType.PAY);
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);//解除订阅
        super.onDestroy();
    }
}
