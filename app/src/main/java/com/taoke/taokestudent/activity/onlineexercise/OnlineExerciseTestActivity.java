package com.taoke.taokestudent.activity.onlineexercise;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.OnlineExerciseQuestionChoiceListAdapter;
import com.taoke.taokestudent.adapter.QuesitonPagerAdapter;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.entity.OnlineExerciseQuestion;
import com.taoke.taokestudent.presenter.onlineexercise.OnlineExerciseTestActivityPresenter;
import com.taoke.taokestudent.util.DialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnPageChange;

/**
 * 在线做题答题界面
 */
public class OnlineExerciseTestActivity extends BaseActivity {
    /* 计时器 */
    @BindView(R.id.chronometer)
    Chronometer chronometer;
    /* 进度条 */
    @BindView(R.id.sb_text_progress)
    SeekBar sbTextProgress;
    /* 进度 */
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    /* 试题页 */
    @BindView(R.id.vp_question_list)
    ViewPager vpQuestionList;

    /* 题目选择区域 */
    @BindView(R.id.ll_choice_area)
    LinearLayout llChoiceArea;
    /* 题目选择列表 */
    @BindView(R.id.gv_choice_question)
    GridView gvChoiceQuestion;

    /* 试题页数据源 */
    private List<OnlineExerciseQuestion> list;
    /* 试题页Adapter */
    private QuesitonPagerAdapter pagerAdapter;
    /* 答案 */
    private List<String> answers;
    /* 在线做题题目选择列表的Adapter */
    private OnlineExerciseQuestionChoiceListAdapter choiceListAdapter;

    /* 当前试题索引 */
    private int currPosition = 0;

    /* 答题开始时间 */
    private long startTime;

    /* 主导器 */
    private OnlineExerciseTestActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_online_exercise_test);
        presenter = new OnlineExerciseTestActivityPresenter(this);
        // 初始化
        init();
        // 订阅
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化
     */
    private void init() {
        // 接收参数
        list = getIntent().getParcelableArrayListExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_QUESTION_LIST);
        // 初始化界面
        initView();
        // 绑定数据
        bindData();
        // 开始计时
        startTime();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        // 进度
        sbTextProgress.setMax((list.size()) * 10);
        if (currPosition == list.size() - 1) {
            sbTextProgress.setProgress((currPosition + 1) * 10 - 2);
        } else {
            sbTextProgress.setProgress((currPosition + 1) * 10);
        }

        sbTextProgress.setEnabled(false);
        tvProgress.setText((currPosition + 1) + " / " + list.size());
    }


    /**
     * 绑定数据
     */
    private void bindData() {
        // 试题数据
        // 初始化答案
        answers = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            answers.add("");
        }
        // 设置Adapter
        pagerAdapter = new QuesitonPagerAdapter(this, list, answers);
        vpQuestionList.setAdapter(pagerAdapter);
        // 一次加载5个页面
        vpQuestionList.setOffscreenPageLimit(5);

        // 题目选择GridView数据
        choiceListAdapter = new OnlineExerciseQuestionChoiceListAdapter(this, answers);
        gvChoiceQuestion.setAdapter(choiceListAdapter);
    }

    /**
     * 开始计时
     */
    private void startTime() {
        startTime = System.currentTimeMillis();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    /**
     * 结束计时
     */
    private String endTime() {
        chronometer.stop();
        // 得时间
        String showTime = chronometer.getText().toString();
        return showTime;
    }

    /**
     * 设置答题记录id
     *
     * @param recordId 答题记录id
     */
    public void setTestRecord(String recordId) {
        // 打开分析界面
        Intent intent = new Intent(this, OnlineExerciseReportActivity.class);
        intent.putExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_RECORD_ID, recordId);
        startActivity(intent);
        // 关闭当前界面
        finish();
    }

    /**
     * 试题页改变监听器
     *
     * @param position
     */
    @OnPageChange(R.id.vp_question_list)
    public void onPageSelected(int position) {
        currPosition = position;
        initView();
    }

    /**
     * 上一题
     */
    @OnClick(R.id.iv_pre_question)
    public void preQuestion() {
        currPosition--;
        if (currPosition < 0) {
            currPosition = 0;
        }
        vpQuestionList.setCurrentItem(currPosition, false);
    }

    /**
     * 下一题
     */
    @OnClick(R.id.iv_next_question)
    public void nextQuestion() {
        currPosition++;
        if (currPosition >= list.size()) {
            currPosition = list.size() - 1;
        }
        vpQuestionList.setCurrentItem(currPosition, false);
    }

    /**
     * 显示或隐藏题目选择区域
     */
    @OnClick({R.id.tv_choice_question, R.id.rl_hide_choice_area})
    public void showOrHideChoiceArea() {
        if (llChoiceArea.getVisibility() == View.VISIBLE) {
            llChoiceArea.setVisibility(View.GONE);
        } else {
            llChoiceArea.setVisibility(View.VISIBLE);
            choiceListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        new DialogUtils(this).confirm(getString(R.string.tip), "确定放弃本次练习吗？",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        back();
    }

    /**
     * 提交
     */
    @OnClick({R.id.iv_submit, R.id.tv_submit})
    public void submit() {
        int finishNum = 0;
        for (String answer : answers) {
            if (!TextUtils.isEmpty(answer)) {
                finishNum++;
            }
        }

        new DialogUtils(this).confirm("交卷", "共" + list.size() + "题，完成" + finishNum + "题",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 结束计时
                        String useTime = endTime();
                        // 提交答案
                        presenter.submitResult(startTime, useTime, list, answers);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 题目选择区域透明部分
     */
    @OnClick(R.id.tv_space)
    public void clickSpace() {
        // 该方法不需要写代码，但必须存在
    }

    /**
     * 定位试题
     */
    @OnItemClick(R.id.gv_choice_question)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 发送事件
        postEvent(Consts.EventType.ONLINE_EXERCISE_CHANGE_QUESTION_PAGE, position);
        // 隐藏选择区
        llChoiceArea.setVisibility(View.GONE);
    }

    /**
     * 接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataEvent(DataEvent dataEvent) {
        switch (dataEvent.getType()) {
            case Consts.EventType.ONLINE_EXERCISE_CHANGE_QUESTION_PAGE: // 在线做题试题换页
                int position = (int) dataEvent.getData();
                vpQuestionList.setCurrentItem(position, false);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // 取消订阅
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
