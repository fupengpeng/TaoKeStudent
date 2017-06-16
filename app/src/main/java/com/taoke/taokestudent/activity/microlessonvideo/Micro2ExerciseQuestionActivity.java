package com.taoke.taokestudent.activity.microlessonvideo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.MicroDetailListAdapter;
import com.taoke.taokestudent.adapter.MicroExerciseQuestionListAdapter;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.MicroExerciseQuestionListItem;
import com.taoke.taokestudent.entity.MicroExerciseQuestionListResponseData;
import com.taoke.taokestudent.presenter.microlessonvideo.Micro2ExerciseQuestionActivityPresenter;
import com.taoke.taokestudent.presenter.microlessonvideo.Micro3DetailFreeFragmentPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 习题微课习题界面
 */
public class Micro2ExerciseQuestionActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 习题列表 */
    @BindView(R.id.lv_micro_2_question_list)
    ListView lvMicro2QuestionList;
    /* 加载更多 */
    @BindView(R.id.ll_load_more)
    LinearLayout llLoadMore;
    /* 空时提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;

    /* 数据源 */
    private List<MicroExerciseQuestionListItem> list;
    /* Adapter */
    private MicroExerciseQuestionListAdapter adapter;

    /* 当前页或要显示的页 */
    private int currentPage = 1;
    /* 总页数 */
    private int totalPage = 0;
    /* 主导器 */
    private Micro2ExerciseQuestionActivityPresenter presenter;

    /* 章节id */
    private String lessonId;
    /* 章节名 */
    private String lessonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_micro2_exercise_question);
        presenter = new Micro2ExerciseQuestionActivityPresenter(this);
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 接收参数
        lessonId = getIntent().getStringExtra(Consts.IntentExtraKey.LESSON_ID);
        lessonName = getIntent().getStringExtra(Consts.IntentExtraKey.LESSON_NAME);
        tvTitleText.setText(lessonName);
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
        adapter = new MicroExerciseQuestionListAdapter(this, list);
        lvMicro2QuestionList.setAdapter(adapter);
    }

    /**
     * 刷新数据
     */
    private void refreshData(int page) {
        presenter.loadExerciseQuestionList(lessonId, page);
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
    public void setData(MicroExerciseQuestionListResponseData data) {
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
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back(){
        finish();
    }
}
