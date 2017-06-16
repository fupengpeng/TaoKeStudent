package com.taoke.taokestudent.activity.personalcenter;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.customerview.ClearEditText;
import com.taoke.taokestudent.customerview.SendValidateButton;
import com.taoke.taokestudent.presenter.personalcenter.BindAccountActivityPresenter;
import com.taoke.taokestudent.presenter.personalcenter.RegisterActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 绑定账号界面
 */
public class BindAccountActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 手机号 */
    @BindView(R.id.cet_bind_cellphone_number)
    ClearEditText cetCellphoneNumber;
    /* 密码 */
    @BindView(R.id.cet_bind_password)
    ClearEditText cetPassword;
    /* 确认密码 */
    @BindView(R.id.cet_bind_confirm_password)
    ClearEditText cetConfirmPassword;
    /* 验证码 */
    @BindView(R.id.cet_bind_validate_code)
    ClearEditText cetValidateCode;
    /* 发送验证码按钮 */
    @BindView(R.id.svb_bind_sand_validate)
    SendValidateButton svbSandValidate;
    /* 绑定按钮 */
    @BindView(R.id.btn_bind)
    Button btnBind;

    /* 主导器 */
    private BindAccountActivityPresenter presenter;

    /* 服务器返回的验证码 */
    private String bindValidateCode = "";

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
        super.onCreate(savedInstanceState, R.layout.activity_bind_account);
        // 初始化主导器
        presenter = new BindAccountActivityPresenter(this);
        // 初始化验证码发送按钮
        initSendValidateButton();
        // 标题
        tvTitleText.setText(getString(R.string.bind_connection_account));
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
                // 验证手机号
                String phoneNumber = cetCellphoneNumber.getText().toString().trim();
                if (validatePhoneNumber(phoneNumber)) {
                    // 发送验证码
                    presenter.sendValidateCode(phoneNumber);
                }
            }
        });
    }

    /**
     * 绑定
     */
    @OnClick(R.id.btn_bind)
    public void bindAccount() {
        // 获取注册信息
        phoneNumber = cetCellphoneNumber.getText().toString().trim();
        password = cetPassword.getText().toString().trim();
        confirmPassword = cetConfirmPassword.getText().toString().trim();
        validateCode = cetValidateCode.getText().toString().trim();

        // 验证绑定信息
        if (validateBindInfo()) {
            // 绑定
            presenter.bindAccount(phoneNumber, password);
        }
    }

    /**
     * 输入框文本改变后
     *
     * @param editable editable
     */
    @OnTextChanged(value = {R.id.cet_bind_cellphone_number, R.id.cet_bind_password,
            R.id.cet_bind_confirm_password, R.id.cet_bind_validate_code
    }, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(cetCellphoneNumber.getText().toString().trim())
                && !TextUtils.isEmpty(cetPassword.getText().toString().trim())
                && !TextUtils.isEmpty(cetConfirmPassword.getText().toString().trim())
                && !TextUtils.isEmpty(cetValidateCode.getText().toString().trim())) {
            btnBind.setEnabled(true);
            btnBind.setBackgroundResource(R.drawable.bg_btn_enable_selector);
        } else {
            btnBind.setEnabled(false);
            btnBind.setBackgroundResource(R.drawable.bg_btn_unable);
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
    public void setValidateCode(String registerValidateCode) {
        this.bindValidateCode = registerValidateCode;
        // 启动验证码发送按钮的倒计时
        svbSandValidate.startTickWork();
    }

    /**
     * 验证手机号
     *
     * @param phoneNumber 手机号
     */
    private boolean validatePhoneNumber(String phoneNumber) {
        // 验证是否为空
        if (TextUtils.isEmpty(phoneNumber)) {
            InfoUtils.showInfo(this, getString(R.string.phone_number_empty));
            UIUtils.showInputMethod(this, cetCellphoneNumber, true);
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
            InfoUtils.showInfo(this, getString(R.string.phone_number_format_error));
            UIUtils.showInputMethod(this, cetCellphoneNumber, true);
            return false;
        }
        return true;
    }

    /**
     * 验证绑定信息
     */
    private boolean validateBindInfo() {
        // 验证手机号
        if (!validatePhoneNumber(phoneNumber)) {
            return false;
        }

        // 验证登录密码
        if (!password.equals(confirmPassword)) {
            InfoUtils.showInfo(this, getString(R.string.password_inconformity));
            UIUtils.showInputMethod(this, cetConfirmPassword, true);
            return false;
        }

        // 验证验证码
        if (!bindValidateCode.equals(validateCode)) {
            InfoUtils.showInfo(this, getString(R.string.validate_code_inconformity));
            UIUtils.showInputMethod(this, cetValidateCode, true);
            return false;
        }

        return true;
    }

}
