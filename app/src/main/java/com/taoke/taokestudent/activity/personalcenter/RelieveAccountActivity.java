package com.taoke.taokestudent.activity.personalcenter;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.customerview.ClearEditText;
import com.taoke.taokestudent.customerview.SendValidateButton;
import com.taoke.taokestudent.presenter.personalcenter.RelieveAccountActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.UIUtils;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RelieveAccountActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 手机号 */
    @BindView(R.id.cet_unbind_cellphone_number)
    ClearEditText cetCellphoneNumber;
    /* 密码 */
    @BindView(R.id.cet_unbind_password)
    ClearEditText cetPassword;
    /* 验证码 */
    @BindView(R.id.cet_unbind_validate_code)
    ClearEditText cetValidateCode;
    /* 发送验证码按钮 */
    @BindView(R.id.svb_unbind_sand_validate)
    SendValidateButton svbSandValidate;
    /* 绑定按钮 */
    @BindView(R.id.btn_unbind)
    Button btnUnbind;

    /* 主导器 */
    private RelieveAccountActivityPresenter presenter;

    /* 服务器返回的验证码 */
    private String unbindValidateCode = "";

    /* 手机号 */
    private String phoneNumber;
    /* 密码 */
    private String password;
    /* 验证码 */
    private String validateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_relieve_account);
        // 初始化主导器
        presenter = new RelieveAccountActivityPresenter(this);
        // 初始化验证码发送按钮
        initSendValidateButton();
        // 获取参数
        phoneNumber = getIntent().getStringExtra(Consts.IntentExtraKey.PHONE_NUM);
        cetCellphoneNumber.setText(phoneNumber);
        cetCellphoneNumber.setEnabled(false);
        // 标题
        tvTitleText.setText(getString(R.string.relieve_account));
    }

    /**
     * 初始化验证码发送按钮
     */
    private void initSendValidateButton() {
        svbSandValidate.setEnableColor(Color.WHITE);
        svbSandValidate.setDisableColor(Color.WHITE);
        svbSandValidate.setmListener(new SendValidateButton.OnSendValidateListener() {
            @Override
            public void onTick() {
            }

            @Override
            public void onClickSendValidateButton() {
                // 发送验证码
                presenter.sendValidateCode(phoneNumber);

            }
        });
    }

    /**
     * 解除绑定
     */
    @OnClick(R.id.btn_unbind)
    public void unbindAccount() {
        // 获取注册信息
        password = cetPassword.getText().toString().trim();
        validateCode = cetValidateCode.getText().toString().trim();

        // 验证绑定信息
        if (validateUnbindInfo()) {
            // 解除绑定
            presenter.relieveAccount(phoneNumber, password);
        }
    }

    /**
     * 输入框文本改变后
     *
     * @param editable editable
     */
    @OnTextChanged(value = {R.id.cet_unbind_password, R.id.cet_unbind_validate_code
    }, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(cetPassword.getText().toString().trim())
                && !TextUtils.isEmpty(cetValidateCode.getText().toString().trim())) {
            btnUnbind.setEnabled(true);
            btnUnbind.setBackgroundResource(R.drawable.bg_btn_enable_selector);
        } else {
            btnUnbind.setEnabled(false);
            btnUnbind.setBackgroundResource(R.drawable.bg_btn_unable);
        }
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

    /**
     * 设置验证码
     */
    public void setValidateCode(String validateCode) {
        this.unbindValidateCode = validateCode;
        // 启动验证码发送按钮的倒计时
        svbSandValidate.startTickWork();
    }

    /**
     * 验证绑定信息
     */
    private boolean validateUnbindInfo() {
        // 验证验证码
        if (!unbindValidateCode.equals(validateCode)) {
            InfoUtils.showInfo(this, getString(R.string.validate_code_inconformity));
            UIUtils.showInputMethod(this, cetValidateCode, true);
            return false;
        }

        return true;
    }
}
