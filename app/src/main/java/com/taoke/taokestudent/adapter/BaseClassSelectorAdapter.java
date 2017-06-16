package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoke.taokestudent.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择器Adapter
 */
public class BaseClassSelectorAdapter extends BaseAdapter<String> {
    /* 选中项索引 */
    private int selectedPosition = 0;

    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public BaseClassSelectorAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_micro_base_class, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 填充数据
        String item = getItem(position);
        holder.tvItem.setText(item);
        if (position == selectedPosition) {
            holder.tvItem.setBackgroundResource(R.drawable.bg_base_class_item);
            holder.tvItem.setTextColor(Color.WHITE);
        } else {
            holder.tvItem.setBackgroundResource(R.drawable.bg_base_class_unselected);
            holder.tvItem.setTextColor(getContext().getResources().getColor(R.color.sub_item_text_color));
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_micro_selector_list_item)
        TextView tvItem;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
