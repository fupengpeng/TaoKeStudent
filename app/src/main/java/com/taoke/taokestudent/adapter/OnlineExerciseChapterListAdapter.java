package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.taoke.taokestudent.customerview.OnlineExerciseChapterItem;
import com.taoke.taokestudent.entity.OnlineExerciseChapter;

import java.util.List;

/**
 * 在线做题章节列表Adapter
 */
public class OnlineExerciseChapterListAdapter extends BaseAdapter<OnlineExerciseChapter> {
    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public OnlineExerciseChapterListAdapter(Context context, List<OnlineExerciseChapter> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        OnlineExerciseChapterItem view = new OnlineExerciseChapterItem(getContext(),
//                getItem(position).getName(), getItem(position).getChildList());

        OnlineExerciseChapter item = getItem(position);

        if (convertView == null) {
            convertView = new OnlineExerciseChapterItem(getContext(),
                    item.getName(), item.getChildList());
        } else {
            ((OnlineExerciseChapterItem) convertView).resetData(item.getName(), item.getChildList());
        }

        return convertView;
    }

}
