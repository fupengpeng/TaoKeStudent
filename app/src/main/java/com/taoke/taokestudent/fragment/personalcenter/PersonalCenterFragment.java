package com.taoke.taokestudent.fragment.personalcenter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.personalcenter.ConnectionAccountActivity;
import com.taoke.taokestudent.activity.personalcenter.RechargeActivity;
import com.taoke.taokestudent.activity.personalcenter.UpdatePasswordActivity;
import com.taoke.taokestudent.activity.personalcenter.UserInfoActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.customerview.CircleImageView;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.fragment.BaseFragment;
import com.taoke.taokestudent.util.DialogUtils;
import com.taoke.taokestudent.util.InfoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心Fragment
 */
public class PersonalCenterFragment extends BaseFragment {
    /* 用户头像 */
    @BindView(R.id.civ_personal_center_user_image)
    CircleImageView civPersonalCenterUserImage;
    /* 用户名 */
    @BindView(R.id.tv_personal_center_name)
    TextView tvPersonalCenterName;
    /* 淘课币 */
    @BindView(R.id.tv_cash)
    TextView tvCash;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 订阅
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载界面
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_personal_center);
        // 初始化界面
        initViews();
        return view;
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        User user = MyApplication.getInstance().getUser();
        if (user == null) {
            civPersonalCenterUserImage.setImageResource(R.drawable.user_unlogin);
            tvPersonalCenterName.setText("");
            return;
        }

        // 加载头像
        if (!TextUtils.isEmpty(user.getHead_image())) {
            // 加载头像
            Picasso.with(getActivity()).load(user.getHead_image()).into(civPersonalCenterUserImage);
        } else {
            civPersonalCenterUserImage.setImageResource(R.drawable.user_unlogin);
        }
        // 显示昵称
        if (!TextUtils.isEmpty(user.getNickname())) {
            String nickName = user.getNickname();
            if (nickName.length() > 10) {
                nickName = nickName.substring(0, 10) + "...";
            }
            tvPersonalCenterName.setText(nickName);
        } else if (!TextUtils.isEmpty(user.getRealname())) {
            String realname = user.getRealname();
            if (realname.length() > 10) {
                realname = realname.substring(0, 10) + "...";
            }
            tvPersonalCenterName.setText(realname);
        } else {
            tvPersonalCenterName.setText("");
        }

        // 显示淘课币
        tvCash.setText(user.getCash());

    }

    /**
     * 打开用户信息界面
     */
    @OnClick(R.id.ll_personal_center_header)
    public void showUserInfo() {
        startActivity(UserInfoActivity.class);
    }

    /**
     * 充值
     */
    @OnClick(R.id.ll_recharge)
    public void recharge() {
        startActivity(RechargeActivity.class);
    }

    /**
     * 关联账号
     */
    @OnClick(R.id.ll_connection_account)
    public void connectionAccount() {
        startActivity(ConnectionAccountActivity.class);
    }

    /**
     * 退出账号
     */
    @OnClick(R.id.ll_exit_account)
    public void exitAccount() {
        new DialogUtils(getActivity()).confirm(getString(R.string.tip), getString(R.string.ensure_exit_account),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 发布退出账号事件
                        postEvent(Consts.EventType.EVENT_EXIT_ACCOUNT, null);
                        //
                        InfoUtils.showInfo(getActivity(), getString(R.string.exit_account_success));
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataEvent(DataEvent dataEvent) {
        switch (dataEvent.getType()) {
            case Consts.EventType.EVENT_UPDATE_USER: // 更新用户数据
            case Consts.EventType.EVENT_LOGIN_SUCCESS: // 登录成功
                // 初始化界面
                initViews();
                break;
        }
    }

    @Override
    public void onDestroy() {
        //解除订阅
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
