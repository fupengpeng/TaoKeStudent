package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.entity.MicroLessonDetailListItem;
import com.taoke.taokestudent.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 下载视频列表的Adapter
 */
public class MyDownloadVideoListAdapter extends BaseAdapter<MicroLessonDetailListItem> {
    /* 是否可以编辑 */
    private boolean isEdit = false;

    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public MyDownloadVideoListAdapter(Context context, List<MicroLessonDetailListItem> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_my_download_video, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 为控件填充数据
        final MicroLessonDetailListItem item = getItem(position);
        holder.tvName.setText(item.getName());
        // 设置下载状态
        holder.tvType.setText(item.getType());
        // 编辑状态
        if (isEdit) {
            holder.checkBox.setVisibility(View.VISIBLE);
            // 是否被选中
            holder.checkBox.setChecked(item.isSelected());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setSelected(isChecked);
                }
            });
        } else {
            holder.checkBox.setVisibility(View.GONE);
            holder.checkBox.setChecked(false);
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.cb_check)
        CheckBox checkBox;
        @BindView(R.id.tv_video_name)
        TextView tvName;
        @BindView(R.id.tv_download_type)
        TextView tvType;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 设置编辑状态
     *
     * @param edit 编辑状态
     */
    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
