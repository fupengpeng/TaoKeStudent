package com.taoke.taokestudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.personalcenter.RelieveAccountActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.ConnectionAccount;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关联账号列表Adapter
 */
public class ConnectionAccountListAdapter extends BaseAdapter<ConnectionAccount> {
    /**
     * 构造方法
     *
     * @param context Context对象，值不能为null
     * @param data
     */
    public ConnectionAccountListAdapter(Context context, List<ConnectionAccount> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_connection_account, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 填充数据
        final ConnectionAccount item = getItem(position);
        holder.tvAccount.setText(item.getUsername());
        holder.llUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RelieveAccountActivity.class);
                intent.putExtra(Consts.IntentExtraKey.PHONE_NUM, item.getUsername());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_connection_account)
        TextView tvAccount;
        @BindView(R.id.ll_unbind)
        LinearLayout llUnbind;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
