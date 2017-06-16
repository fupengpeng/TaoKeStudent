package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClassItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 在线做题单元列表Adapter
 */
public class OnlineExerciseUnitListAdapter extends BaseAdapter<OnlineExerciseBaseClassItem> {
    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public OnlineExerciseUnitListAdapter(Context context, List<OnlineExerciseBaseClassItem> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_online_exercise_unit, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 填充数据
        OnlineExerciseBaseClassItem item = getItem(position);
        holder.tvName.setText(item.getName());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_unit_name)
        TextView tvName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
