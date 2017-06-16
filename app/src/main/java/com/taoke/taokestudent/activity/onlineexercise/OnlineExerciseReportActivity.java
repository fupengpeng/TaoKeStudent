package com.taoke.taokestudent.activity.onlineexercise;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.adapter.OnlineExerciseReportAnswerListAdapter;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.customerview.RoundProgressBar;
import com.taoke.taokestudent.entity.OnlineExerciseQuestion;
import com.taoke.taokestudent.entity.OnlineExerciseTestResultResponseData;
import com.taoke.taokestudent.presenter.onlineexercise.OnlineExerciseReportActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 在线做题结果报告界面
 */
public class OnlineExerciseReportActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 正确率 */
    @BindView(R.id.rpb_accuracy)
    RoundProgressBar rpbAccuracy;
    /* 试题数量 */
    @BindView(R.id.tv_num)
    TextView tvNum;
    /* 用时 */
    @BindView(R.id.tv_use_time)
    TextView tvUseTime;
    /* 答案列表 */
    @BindView(R.id.gv_answer)
    GridView gvAnswer;

    /* 答案列表数据源 */
    private List<OnlineExerciseQuestion> questions;
    /* Adapter */
    private OnlineExerciseReportAnswerListAdapter adapter;

    /* 解析列表 */
    private ArrayList<OnlineExerciseQuestion> analyseList;

    /* 主导器 */
    private OnlineExerciseReportActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_online_exercise_report);
        presenter = new OnlineExerciseReportActivityPresenter(this);
        // 初始化
        init();
        // 标题
        tvTitleText.setText("练习报告");
    }

    /**
     * 初始化
     */
    private void init() {
        // 接收参数
        String recordId = getIntent().getStringExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_RECORD_ID);
        // 初始化解析列表
        analyseList = new ArrayList<>();
        // 请求答题记录
        presenter.getTestReport(recordId);
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(OnlineExerciseTestResultResponseData data) {
        // 设置正确率
        setAccuracy(data.getDaduilv());
        // 题目数量
        tvNum.setText(data.getZongshu() + "道");
        // 用时
        tvUseTime.setText(data.getUse_time());
        // 答案列表
        questions = data.getDataList();
        adapter = new OnlineExerciseReportAnswerListAdapter(this, questions);
        gvAnswer.setAdapter(adapter);

    }

    /**
     * 设置正确率
     */
    private void setAccuracy(final double accuracy) {
        final int[] progress = {0};
        rpbAccuracy.post(new Runnable() {
            @Override
            public void run() {
                while (rpbAccuracy.getProgress() < rpbAccuracy.getMax()) {
                    progress[0]++;
                    if (accuracy >= progress[0]) {
                        rpbAccuracy.setProgress(progress[0]);
                    } else {
                        rpbAccuracy.removeCallbacks(this);
                        break;
                    }
                    rpbAccuracy.postDelayed(this, 100);
                }
            }
        });
    }

    /**
     * 单个题目解析解析
     */
    @OnItemClick(R.id.gv_answer)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        analyseList.clear();
        analyseList.add(questions.get(position));
        showAnalyse();
    }

    /**
     * 错题解析
     */
    @OnClick(R.id.tv_wrong_analyse)
    public void wrongAnalyse(){

    }

    /**
     * 全部解析
     */
    @OnClick(R.id.tv_all_analyse)
    public void allAnalyse(){

    }

    /**
     * 解析
     */
    private void showAnalyse() {
        Intent intent = new Intent(this, OnlineExerciseAnalyseActivity.class);
        intent.putParcelableArrayListExtra(Consts.IntentExtraKey.ONLINE_EXERCISE_QUESTION_LIST, analyseList);
        startActivity(intent);
    }
}
