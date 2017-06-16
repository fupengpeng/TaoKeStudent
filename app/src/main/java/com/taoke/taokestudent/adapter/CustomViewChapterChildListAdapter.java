package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.entity.OnlineExerciseChapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义在线做题章节项子级章节列表Adapter
 */
public class CustomViewChapterChildListAdapter extends BaseAdapter<OnlineExerciseChapter> {
    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public CustomViewChapterChildListAdapter(Context context, List<OnlineExerciseChapter> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.customer_view_list_item_chapter_children, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 填充数据
        final OnlineExerciseChapter item = getItem(position);
        holder.tvName.setText(item.getName());
        // 选择图标
        holder.ivCheck.setImageResource(item.isSelected() ? R.drawable.check_checked : R.drawable.check_uncheck);
        // 选择监听器
        holder.llChildItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setSelected(!item.isSelected());
                holder.ivCheck.setImageResource(item.isSelected() ? R.drawable.check_checked : R.drawable.check_uncheck);
            }
        });

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.ll_child_item)
        LinearLayout llChildItem;
        @BindView(R.id.iv_child_check)
        ImageView ivCheck;
        @BindView(R.id.tv_child_name)
        TextView tvName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
