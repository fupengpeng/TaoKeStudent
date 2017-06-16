package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.customerview.ChoiceQuestionWebView;
import com.taoke.taokestudent.entity.OnlineExerciseQuestion;
import com.taoke.taokestudent.util.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 答题ViewPager的Adapter
 */
public class QuesitonPagerAdapter extends PagerAdapter {
    /* Context */
    private Context context;
    /* 试题集合 */
    private List<OnlineExerciseQuestion> questions;
    /* 答案集合 */
    private List<String> answers;

    /**
     * 构造方法
     *
     * @param context   Context
     * @param questions 试题集合
     */
    public QuesitonPagerAdapter(Context context, List<OnlineExerciseQuestion> questions, List<String> answers) {
        this.context = context;
        this.questions = questions;
        this.answers = answers;
    }

    @Override
    public int getCount() {
        if (questions != null) {
            return questions.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 获取要显示的试题和答案
        final OnlineExerciseQuestion question = questions.get(position);

        //
        if (position % 2 == 1) {
            question.setTixing(Consts.QuestionType.MULTIPLE_CHOICE);
        }

        // 修正图片路径
        changeImageUrl(question);

        // 初始化试题页面
        View view = LayoutInflater.from(context).inflate(R.layout.item_online_exercise_question, null);
        // 试题内容
        ChoiceQuestionWebView choiceQuestionWebView = (ChoiceQuestionWebView) view.findViewById(R.id.choiceQuestionWebView);
        // 设置试题
        ChoiceQuestionWebView.Question wvQuestion = choiceQuestionWebView.new Question();
        // 设置题目序号
        wvQuestion.setPosition(position);
        // 设置单多选
        wvQuestion.setIsSingleChoice(Consts.QuestionType.SINGLE_CHOICE.equals(question.getTixing()));
        // 设置题干
        if (TextUtils.isEmpty(question.getZuti_topic())) {
            wvQuestion.setStem(question.getTopic());
        } else {
            wvQuestion.setStem(question.getZuti_topic() + "<br/>" + question.getTopic());
        }
        // 设置选项
        wvQuestion.getItems().add(question.getA());
        wvQuestion.getItems().add(question.getB());
        wvQuestion.getItems().add(question.getC());
        wvQuestion.getItems().add(question.getD());
        // 设置选中的选项
        String answer = answers.get(position);
        wvQuestion.setAnswer(answer);

        // 设置答案改变监听器
        choiceQuestionWebView.setOnSelectionChangedListener(new ChoiceQuestionWebView.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(int position, String answer) {
                // 保存答案
                answers.set(position, answer);
                // 下一页
                if ((position < getCount() - 1)
                        && Consts.QuestionType.SINGLE_CHOICE.equals(question.getTixing())) {
                    // 发送换页事件
                    EventBus.getDefault().post(new DataEvent(Consts.EventType.ONLINE_EXERCISE_CHANGE_QUESTION_PAGE, (position + 1)));
                }
            }
        });

        // 加载试题
        choiceQuestionWebView.setQuestion(wvQuestion);

        // 将页面加载入ViewPager
        container.addView(view);
        return view;
    }

    /**
     * 修正图片路径
     *
     * @param question
     */
    private void changeImageUrl(OnlineExerciseQuestion question) {
        question.setTopic(StringUtils.changeImageUrl(question.getTopic()));
        question.setZuti_topic(StringUtils.changeImageUrl(question.getZuti_topic()));
        question.setA(StringUtils.changeImageUrl(question.getA()));
        question.setB(StringUtils.changeImageUrl(question.getB()));
        question.setC(StringUtils.changeImageUrl(question.getC()));
        question.setD(StringUtils.changeImageUrl(question.getD()));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 将第position个View对象从容器container中移除
        container.removeView((View) object);
    }

}
