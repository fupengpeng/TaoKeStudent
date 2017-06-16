package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.entity.OnlineExerciseQuestion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 在线做题报告答案列表的Adapter
 */
public class OnlineExerciseReportAnswerListAdapter extends BaseAdapter<OnlineExerciseQuestion> {
    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public OnlineExerciseReportAnswerListAdapter(Context context, List<OnlineExerciseQuestion> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_online_exercise_question_choice, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 为控件填充数据
        OnlineExerciseQuestion question = getItem(position);
        holder.tvItem.setText("" + (position + 1));

        if (TextUtils.isEmpty(question.getDaan())) { // 未答
            holder.tvItem.setBackgroundResource(R.drawable.cicle_huan);
            holder.tvItem.setTextColor(getContext().getResources().getColor(R.color.theme_color));
        } else if (question.getAnswer().toUpperCase().equals(question.getDaan().toUpperCase())) { // 答对
            holder.tvItem.setBackgroundResource(R.drawable.bg_cicle);
            holder.tvItem.setTextColor(Color.WHITE);
        } else { // 答错
            holder.tvItem.setBackgroundResource(R.drawable.bg_cirle_red);
            holder.tvItem.setTextColor(Color.WHITE);
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_question_choice_item)
        TextView tvItem;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
