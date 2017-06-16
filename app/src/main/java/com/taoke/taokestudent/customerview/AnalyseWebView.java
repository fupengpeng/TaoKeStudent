package com.taoke.taokestudent.customerview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择题WebView
 * 最多5个选项，多于5项只显示前5项
 */
public class AnalyseWebView extends WebView {
    // 试题
    private Question question;
    // 选项改变监听器
    private OnSelectionChangedListener listener;
    // 标识选项是否改变
    private boolean changeFlg;

    // tag名与.chtml文件中一一对应，必须一致
    private static final String TAG_STEM = "question_stem";
    private static final String TAG_ITEM = "question_item";
    private static final String TAG_ANSWER = "question_answer";

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public AnalyseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 允许使用JavaScript
        getSettings().setJavaScriptEnabled(true);
        // 缓存模式为无缓冲，提高加载速度
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 将背景色设为透明
        setBackgroundColor(Color.TRANSPARENT);

        //在网页中通过choiceQuestionWebView使用当前对象
        addJavascriptInterface(this, "choiceQuestionWebView");
    }

    @JavascriptInterface
    public void call(String webAnswer) {
        // 保存答案
        changeFlg = !question.getAnswer().equals(webAnswer);
        question.setAnswer(webAnswer);

        // 有选项改变通知监听接口
        if (changeFlg) {
            changeFlg = false;
            if (listener != null) {
                listener.onSelectionChanged(question.getPosition(), webAnswer);
            }
        }
    }

    /**
     * 设置试题
     *
     * @param question 题干
     */
    public void setQuestion(Question question) {
        // 保存试题
        if (question == null) {
            question = new Question();
        }
        this.question = question;

        // 加载html结构
        Chunk chunk;
        if (this.question.isSingleChoice) {
            chunk = getSingleChoiceChunk();
        } else {
            chunk = getMultiChoiceChunk();
        }
        // 设置题干
        chunk.set(TAG_STEM, this.question.getStem());
        // 设置选项
        for (int i = 0; (i < this.question.getItems().size()); i++) {
            chunk.set(TAG_ITEM + (i + 1), this.question.getItems().get(i));
        }
        // 设置选中项
        chunk.set(TAG_ANSWER, this.question.getAnswer());

        // 将html结构载入WebView显示
        this.loadDataWithBaseURL(null, chunk.toString(), "text/html", "utf-8", "about:blank");
    }

    /**
     * 加载单选题html结构
     *
     * @return html结构对象
     */
    private Chunk getSingleChoiceChunk() {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        // single_choice是themes/single_choice.chtml的文件名
        return new Theme(loader).makeChunk("single_choice");
    }

    /**
     * 加载多选题html结构
     *
     * @return html结构对象
     */
    private Chunk getMultiChoiceChunk() {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        // multi_choice是themes/multi_choice.chtml的文件名
        return new Theme(loader).makeChunk("multi_choice");
    }

    /**
     * 试题
     */
    public class Question {
        /**
         * 试题序号（从0开始）
         */
        private int position;
        /**
         * 是否单选题，默认单选
         */
        private boolean isSingleChoice;
        /**
         * 题干
         */
        private String stem;
        /**
         * 选项
         */
        private List<String> items;
        /**
         * 选择的答案
         */
        private String answer;

        /**
         * 构造方法
         */
        public Question() {
            // 试题序号
            position = 0;
            // 是否单选
            isSingleChoice = true;
            // 初始化题干
            stem = "";
            // 初始化选项
            items = new ArrayList<>();
            // 答案
            answer = "";
        }

        /**
         * 获取试题序号
         *
         * @return 试题序号
         */
        public int getPosition() {
            return position;
        }

        /**
         * 设置试题序号
         *
         * @param position 试题序号
         */
        public void setPosition(int position) {
            this.position = position;
        }

        /**
         * 获取是否单选题
         *
         * @return 是否单选题
         */
        public boolean isSingleChoice() {
            return isSingleChoice;
        }

        /**
         * 设置是否单选题
         *
         * @param isSingleChoice 是否单选题
         */
        public void setIsSingleChoice(boolean isSingleChoice) {
            this.isSingleChoice = isSingleChoice;
        }

        /**
         * 获取题干
         *
         * @return 题干
         */
        public String getStem() {
            return stem;
        }

        /**
         * 设置题干
         *
         * @param stem 题干
         */
        public void setStem(String stem) {
            if (stem == null) {
                stem = "";
            }
            this.stem = stem;
        }

        /**
         * 获取试题选项
         *
         * @return 试题选项
         */
        public List<String> getItems() {
            return items;
        }

        /**
         * 设置试题选项
         *
         * @param items 试题选项
         */
        public void setItems(List<String> items) {
            if (items == null) {
                items = new ArrayList<>();
            }
            this.items = items;
        }

        /**
         * 获取答案
         *
         * @return 答案
         */
        public String getAnswer() {
            return answer;
        }

        /**
         * 设置答案
         *
         * @param answer 答案
         */
        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }

    /**
     * 设置选项改变监听器
     *
     * @param listener 选项改变监听器对象
     */
    public void setOnSelectionChangedListener(OnSelectionChangedListener listener) {
        this.listener = listener;
    }

    /**
     * 选项改变监听器
     */
    public interface OnSelectionChangedListener {
        /**
         * 选项改变时调用
         *
         * @param position 试题序号（从0开始计数）
         * @param answer   答案
         */
        void onSelectionChanged(int position, String answer);
    }

}
