package com.taoke.taokestudent.activity.personalcenter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.customerview.ClearEditText;
import com.taoke.taokestudent.customerview.SendValidateButton;
import com.taoke.taokestudent.presenter.personalcenter.RegisterActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 手机号 */
    @BindView(R.id.cet_register_cellphone_number)
    ClearEditText cetRegisterCellphoneNumber;
    /* 密码 */
    @BindView(R.id.cet_register_password)
    ClearEditText cetRegisterPassword;
    /* 确认密码 */
    @BindView(R.id.cet_register_confirm_password)
    ClearEditText cetRegisterConfirmPassword;
    /* 验证码 */
    @BindView(R.id.cet_register_validate_code)
    ClearEditText cetRegisterValidateCode;
    /* 发送验证码按钮 */
    @BindView(R.id.svb_register_sand_validate)
    SendValidateButton svbRegisterSandValidate;
    /* 注册按钮 */
    @BindView(R.id.btn_register)
    Button btnRegister;
    /* 91条款选择框 */
    @BindView(R.id.cb_term)
    CheckBox cbTerm;

    /* 主导器 */
    private RegisterActivityPresenter presenter;

    /* 服务器返回的验证码 */
    private String registerValidateCode = "";

    /* 手机号 */
    private String phoneNumber;
    /* 密码 */
    private String password;
    /* 确认密码 */
    private String confirmPassword;
    /* 验证码 */
    private String validateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_register);
        // 初始化主导器
        presenter = new RegisterActivityPresenter(this);
        // 初始化验证码发送按钮
        initSendValidateButton();
        // 标题
        tvTitleText.setText(getString(R.string.register));
    }

    /**
     * 初始化验证码发送按钮
     */
    private void initSendValidateButton() {
        svbRegisterSandValidate.setEnableColor(Color.WHITE);
        svbRegisterSandValidate.setDisableColor(Color.WHITE);
        svbRegisterSandValidate.setmListener(new SendValidateButton.OnSendValidateListener() {
            @Override
            public void onTick() {
            }

            @Override
            public void onClickSendValidateButton() {
                // 验证手机号
                String phoneNumber = cetRegisterCellphoneNumber.getText().toString().trim();
                if (validatePhoneNumber(phoneNumber)) {
                    // 发送验证码
                    presenter.sendValidateCode(phoneNumber);
                }
            }
        });
    }

    /**
     * 注册
     */
    @OnClick(R.id.btn_register)
    public void register() {
        // 获取注册信息
        phoneNumber = cetRegisterCellphoneNumber.getText().toString().trim();
        password = cetRegisterPassword.getText().toString().trim();
        confirmPassword = cetRegisterConfirmPassword.getText().toString().trim();
        validateCode = cetRegisterValidateCode.getText().toString().trim();

        // 验证注册信息
        if (validateRegisterInfo()) {
            // 注册
            presenter.register(phoneNumber, password);
        }
    }

    /**
     * 显示91注册条款
     */
    @OnClick(R.id.tv_term_91)
    public void showTerm91() {
        Uri uri = Uri.parse(Consts.NetUrl.TERM_91);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    /**
     * 改变91条款选择
     */
    @OnCheckedChanged(R.id.cb_term)
    public void changeCheckTerm() {
        afterTextChanged(null);
    }

    /**
     * 输入框文本改变后
     *
     * @param editable editable
     */
    @OnTextChanged(value = {R.id.cet_register_cellphone_number, R.id.cet_register_password,
            R.id.cet_register_confirm_password, R.id.cet_register_validate_code
    }, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(cetRegisterCellphoneNumber.getText().toString().trim())
                && !TextUtils.isEmpty(cetRegisterPassword.getText().toString().trim())
                && !TextUtils.isEmpty(cetRegisterConfirmPassword.getText().toString().trim())
                && !TextUtils.isEmpty(cetRegisterValidateCode.getText().toString().trim())
                && cbTerm.isChecked()) {
            btnRegister.setEnabled(true);
            btnRegister.setBackgroundResource(R.drawable.bg_btn_enable_selector);
        } else {
            btnRegister.setEnabled(false);
            btnRegister.setBackgroundResource(R.drawable.bg_btn_unable);
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
    public void setRegisterValidateCode(String registerValidateCode) {
        this.registerValidateCode = registerValidateCode;
        // 启动验证码发送按钮的倒计时
        svbRegisterSandValidate.startTickWork();
    }

    /**
     * 验证手机号
     *
     * @param phoneNumber 手机号
     */
    private boolean validatePhoneNumber(String phoneNumber) {
        // 验证是否为空
        if (TextUtils.isEmpty(phoneNumber)) {
            InfoUtils.showInfo(RegisterActivity.this, getString(R.string.phone_number_empty));
            UIUtils.showInputMethod(RegisterActivity.this, cetRegisterCellphoneNumber, true);
            return false;
        }
        // 验证格式
        // 大陆号
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNumber);
        // 香港号
        String regExp2 = "^(5|6|8|9)\\d{7}$";
        Pattern p2 = Pattern.compile(regExp2);
        Matcher m2 = p2.matcher(phoneNumber);

        if (!m.matches() && !m2.matches()) {
            InfoUtils.showInfo(RegisterActivity.this, getString(R.string.phone_number_format_error));
            UIUtils.showInputMethod(RegisterActivity.this, cetRegisterCellphoneNumber, true);
            return false;
        }
        return true;
    }

    /**
     * 验证注册信息
     */
    private boolean validateRegisterInfo() {
        // 验证手机号
        if (!validatePhoneNumber(phoneNumber)) {
            return false;
        }

        // 验证登录密码
        if (!password.equals(confirmPassword)) {
            InfoUtils.showInfo(RegisterActivity.this, getString(R.string.password_inconformity));
            UIUtils.showInputMethod(RegisterActivity.this, cetRegisterConfirmPassword, true);
            return false;
        }

        // 验证验证码
        if (!registerValidateCode.equals(validateCode)) {
            InfoUtils.showInfo(RegisterActivity.this, getString(R.string.validate_code_inconformity));
            UIUtils.showInputMethod(RegisterActivity.this, cetRegisterValidateCode, true);
            return false;
        }

        return true;
    }
}
