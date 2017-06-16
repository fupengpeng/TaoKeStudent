package com.taoke.taokestudent.activity.personalcenter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.customerview.ClearEditText;
import com.taoke.taokestudent.presenter.personalcenter.UpdatePasswordActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;
import com.taoke.taokestudent.util.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 修改密码
 */
public class UpdatePasswordActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 原密码 */
    @BindView(R.id.cet_old_password)
    ClearEditText cetOldPassword;
    /* 新密码 */
    @BindView(R.id.cet_new_password)
    ClearEditText cetNewPassword;
    /* 确认密码 */
    @BindView(R.id.cet_confirm_password)
    ClearEditText cetConfirmPassword;
    /* 确认 */
    @BindView(R.id.btn_ensure)
    Button btnEnsure;

    /* 主导器 */
    private UpdatePasswordActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_update_password);
        presenter = new UpdatePasswordActivityPresenter(this);
        // 标题
        tvTitleText.setText(getString(R.string.update_password));
    }

    /**
     * 输入框文本改变后
     *
     * @param editable editable
     */
    @OnTextChanged(value = {R.id.cet_old_password, R.id.cet_new_password,
            R.id.cet_confirm_password}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(cetOldPassword.getText().toString().trim())
                && !TextUtils.isEmpty(cetNewPassword.getText().toString().trim())
                && !TextUtils.isEmpty(cetConfirmPassword.getText().toString().trim())) {
            btnEnsure.setEnabled(true);
            btnEnsure.setBackgroundResource(R.drawable.bg_btn_enable_selector);
        } else {
            btnEnsure.setEnabled(false);
            btnEnsure.setBackgroundResource(R.drawable.bg_btn_unable);
        }
    }

    /**
     * 确认
     */
    @OnClick(R.id.btn_ensure)
    public void ensure() {
        String oldPwd = cetOldPassword.getText().toString().trim();
        String newPws = cetNewPassword.getText().toString().trim();
        String confirmPassword = cetConfirmPassword.getText().toString().trim();

        // 验证登录密码
        if (!newPws.equals(confirmPassword)) {
            InfoUtils.showInfo(this, getString(R.string.password_inconformity));
            UIUtils.showInputMethod(this, cetConfirmPassword, true);
            return;
        }

        presenter.updatePassword(oldPwd, newPws);
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

}
