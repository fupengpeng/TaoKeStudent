package com.taoke.taokestudent.activity.onlineexercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.OnlineExerciseChapterListAdapter;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.OnlineExerciseChapter;
import com.taoke.taokestudent.entity.OnlineExerciseQuestion;
import com.taoke.taokestudent.presenter.onlineexercise.OnlineExerciseChapterActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 在线做题章节界面
 */
public class OnlineExerciseChapterActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 标题右侧操作 */
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    /* 章节列表 */
    @BindView(R.id.lv_online_chapter_list)
    ListView lvOnlineChapterList;
    /* 空时提示 */
    @BindView(R.id.tv_data_empty)
    TextView tvDataEmpty;

    /* 数据源 */
    private List<OnlineExerciseChapter> list;
    /* Adapter */
    private OnlineExerciseChapterListAdapter adapter;

    /* 题目数量 */
    private int questionNum;

    /* 主导器 */
    private OnlineExerciseChapterActivityPresenter presenter;

    /* 标识是否全选 */
    private boolean isCheckAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_online_exercise_chapter);
        presenter = new OnlineExerciseChapterActivityPresenter(this);
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 接收参数
        String unitId = getIntent().getStringExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_UNIT_ID);
        String unitName = getIntent().getStringExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_UNIT_NAME);
        questionNum = getIntent().getIntExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_QUESTION_NUM, 5);
        // 标题
        tvTitleText.setText(getString(R.string.online_exercise));
        tvTitleRight.setText("全选");
        tvTitleRight.setVisibility(View.VISIBLE);
        // 绑定数据
        bindData();
        // 加载章节列表数据
        presenter.loadChapterList(unitId);
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        list = new ArrayList<>();
        adapter = new OnlineExerciseChapterListAdapter(this, list);
        lvOnlineChapterList.setAdapter(adapter);
    }

    /**
     * 设置章节列表数据
     *
     * @param datas 章节列表数据
     */
    public void setChapterListDatas(List<OnlineExerciseChapter> datas) {
        // 清空现有数据
        list.clear();

        // 判断是否存在数据
        if (datas == null || datas.size() == 0) {
            // 显示提示
            tvDataEmpty.setVisibility(View.VISIBLE);
            // 刷新数据
            adapter.notifyDataSetChanged();
            return;
        }

        // 隐藏提示
        tvDataEmpty.setVisibility(View.GONE);

        // 刷新数据
        list.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    /**
     * 开始答题
     */
    @OnClick(R.id.tv_start_answer)
    public void startAnswer() {
        // 获取选中的知识点
        StringBuffer stringBuffer = new StringBuffer();
        for (OnlineExerciseChapter chapter : list) {
            if (chapter.getChildList() == null || chapter.getChildList().size() == 0) {
                continue;
            }
            for (OnlineExerciseChapter child : chapter.getChildList()) {
                if (child.isSelected()) {
                    stringBuffer.append(child.getId() + ",");
                }
            }
        }
        // 判断是否选中了知识点
        if (stringBuffer.length() == 0) {
            InfoUtils.showInfo(this, "您还没有选择知识点");
            return;
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        String fid = stringBuffer.toString();

        // 请求试题列表
        presenter.loadQuestionList(fid, questionNum);
    }

    /**
     * 设置试题列表数据
     *
     * @param datas 试题列表数据
     */
    public void setQuestionListDatas(ArrayList<OnlineExerciseQuestion> datas) {
        if (datas == null || datas.size() == 0) {
            InfoUtils.showInfo(this, "您选择的知识点没有题进行练习");
            return;
        }

        // 打开做题界面
        Intent intent = new Intent(this, OnlineExerciseTestActivity.class);
        intent.putParcelableArrayListExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_QUESTION_LIST, datas);
        startActivity(intent);

        //
        finish();
    }

    /**
     * 全选/全不选
     */
    @OnClick(R.id.tv_title_right)
    public void checkAllOrNot() {
        isCheckAll = !isCheckAll;
        if (isCheckAll) {
            tvTitleRight.setText("全不选");
            changeCheck();
        } else {
            tvTitleRight.setText("全选");
            changeCheck();
        }
    }

    /**
     * 变更选中状态
     */
    private void changeCheck() {
        for (OnlineExerciseChapter chapter : list) {
            if (chapter.getChildList() == null || chapter.getChildList().size() == 0) {
                continue;
            }
            for (OnlineExerciseChapter child : chapter.getChildList()) {
                child.setSelected(isCheckAll);
            }
        }
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
