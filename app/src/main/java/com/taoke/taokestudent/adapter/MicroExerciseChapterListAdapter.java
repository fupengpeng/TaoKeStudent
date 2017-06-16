package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.entity.ExerciseChapter;
import com.taoke.taokestudent.entity.ExerciseClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 习题微课章节列表Adapter
 */
public class MicroExerciseChapterListAdapter extends BaseExpandableListAdapter {
    /* Context */
    private Context context;
    /* 数据源 */
    private List<ExerciseChapter> chapterList;

    public MicroExerciseChapterListAdapter(Context context, List<ExerciseChapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @Override
    public int getGroupCount() {
        return chapterList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return chapterList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return chapterList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return chapterList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_micro_exercise_chapter_parent, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        // 填充数据
        ExerciseChapter chapter = (ExerciseChapter) getGroup(groupPosition);
        holder.tvName.setText(chapter.getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_micro_exercise_chapter_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        // 填充数据
        ExerciseClass child = (ExerciseClass) getChild(groupPosition, childPosition);
        holder.tvName.setText(child.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        @BindView(R.id.tv_exercise_chapter_parent_name)
        TextView tvName;

        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.tv_exercise_chapter_child_name)
        TextView tvName;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
