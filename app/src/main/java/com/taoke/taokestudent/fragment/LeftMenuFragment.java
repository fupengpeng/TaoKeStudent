package com.taoke.taokestudent.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.MainActivity;
import com.taoke.taokestudent.activity.personalcenter.LoginActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.DataEvent;
import com.taoke.taokestudent.customerview.CircleImageView;
import com.taoke.taokestudent.customerview.SimpleMenuItemView;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.manager.ViewNavigatorManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 左侧菜单
 */
public class LeftMenuFragment extends BaseFragment {
    /* 微课视频 */
    @BindView(R.id.miv_micro_lesson_video)
    SimpleMenuItemView mivMicroLessonVideo;
    /* 在线做题 */
    @BindView(R.id.miv_online_exercise)
    SimpleMenuItemView mivOnlineExercise;
    /* 作业与考试 */
    @BindView(R.id.miv_assignment_exam)
    SimpleMenuItemView mivAssignmentExam;
    /* 名师答疑 */
    @BindView(R.id.miv_teacher_answer)
    SimpleMenuItemView mivTeacherAnswer;
    /* 消息 */
    @BindView(R.id.miv_message)
    SimpleMenuItemView mivMessage;
    /* 个人中心 */
    @BindView(R.id.miv_personal_center)
    SimpleMenuItemView mivPersonalCenter;
    /* 用户头像 */
    @BindView(R.id.civ_menu_user_image)
    CircleImageView civMenuUserImage;
    /* 昵称 */
    @BindView(R.id.tv_menu_login_text)
    TextView tvMenuLoginText;
    /* 右箭头 */
    @BindView(R.id.iv_menu_login_right_arrow)
    ImageView ivMenuLoginRightArrow;

    /* 导航菜单管理器 */
    private ViewNavigatorManager navigatorManager;

    /* 侧滑菜单监听器 */
    private LeftMenuFragmentListener listener;

    /* 菜单项集合 */
    private SimpleMenuItemView[] menuItemViews;

    /* 记录上一次选择的菜单项的索引 */
    private int lastIndex;
    /* 标识是否需要关闭侧滑菜单 */
    private boolean toggleMenu = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 订阅
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载界面
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_left_menu);
        // 初始化界面
        initView();
        return view;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        // 微课视频
        mivMicroLessonVideo.setTitleText(getString(R.string.micro_lesson_video));
        mivMicroLessonVideo.setNormalTitleImageResId(R.drawable.menu_item_microlesson_video);
        mivMicroLessonVideo.setSelectedTitleImageResId(R.drawable.menu_item_microlesson_video);
        // 在线做题
        mivOnlineExercise.setTitleText(getString(R.string.online_exercise));
        mivOnlineExercise.setNormalTitleImageResId(R.drawable.menu_item_online_exercise);
        mivOnlineExercise.setSelectedTitleImageResId(R.drawable.menu_item_online_exercise);
        // 作业与考试
        mivAssignmentExam.setTitleText(getString(R.string.assignment_exam));
        mivAssignmentExam.setNormalTitleImageResId(R.drawable.menu_item_assignment_exam);
        mivAssignmentExam.setSelectedTitleImageResId(R.drawable.menu_item_assignment_exam);
        // 名师答疑
        mivTeacherAnswer.setTitleText(getString(R.string.teacher_answer));
        mivTeacherAnswer.setNormalTitleImageResId(R.drawable.menu_item_teacher_answer);
        mivTeacherAnswer.setSelectedTitleImageResId(R.drawable.menu_item_teacher_answer);
        // 消息
        mivMessage.setTitleText(getString(R.string.message));
        mivMessage.setNormalTitleImageResId(R.drawable.menu_item_message);
        mivMessage.setSelectedTitleImageResId(R.drawable.menu_item_message);
        // 个人中心
        mivPersonalCenter.setTitleText(getString(R.string.personal_center));
        mivPersonalCenter.setNormalTitleImageResId(R.drawable.menu_item_personal_center);
        mivPersonalCenter.setSelectedTitleImageResId(R.drawable.menu_item_personal_center);

        // 菜单项集合
        menuItemViews = new SimpleMenuItemView[]{
                mivMicroLessonVideo, mivOnlineExercise, mivAssignmentExam,
                mivTeacherAnswer, mivMessage, mivPersonalCenter};

        // 菜单管理器
        navigatorManager = new ViewNavigatorManager();
        // 设置菜单项集合
        navigatorManager.setNavigatorItems(menuItemViews);
        // 设置监听器
        navigatorManager.setOnMenuItemClickListener(new ViewNavigatorManager.OnNavigatorItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                // 未登录状态下，选择除微课视频以外的菜单项时，
                // 保持原选中项，侧滑菜单不关闭
                if (MyApplication.getInstance().getUser() == null) {
                    if (index == Consts.MenuItemIndex.MICRO_LESSON_VIDEO) {
                        lastIndex = index;
                    } else {
                        navigatorManager.setSelectIndex(lastIndex, null, false);
                        toggleMenu = false;
                    }
                }

                // 通知侧滑菜单监听器
                if (listener != null) {
                    listener.onMenuItemSelected(v, index);
                }

                // 关闭侧滑菜单
                if (toggleMenu) {
                    ((MainActivity) getActivity()).closeLeftMenu();
                }
                toggleMenu = true;
            }
        });

        // 设置默认选中项
        // 加载主内容，以微课视频作为首页
        setSelectIndex(0, true);

        // 显示用户信息
        showUserInfo();
    }

    /**
     * 接收事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataEvent(DataEvent dataEvent) {
        switch (dataEvent.getType()) {
            case Consts.EventType.EVENT_UPDATE_USER: // 更新用户数据
            case Consts.EventType.EVENT_LOGIN_SUCCESS: // 登录成功
                // 显示用户信息
                showUserInfo();
                break;
            case Consts.EventType.EVENT_EXIT_ACCOUNT: // 退出账号
                // 显示默认信息
                showDefaultInfo();
                // 显示微课视频
                navigatorManager.setSelectIndex(0, menuItemViews[0], true);
                break;
        }
    }

    /**
     * 显示默认信息
     */
    private void showDefaultInfo() {
        // 头像
        civMenuUserImage.setImageResource(R.drawable.user_unlogin);
        // 昵称
        tvMenuLoginText.setText("登录");
        // 右箭头
        ivMenuLoginRightArrow.setVisibility(View.VISIBLE);
    }

    /**
     * 显示用户信息
     */
    private void showUserInfo() {
        User user = MyApplication.getInstance().getUser();
        if (user == null) {
            return;
        }
        // 加载头像
        if (!TextUtils.isEmpty(user.getHead_image())) {
            // 加载头像
            Picasso.with(getActivity()).load(user.getHead_image()).into(civMenuUserImage);
        } else {
            civMenuUserImage.setImageResource(R.drawable.user_unlogin);
        }
        // 显示昵称
        if (!TextUtils.isEmpty(user.getNickname())) {
            String nickName = user.getNickname();
            if (nickName.length() > 10) {
                nickName = nickName.substring(0, 10) + "...";
            }
            tvMenuLoginText.setText(nickName);
        } else if (!TextUtils.isEmpty(user.getRealname())) {
            String realname = user.getRealname();
            if (realname.length() > 10) {
                realname = realname.substring(0, 10) + "...";
            }
            tvMenuLoginText.setText(realname);
        } else {
            tvMenuLoginText.setText("");
        }
        // 隐藏右箭头
        ivMenuLoginRightArrow.setVisibility(View.GONE);

    }

    /**
     * 设置选中项
     *
     * @param index          选中项索引
     * @param notifyListener 是否通知监听器
     */
    public void setSelectIndex(int index, boolean notifyListener) {
        if (navigatorManager != null) {
            navigatorManager.setSelectIndex(index, menuItemViews[index], notifyListener);
        }
    }

    /**
     * 设置侧滑菜单监听器
     *
     * @param listener 侧滑菜单监听器
     */
    public void setLeftMenuFragmentListener(LeftMenuFragmentListener listener) {
        this.listener = listener;
    }

    /**
     * 侧滑菜单监听器
     */
    public interface LeftMenuFragmentListener {
        /**
         * 当菜单项被选中时调用
         *
         * @param v     被选中的控件
         * @param index 菜单项索引
         */
        void onMenuItemSelected(View v, int index);
    }

    /**
     * 登录
     */
    @OnClick(R.id.ll_menu_login)
    public void login() {
        if (MyApplication.getInstance().getUser() == null) {
            startActivity(LoginActivity.class);
        }
    }

    @Override
    public void onDestroy() {
        // 解除订阅
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
