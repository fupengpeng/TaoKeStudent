package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.entity.MicroLessonDetailListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 微课视频详情中的免费课程列表Adapter
 */
public class MicroDetailListAdapter extends BaseAdapter<MicroLessonDetailListItem> {

    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public MicroDetailListAdapter(Context context, List<MicroLessonDetailListItem> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_micro_detail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 为控件填充数据
        MicroLessonDetailListItem item = getItem(position);
        holder.tvName.setText(item.getName());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_micro_detail_name)
        TextView tvName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
