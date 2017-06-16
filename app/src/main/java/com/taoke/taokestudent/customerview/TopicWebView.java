package com.taoke.taokestudent.customerview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.taoke.taokestudent.util.LogUtils;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import java.util.ArrayList;
import java.util.List;

/**
 * 题干WebView
 */
public class TopicWebView extends WebView {
    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public TopicWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 允许使用JavaScript
        getSettings().setJavaScriptEnabled(true);
        // 缓存模式为无缓冲，提高加载速度
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 将背景色设为透明
        setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 设置试题
     *
     * @param topic 题干
     */
    public void setTopic(String topic) {
        if (topic == null) {
            topic = "";
        }

        //加载html结构
        Chunk chunk = getChunk();
        // tag名与.chtml文件中一一对应，必须一致
        String TAG_STEM = "question_topic";

        //设置题干
        chunk.set(TAG_STEM, topic);

        //将html结构载入WebView显示
        this.loadDataWithBaseURL(null, chunk.toString(), "text/html", "utf-8", "about:blank");
    }

    /**
     * 加载html结构
     *
     * @return html结构对象
     */
    private Chunk getChunk() {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        // topic是themes/topic.chtml的文件名
        return new Theme(loader).makeChunk("topic");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
