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
 * 微课视频列表Adapter
 */
public class MicroLessonVideoListAdapter extends BaseAdapter<MicroLessonVideoListItem> {
    /* 是否免费 */
    private boolean isFree;

    /* 微课视频业务 */
    private MicroLessonVideoModel model;

    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public MicroLessonVideoListAdapter(Context context, List<MicroLessonVideoListItem> data) {
        super(context, data);
        if (MyApplication.getInstance().getUser() != null) {
            isFree = MyApplication.getInstance().getUser().isFree();
        } else {
            isFree = false;
        }
        model = new MicroLessonVideoModel();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_micro_lesson_video, null);
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
        // 是否购买
        if (Consts.MicroPayType.PAY.equals(item.getPayly()) || isFree) {
            holder.tvMicroLessonVideoBuy.setText(getContext().getString(R.string.buyed));
            holder.tvMicroLessonVideoBuy.setBackgroundResource(R.drawable.bg_buy_d5d4cf);
            holder.tvMicroLessonVideoBuy.setEnabled(false);
        } else {
            holder.tvMicroLessonVideoBuy.setText(getContext().getString(R.string.buy));
            holder.tvMicroLessonVideoBuy.setBackgroundResource(R.drawable.bg_buy_21a675);
            holder.tvMicroLessonVideoBuy.setEnabled(true);
            holder.tvMicroLessonVideoBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 判断是否登录
                    if (MyApplication.getInstance().getUser() == null) { // 未登录
                        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                    } else {
                        // 显示等待对话框
                        showWaitDialog(Consts.WaitDialogMessage.DATA_LODING);
                        // 购买
                        model.payWeiKe(MyApplication.getInstance().getUser().getUid(), item.getId(), new ObjectCallBack<String>() {
                            @Override
                            public void onSuccess(String data) {
                                // 关闭等待对话框
                                closeWaitDialog();
                                // 更新用户信息
                                MyApplication.getInstance().updateUserInfo();
                                // 发送购买完成事件
                                EventBus.getDefault().post(new DataEvent(Consts.EventType.EVENT_BUY_LESSON_SUCCESS, item.getId()));
                            }

                            @Override
                            public void onFail(Exception e) {
                                // 关闭等待对话框
                                closeWaitDialog();
                                InfoUtils.showInfo(getContext(), e.getMessage());
                            }
                        });
                    }
                }
            });
        }

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
