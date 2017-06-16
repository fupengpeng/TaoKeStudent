package com.taoke.taokestudent.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;
import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.personalcenter.LoginActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.fragment.AssignmentExamFragment;
import com.taoke.taokestudent.fragment.BaseFragment;
import com.taoke.taokestudent.fragment.LeftMenuFragment;
import com.taoke.taokestudent.fragment.MessageFragment;
import com.taoke.taokestudent.fragment.microlessonvideo.MicroLessonVideoFragment;
import com.taoke.taokestudent.fragment.onlineexercise.OnlineExerciseFragment;
import com.taoke.taokestudent.fragment.personalcenter.PersonalCenterFragment;
import com.taoke.taokestudent.fragment.TeacherAnswerFragment;

import java.lang.reflect.Field;

import butterknife.BindView;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {
    /* 主界面布局 */
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    /* 侧滑菜单布局 */
    @BindView(R.id.fl_left_menu)
    FrameLayout flLeftMenu;

    /* 侧滑菜单 */
    private LeftMenuFragment leftMenuFragment;

    /* 微课视频界面 */
    private MicroLessonVideoFragment microLessonVideoFragment;
    /* 在线做题界面 */
    private OnlineExerciseFragment onlineExerciseFragment;
    /* 作业与考试界面 */
    private AssignmentExamFragment assignmentExamFragment;
    /* 名师答疑界面 */
    private TeacherAnswerFragment teacherAnswerFragment;
    /* 消息界面 */
    private MessageFragment messageFragment;
    /* 个人中心界面 */
    private PersonalCenterFragment personalCenterFragment;
    /* 上一次显示的Fragment */
    private BaseFragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        // 初始化侧滑菜单
        initLeftMenu();
    }

    /**
     * 初始化侧滑菜单
     */
    private void initLeftMenu() {
        // 加载侧滑菜单
        leftMenuFragment = new LeftMenuFragment();
        replaceFragment(leftMenuFragment, R.id.fl_left_menu);
        // 设置侧滑菜单监听器
        leftMenuFragment.setLeftMenuFragmentListener(new InnerLeftMenuFragmentListener());

        // 全屏滑动
        setDrawerLeftEdgeSize(this, dlMain, 0.1f);

        // 主内容跟随菜单移动
        dlMain.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = dlMain.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                //改变DrawLayout侧栏透明度，若不需要效果可以不设置
                ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                ViewHelper.setTranslationX(mContent,
                        mMenu.getMeasuredWidth() * (1 - scale));
                ViewHelper.setPivotX(mContent, 0);
                ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                mContent.invalidate();
            }
        });
    }

    /**
     * 解决DrawerLayout不能全屏滑动的问题
     */
    private void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    /**
     * 侧滑菜单监听器
     */
    private class InnerLeftMenuFragmentListener implements LeftMenuFragment.LeftMenuFragmentListener {
        @Override
        public void onMenuItemSelected(View v, int index) {
            switch (index) {
                case Consts.MenuItemIndex.MICRO_LESSON_VIDEO:
                    // 微课视频
                    showMicroLessonVideoView();
                    break;
                case Consts.MenuItemIndex.ONLINE_EXERCISE:
                    // 在线做题
                    showOnlineExerciseView();
                    break;
                case Consts.MenuItemIndex.ASSIGNMENT_EXAM:
                    // 作业与考试
                    showAssignmentExamView();
                    break;
                case Consts.MenuItemIndex.TEACHER_ANSWER:
                    // 名师答疑
                    showTeacherAnswerView();
                    break;
                case Consts.MenuItemIndex.MESSAGE:
                    // 消息
                    showMessageView();
                    break;
                case Consts.MenuItemIndex.PERSONAL_CENTER:
                    // 个人中心
                    showPersonalCenterView();
                    break;
            }
        }
    }

    /**
     * 显示微课视频界面
     */
    private void showMicroLessonVideoView() {
        if (microLessonVideoFragment == null) {
            microLessonVideoFragment = new MicroLessonVideoFragment();
        }
        showFragment(R.id.fl_main_content, lastFragment, microLessonVideoFragment);
        lastFragment = microLessonVideoFragment;
    }

    /**
     * 显示在线做题界面
     */
    private void showOnlineExerciseView() {
        // 判断是否登录
        if (MyApplication.getInstance().getUser() != null) {
            // 已经登录
            if (onlineExerciseFragment == null) {
                onlineExerciseFragment = new OnlineExerciseFragment();
            }
            showFragment(R.id.fl_main_content, lastFragment, onlineExerciseFragment);
            lastFragment = onlineExerciseFragment;
        } else {
            // 未登录
            startActivity(LoginActivity.class);
        }
    }

    /**
     * 显示作业与考试界面
     */
    private void showAssignmentExamView() {
        // 判断是否登录
        if (MyApplication.getInstance().getUser() != null) {
            // 已经登录
            if (assignmentExamFragment == null) {
                assignmentExamFragment = new AssignmentExamFragment();
            }
            showFragment(R.id.fl_main_content, lastFragment, assignmentExamFragment);
            lastFragment = assignmentExamFragment;
        } else {
            // 未登录
            startActivity(LoginActivity.class);
        }
    }

    /**
     * 显示名师答疑界面
     */
    private void showTeacherAnswerView() {
        // 判断是否登录
        if (MyApplication.getInstance().getUser() != null) {
            // 已经登录
            if (teacherAnswerFragment == null) {
                teacherAnswerFragment = new TeacherAnswerFragment();
            }
            showFragment(R.id.fl_main_content, lastFragment, teacherAnswerFragment);
            lastFragment = teacherAnswerFragment;
        } else {
            // 未登录
            startActivity(LoginActivity.class);
        }
    }

    /**
     * 显示消息界面
     */
    private void showMessageView() {
        // 判断是否登录
        if (MyApplication.getInstance().getUser() != null) {
            // 已经登录
            if (messageFragment == null) {
                messageFragment = new MessageFragment();
            }
            showFragment(R.id.fl_main_content, lastFragment, messageFragment);
            lastFragment = messageFragment;
        } else {
            // 未登录
            startActivity(LoginActivity.class);
        }
    }

    /**
     * 显示个人中心界面
     */
    private void showPersonalCenterView() {
        // 判断是否登录
        if (MyApplication.getInstance().getUser() != null) {
            // 已经登录
            if (personalCenterFragment == null) {
                personalCenterFragment = new PersonalCenterFragment();
            }
            showFragment(R.id.fl_main_content, lastFragment, personalCenterFragment);
            lastFragment = personalCenterFragment;
        } else {
            // 未登录
            startActivity(LoginActivity.class);
        }
    }

    /**
     * 打开侧滑菜单
     */
    public void openLeftMenu() {
        dlMain.openDrawer(flLeftMenu);
    }

    /**
     * 关闭侧滑菜单
     */
    public void closeLeftMenu() {
        dlMain.closeDrawer(flLeftMenu);
    }


}
