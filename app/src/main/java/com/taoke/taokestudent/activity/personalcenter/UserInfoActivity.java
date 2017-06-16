package com.taoke.taokestudent.activity.personalcenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.customerview.CircleImageView;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.presenter.personalcenter.UserInfoActivityPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户信息界面
 */
public class UserInfoActivity extends BaseActivity {
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 头像ImageView */
    @BindView(R.id.civ_user_info_photo)
    CircleImageView civUserInfoPhoto;
    /* 昵称 */
    @BindView(R.id.tv_user_info_nickname)
    TextView tvUserInfoNickname;
    /* 真实姓名 */
    @BindView(R.id.tv_user_info_realname)
    TextView tvUserInfoRealname;
    /* 性别 */
    @BindView(R.id.tv_user_info_sex)
    TextView tvUserInfoSex;
    /* 年级 */
    @BindView(R.id.tv_user_info_nianji)
    TextView tvUserInfoNianji;

    // 拍照临时图片
    private String tempPhotoPath;
    // 相册选图标记
    private static final int GALLERY_REQUEST_CODE = 0;
    // 相机拍照标记
    private static final int CAMERA_REQUEST_CODE = 1;
    // 显示图片
    public static final int SHOW_PICTURE = 2;

    /* 主导器 */
    private UserInfoActivityPresenter presenter;
    /* 用户信息 */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_info);
        // 拍照临时图片路径
        tempPhotoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "tk_head_photo.jpeg";
        // 创建主导器
        presenter = new UserInfoActivityPresenter(this);
        // 初始化界面
        initViews();
        //订阅
        EventBus.getDefault().register(this);
        // 标题
        tvTitleText.setText(getString(R.string.user_info));
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        user = MyApplication.getInstance().getUser();
        // 头像
        if (!TextUtils.isEmpty(user.getHead_image())) {
            Picasso.with(this).load(user.getHead_image()).into(civUserInfoPhoto);
        } else {
            civUserInfoPhoto.setImageResource(R.drawable.user_unlogin);
        }
        // 昵称
        if (!TextUtils.isEmpty(user.getNickname())) {
            String nickName = user.getNickname();
            if (nickName.length() > 6) {
                nickName = nickName.substring(0, 6) + "...";
            }
            tvUserInfoNickname.setText(nickName);
        } else {
            tvUserInfoNickname.setText("");
        }
        // 真实姓名
        if (!TextUtils.isEmpty(user.getRealname())) {
            String realname = user.getRealname();
            if (realname.length() > 6) {
                realname = realname.substring(0, 6) + "...";
            }
            tvUserInfoRealname.setText(realname);
        } else {
            tvUserInfoRealname.setText("");
        }
        // 性别
        if (!TextUtils.isEmpty(user.getSex())) {
            if (Consts.Sex.MALE.equals(user.getSex())) {
                tvUserInfoSex.setText(getString(R.string.male));
            } else {
                tvUserInfoSex.setText(getString(R.string.female));
            }
        } else {
            tvUserInfoSex.setText("");
        }
        // 年级
        if (!TextUtils.isEmpty(user.getBanji())) {
            tvUserInfoNianji.setText(user.getBanji());
        } else {
            tvUserInfoNianji.setText("");
        }
    }

    /**
     * 修改昵称
     */
    @OnClick(R.id.ll_nickname)
    public void updateNickname() {
        Intent intent = new Intent(this, UpdateUserInfoActivity.class);
        intent.putExtra(Consts.IntentExtraKey.USER_INFO, Consts.IntentExtraValue.NICKNAME);
        startActivity(intent);
    }

    /**
     * 修改真实姓名
     */
    @OnClick(R.id.ll_realname)
    public void updateRealname() {
        Intent intent = new Intent(this, UpdateUserInfoActivity.class);
        intent.putExtra(Consts.IntentExtraKey.USER_INFO, Consts.IntentExtraValue.REALNAME);
        startActivity(intent);
    }

    /**
     * 修改性别
     */
    @OnClick(R.id.ll_sex)
    public void updateSex() {
        Intent intent = new Intent(this, UpdateUserInfoActivity.class);
        intent.putExtra(Consts.IntentExtraKey.USER_INFO, Consts.IntentExtraValue.SEX);
        startActivity(intent);
    }

    /**
     * 修改年级
     */
    @OnClick(R.id.ll_nianji)
    public void updateNianji() {
        Intent intent = new Intent(this, UpdateUserInfoActivity.class);
        intent.putExtra(Consts.IntentExtraKey.USER_INFO, Consts.IntentExtraValue.NIANJI);
        startActivity(intent);
    }

    /**
     * 修改密码
     */
    @OnClick(R.id.ll_update_password)
    public void updatePassword() {
        startActivity(UpdatePasswordActivity.class);
    }

    /**
     * 更换头像
     */
    @OnClick(R.id.ll_user_info_photo)
    public void changePhoto() {
        // 显示选择窗口
        showWindow();
    }

    /**
     * 显示选择窗口
     */
    private void showWindow() {
        //弹出对话框
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        //自定义dialog的ui
        Window window = dialog.getWindow();
        View dialogView = View.inflate(this, R.layout.dialog_select_image, null);
        LinearLayout llPhotoGraph = (LinearLayout) dialogView.findViewById(R.id.ll_photograph);
        llPhotoGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 拍照
                takePhoto();
                dialog.dismiss();
            }
        });

        LinearLayout llSelectFromAlbum = (LinearLayout) dialogView.findViewById(R.id.ll_select_from_album);
        llSelectFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从相册中选择
                selectFromAlbum();
                dialog.dismiss();
            }
        });

        window.setContentView(dialogView);
    }

    /**
     * 从相册中选择
     */
    private void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempPhotoPath)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempPhotoPath)));
        startActivityForResult(intent, SHOW_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:   // 调用相机拍照
                    // 裁剪相片
                    cropPhoto(Uri.fromFile(new File(tempPhotoPath)));
                    break;

                case GALLERY_REQUEST_CODE:   // 从相册中选择
                    // 裁剪相片
                    cropPhoto(data.getData());
                    break;

                case SHOW_PICTURE: // 显示图片
                    try {
                        File file = new File(tempPhotoPath);
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(Uri.fromFile(file)));
                        file.delete();
                        // 修改用户头像
                        user.setHeadImage(bitmap);
                        presenter.updateUserInfo(user);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

    /**
     * 接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataEvent(DataEvent dataEvent) {
        switch (dataEvent.getType()) {
            case Consts.EventType.EVENT_UPDATE_USER: // 更新用户信息
                // 初始化界面
                initViews();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //解除订阅
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
