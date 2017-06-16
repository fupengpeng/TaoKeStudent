package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.microlessonvideo.VideoPlayerActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.customerview.ChoiceQuestionWebView;
import com.taoke.taokestudent.customerview.TopicWebView;
import com.taoke.taokestudent.entity.MicroExerciseQuestionListItem;
import com.taoke.taokestudent.util.StringUtils;
import com.taoke.taokestudent.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 习题微课习题列表
 */
public class MicroExerciseQuestionListAdapter extends BaseAdapter<MicroExerciseQuestionListItem> {
    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public MicroExerciseQuestionListAdapter(Context context, List<MicroExerciseQuestionListItem> data) {
        super(context, data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_micro_exercise_question, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 填充数据
        final MicroExerciseQuestionListItem item = getItem(position);
        holder.tvNum.setText("第" + (position + 1) + "题");
        //设置试题
        holder.question.setTopic(StringUtils.changeImageUrl(item.getTopic()));
        // 设置答案
        holder.answer.setTopic(StringUtils.changeImageUrl(item.getAnswer()));
        // 设置解析
        holder.analyse.setTopic(StringUtils.changeImageUrl(item.getResolve()));

        // 视频解析
        holder.tvVideoAnalyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
                ArrayList<Uri> uriList = new ArrayList<>();
                uriList.add(Uri.parse(item.getViewUrl()));
                intent.putParcelableArrayListExtra(Consts.IntentExtraKey.VIDEO_URI, uriList);
                intent.putExtra(Consts.IntentExtraKey.VIDEO_NAME, "第" + (position + 1) + "题");
                getContext().startActivity(intent);
            }
        });

        // 显示答案和解析
        holder.llTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.llAnswerAnalyse.setVisibility(holder.llAnswerAnalyse.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_video_analyse)
        TextView tvVideoAnalyse;
        @BindView(R.id.wv_question)
        TopicWebView question;
        @BindView(R.id.wv_answer)
        TopicWebView answer;
        @BindView(R.id.wv_analyse)
        TopicWebView analyse;
        @BindView(R.id.ll_answer_analyse)
        LinearLayout llAnswerAnalyse;
        @BindView(R.id.ll_topic)
        LinearLayout llTopic;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
