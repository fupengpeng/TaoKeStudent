package com.taoke.taokestudent.activity.personalcenter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.presenter.personalcenter.RechargeActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值界面
 */
public class RechargeActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 淘课币 */
    @BindView(R.id.tv_recharge_cash)
    TextView tvRechargeCash;
    /* 充值金额 */
    @BindView(R.id.sp_recharge_amount)
    Spinner spRechargeAmount;
    /* 支付宝单选按钮 */
    @BindView(R.id.rb_recharge_zfb)
    RadioButton rbRechargeZfb;
    /* 微信单选按钮 */
    @BindView(R.id.rb_recharge_wx)
    RadioButton rbRechargeWx;

    /**
     * 主导器
     */
    private RechargeActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_recharge);
        presenter = new RechargeActivityPresenter(this);
        // 初始化界面
        initViews();
        // 标题
        tvTitleText.setText(getString(R.string.recharge));
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        // 淘课币
        String cash = MyApplication.getInstance().getUser().getCash();
        tvRechargeCash.setText(cash);
    }

    /**
     * 确定
     */
    @OnClick(R.id.btn_ensure)
    public void ensure() {
        // 充值金额
        String[] amounts = getResources().getStringArray(R.array.sp_recharge_value);
        int position = spRechargeAmount.getSelectedItemPosition();
        String increase = amounts[position];
        // 支付方式
        String type;
        if (rbRechargeZfb.isChecked()) {
            type = Consts.PayType.ZFB;
        } else {
            type = Consts.PayType.WX;
        }
        // 支付
        presenter.pay(increase, type);
    }

    /**
     * 点击支付宝图片
     */
    @OnClick(R.id.iv_recharge_zfb)
    public void onClickZFB() {
        rbRechargeZfb.setChecked(true);
    }

    /**
     * 点击微信图片
     */
    @OnClick(R.id.iv_recharge_wx)
    public void onClickWX() {
        rbRechargeWx.setChecked(true);
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }
}
