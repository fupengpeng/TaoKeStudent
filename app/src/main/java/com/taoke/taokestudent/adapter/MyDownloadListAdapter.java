package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoke.taokestudent.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的下载文件夹列表的Adapter
 */
public class MyDownloadListAdapter extends BaseAdapter<String> {

    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public MyDownloadListAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_my_download_folder, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 填充数据
        holder.tvName.setText(getItem(position));

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_folder_name)
        TextView tvName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
