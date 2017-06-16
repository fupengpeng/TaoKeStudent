package com.taoke.taokestudent.activity.microlessonvideo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.MicroExerciseChapterListAdapter;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.ExerciseChapter;
import com.taoke.taokestudent.entity.ExerciseClass;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.presenter.microlessonvideo.Micro2ExerciseChapterActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 习题微课章节界面
 */
public class Micro2ExerciseChapterActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 章节列表 */
    @BindView(R.id.lv_micro_exercise_chapter_list)
    ExpandableListView lvMicroExerciseChapterList;
    /* 空数据时的提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;

    /* 课程ID */
    private String lessonId;
    /* 课程名 */
    private String lessonName;

    /* 数据源 */
    private List<ExerciseChapter> list;
    /* Adatper */
    private MicroExerciseChapterListAdapter adapter;

    /* 主导器 */
    private Micro2ExerciseChapterActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_micro2_exercise_chapter);
        presenter = new Micro2ExerciseChapterActivityPresenter(this);
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 获取参数
        lessonId = getIntent().getStringExtra(Consts.IntentExtraKey.LESSON_ID);
        lessonName = getIntent().getStringExtra(Consts.IntentExtraKey.LESSON_NAME);
        // 标题
        tvTitleText.setText(lessonName);
        // 绑定数据
        bindData();
        // 设置监听器
        setListeners();
        // 请求数据
        presenter.loadExerciseChapterList(lessonId);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        // Child项点击监听器
        lvMicroExerciseChapterList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //
                ExerciseClass exerciseClass = list.get(groupPosition).getChildList().get(childPosition);

                // 判断选中章节是否免费
                boolean isFree = false;
                User user = MyApplication.getInstance().getUser();
                if (user != null && user.isFree()) {
                    isFree = true;
                }

                if (isFree || Consts.MicroExerciseChapterFree.FREE.equals(exerciseClass.getIs_free())) {
                    // 打开习题列表界面
                    Intent intent = new Intent(Micro2ExerciseChapterActivity.this, Micro2ExerciseQuestionActivity.class);
                    intent.putExtra(Consts.IntentExtraKey.LESSON_ID, exerciseClass.getId());
                    intent.putExtra(Consts.IntentExtraKey.LESSON_NAME, exerciseClass.getName());
                    startActivity(intent);
                } else {
                    InfoUtils.showInfo(Micro2ExerciseChapterActivity.this, "请购买后操作");
                }

                return true;
            }
        });
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        list = new ArrayList<>();
        adapter = new MicroExerciseChapterListAdapter(this, list);
        lvMicroExerciseChapterList.setAdapter(adapter);
        //设置 属性 GroupIndicator 去掉默认向下的箭头
        lvMicroExerciseChapterList.setGroupIndicator(null);
    }

    /**
     * 设置数据
     */
    public void setData(List<ExerciseChapter> datas) {
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
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

}
