package com.taoke.taokestudent.customerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.adapter.CustomViewChapterChildListAdapter;
import com.taoke.taokestudent.entity.OnlineExerciseChapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线做题章节项
 */
public class OnlineExerciseChapterItem extends LinearLayout {
    private TextView tvChapterName;
    // 子级章节列表
    private GridView gvChildList;
    // 子级列表数据源
    private List<OnlineExerciseChapter> childList;
    // Adapter
    private CustomViewChapterChildListAdapter adapter;

    // 章节名
    private String name;

    public OnlineExerciseChapterItem(Context context, String name, List<OnlineExerciseChapter> childList) {
        super(context);
        if (childList == null) {
            childList = new ArrayList<>();
        }
        this.childList = childList;

        this.name = name;

        // 初始化
        init();
    }

    public OnlineExerciseChapterItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化
     */
    private void init() {
        // 加载布局
        LinearLayout view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.customer_view_online_exercise_chapter_item, null);
        gvChildList = (GridView) view.findViewById(R.id.gv_customer_view_chapter_child_list);
        tvChapterName = (TextView) view.findViewById(R.id.tv_customer_view_chapter_name);

        // 绑定数据
        bindData();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        this.addView(view, params);
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        tvChapterName.setText(name);
        adapter = new CustomViewChapterChildListAdapter(getContext(), childList);
        gvChildList.setAdapter(adapter);
    }

    /**
     * 重置数据
     *
     * @param name
     * @param childList
     */
    public void resetData(String name, List<OnlineExerciseChapter> childList) {
        if (childList == null) {
            childList = new ArrayList<>();
        }

        tvChapterName.setText(name);
        adapter.setData(childList);
        adapter.notifyDataSetChanged();
    }
}
