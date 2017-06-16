package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.personalcenter.LoginActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.MicroLessonVideoListItem;
import com.taoke.taokestudent.model.MicroLessonVideoModel;
import com.taoke.taokestudent.util.InfoUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的课程列表Adapter
 */
public class MicroMyLessonListAdapter extends BaseAdapter<MicroLessonVideoListItem> {

    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public MicroMyLessonListAdapter(Context context, List<MicroLessonVideoListItem> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_micro_my_lesson, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 为控件填充数据
        final MicroLessonVideoListItem item = getItem(position);
        Picasso.with(getContext()).load(item.getPic())
                .placeholder(R.drawable.mianfei)
                .error(R.drawable.mianfei)
                .into(holder.ivMicroLessonVideoImage);
        holder.tvMicroLessonVideoName.setText(item.getName());
        holder.tvMicroLessonVideoPrice.setText(item.getPrice());
        holder.tvMicroLessonVideoClanum.setText(item.getClanum());
        holder.tvMicroLessonVideoBuy.setText("已购买");
        holder.tvMicroLessonVideoBuy.setBackgroundResource(R.drawable.bg_buy_d5d4cf);

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_micro_lesson_video_image)
        ImageView ivMicroLessonVideoImage;
        @BindView(R.id.tv_micro_lesson_video_name)
        TextView tvMicroLessonVideoName;
        @BindView(R.id.tv_micro_lesson_video_price)
        TextView tvMicroLessonVideoPrice;
        @BindView(R.id.tv_micro_lesson_video_clanum)
        TextView tvMicroLessonVideoClanum;
        @BindView(R.id.tv_micro_lesson_video_buy)
        TextView tvMicroLessonVideoBuy;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
