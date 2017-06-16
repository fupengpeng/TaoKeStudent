package com.taoke.taokestudent.activity.personalcenter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.customerview.ClearEditText;
import com.taoke.taokestudent.presenter.personalcenter.LoginActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 账号 */
    @BindView(R.id.cet_login_account)
    ClearEditText cetLoginAccount;
    /* 密码 */
    @BindView(R.id.cet_login_password)
    ClearEditText cetLoginPassword;
    /* 登录 */
    @BindView(R.id.btn_login)
    Button btnLogin;
    /* 去注册 */
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;

    /* 主导器 */
    private LoginActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_login);
        // 初始化主导器
        presenter = new LoginActivityPresenter(this);
        // 标题
        tvTitleText.setText(getString(R.string.login));
    }

    /**
     * 输入框文本改变后
     *
     * @param editable editable
     */
    @OnTextChanged(value = {R.id.cet_login_account, R.id.cet_login_password}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(cetLoginAccount.getText().toString().trim()) &&
                !TextUtils.isEmpty(cetLoginPassword.getText().toString().trim())) {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.bg_btn_enable_selector);
        } else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.bg_btn_unable);
        }
    }

    /**
     * 登录
     */
    @OnClick(R.id.btn_login)
    public void login() {
        String account = cetLoginAccount.getText().toString().trim();
        String password = cetLoginPassword.getText().toString().trim();
        // 登录
        presenter.login(account, password);
    }

    /**
     * 去注册
     */
    @OnClick(R.id.tv_login_register)
    public void register() {
        startActivity(RegisterActivity.class);
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }
}
