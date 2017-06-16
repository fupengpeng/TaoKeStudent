package com.taoke.taokestudent.activity.personalcenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.presenter.personalcenter.UpdateUserInfoActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 修改个人信息界面
 */
public class UpdateUserInfoActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 昵称 */
    @BindView(R.id.et_nickname)
    EditText etNickname;
    /* 昵称区域 */
    @BindView(R.id.ll_update_nickname)
    LinearLayout llNickname;
    /* 真实姓名 */
    @BindView(R.id.et_realname)
    EditText etRealname;
    /* 真实姓名区域 */
    @BindView(R.id.ll_realname)
    LinearLayout llRealname;
    /* 男 */
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    /* 女 */
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    /* 性别区域 */
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    /* 年级 */
    @BindView(R.id.sp_nianji)
    Spinner spNianji;
    /* 年级区域 */
    @BindView(R.id.ll_nianji)
    LinearLayout llNianji;
    /* 确定 */
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    /* 用户 */
    private User user;
    private String extra;

    /* 主导器 */
    private UpdateUserInfoActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_update_user_info);
        // 初始化界面
        initViews();
        // 创建主导器
        presenter = new UpdateUserInfoActivityPresenter(this);
        // 标题
        tvTitleText.setText(getString(R.string.update_user_info));
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        user = MyApplication.getInstance().getUser();
        extra = getIntent().getStringExtra(Consts.IntentExtraKey.USER_INFO);
        // 昵称
        if (Consts.IntentExtraValue.NICKNAME.equals(extra)) {
            llNickname.setVisibility(View.VISIBLE);
            etNickname.setText(user.getNickname());
            return;
        }
        // 真实姓名
        if (Consts.IntentExtraValue.REALNAME.equals(extra)) {
            llRealname.setVisibility(View.VISIBLE);
            etRealname.setText(user.getRealname());
            if (TextUtils.isEmpty(user.getRealname())) {
                btnSubmit.setEnabled(false);
                btnSubmit.setBackgroundResource(R.drawable.bg_btn_unable);
            }
            return;
        }
        // 性别
        if (Consts.IntentExtraValue.SEX.equals(extra)) {
            llSex.setVisibility(View.VISIBLE);
            if (!Consts.Sex.FEMALE.equals(user.getSex())) {
                rbMale.setChecked(true);
            } else {
                rbFemale.setChecked(true);
            }
            return;
        }
        // 年级
        if (Consts.IntentExtraValue.NIANJI.equals(extra)) {
            llNianji.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(user.getBanji())) {
                String[] datas = getResources().getStringArray(R.array.sp_nj);
                for (int i = 0; i < datas.length; i++) {
                    if (datas[i].equals(user.getBanji())) {
                        spNianji.setSelection(i);
                        break;
                    }
                }
            }
            return;
        }
    }

    /**
     * 真实姓名不能为空
     */
    @OnTextChanged(value = R.id.et_realname, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onChangeRealname() {
        if (!TextUtils.isEmpty(etRealname.getText().toString().trim())) {
            btnSubmit.setEnabled(true);
            btnSubmit.setBackgroundResource(R.drawable.bg_btn_enable_selector);
        } else {
            btnSubmit.setEnabled(false);
            btnSubmit.setBackgroundResource(R.drawable.bg_btn_unable);
        }
    }

    /**
     * 确定
     */
    @OnClick(R.id.btn_submit)
    public void submit() {
        // 昵称
        if (Consts.IntentExtraValue.NICKNAME.equals(extra)) {
            String nickname = etNickname.getText().toString().trim();
            user.setNickname(nickname);
        }
        // 真实姓名
        if (Consts.IntentExtraValue.REALNAME.equals(extra)) {
            String realname = etRealname.getText().toString().trim();
            user.setRealname(realname);
        }
        // 性别
        if (Consts.IntentExtraValue.SEX.equals(extra)) {
            if (rbMale.isChecked()) {
                user.setSex(Consts.Sex.MALE);
            } else {
                user.setSex(Consts.Sex.FEMALE);
            }
        }
        // 年级
        if (Consts.IntentExtraValue.NIANJI.equals(extra)) {
            String nianji = spNianji.getSelectedItem().toString();
            user.setBanji(nianji);
        }

        user.setHeadImage(null);
        presenter.updateUserInfo(user);
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

}
