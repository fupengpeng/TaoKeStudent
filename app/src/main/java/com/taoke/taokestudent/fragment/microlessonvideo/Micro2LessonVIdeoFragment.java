package com.taoke.taokestudent.fragment.microlessonvideo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.MainActivity;
import com.taoke.taokestudent.activity.microlessonvideo.MicroLessonVideoSelectorActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.customerview.SimpleTabView;
import com.taoke.taokestudent.entity.MicroLessonVideoBaseClass;
import com.taoke.taokestudent.fragment.BaseFragment;
import com.taoke.taokestudent.manager.ViewNavigatorManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 微课视频-微课视频
 */
public class Micro2LessonVIdeoFragment extends BaseFragment {
    /* 微课视频 */
    @BindView(R.id.stv_micro_micro_micro_lesson)
    SimpleTabView stvMicroMicroMicroLesson;
    /* 习题微课 */
    @BindView(R.id.stv_micro_micro_micro_exercise)
    SimpleTabView stvMicroMicroMicroExercise;

    /* 子项Fragment集合 */
    private Micro2LessonVideoItemBaseFragment[] fragments;
    /* 上一次显示的Fragment */
    private Micro2LessonVideoItemBaseFragment lastFragment;

    /* 导航菜单管理器 */
    private ViewNavigatorManager navigatorManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载界面
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_micro_2_lesson_video);
        // 初始化子项Fragment
        initSubFragments();
        // 初始化Tab项
        initTabItemView();

        return view;
    }

    /**
     * 打开侧滑菜单
     */
    @OnClick(R.id.iv_menu)
    public void openLeftMenu() {
        ((MainActivity) getActivity()).openLeftMenu();
    }

    /**
     * 筛选课程
     */
    @OnClick(R.id.iv_selector)
    public void selectLesson() {
        // 获取基础分类
        MicroLessonVideoBaseClass baseClass = lastFragment.getMicroLessonVideoBaseClass();
        // 启动筛选课程界面
        Intent intent = new Intent(getActivity(), MicroLessonVideoSelectorActivity.class);
        intent.putExtra(Consts.IntentExtraKey.MICRO_BASE_CLASS, baseClass);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == MainActivity.RESULT_OK) {
                MicroLessonVideoBaseClass baseClass = data.getParcelableExtra(Consts.IntentExtraKey.MICRO_BASE_CLASS);
                lastFragment.setMicroLessonVideoBaseClass(baseClass);
            }
        }
    }

    /**
     * 初始化子项Fragment
     */
    private void initSubFragments() {
        fragments = new Micro2LessonVideoItemBaseFragment[]{
                new Micro3LessonVideoFragment(),
                new Micro2ExerciseFragment()
        };
    }

    /**
     * 初始化Tab项
     */
    private void initTabItemView() {
        stvMicroMicroMicroLesson.setTabName(getString(R.string.micro_lesson_video));
        stvMicroMicroMicroLesson.setTabNameTextSize(16);
        stvMicroMicroMicroLesson.setNormalTextColor(R.color.text_micro_tab_item_normal);
        stvMicroMicroMicroLesson.setSelectedTextColor(R.color.text_micro_tab_item_select);
        stvMicroMicroMicroLesson.setNormalBackgroundImage(R.drawable.simple_tab_iem_left_normal);
        stvMicroMicroMicroLesson.setSelectedBackgroundImage(R.drawable.simple_tab_iem_left_select);


        stvMicroMicroMicroExercise.setTabName(getString(R.string.micro_exercise));
        stvMicroMicroMicroExercise.setTabNameTextSize(16);
        stvMicroMicroMicroExercise.setNormalTextColor(R.color.text_micro_tab_item_normal);
        stvMicroMicroMicroExercise.setSelectedTextColor(R.color.text_micro_tab_item_select);
        stvMicroMicroMicroExercise.setNormalBackgroundImage(R.drawable.simple_tab_iem_right_normal);
        stvMicroMicroMicroExercise.setSelectedBackgroundImage(R.drawable.simple_tab_iem_right_select);


        // 菜单项集合
        SimpleTabView[] simpleTabViews = new SimpleTabView[]{
                stvMicroMicroMicroLesson, stvMicroMicroMicroExercise};

        // 菜单管理器
        navigatorManager = new ViewNavigatorManager();
        // 设置菜单项集合
        navigatorManager.setNavigatorItems(simpleTabViews);
        // 设置监听器
        navigatorManager.setOnMenuItemClickListener(new ViewNavigatorManager.OnNavigatorItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                showFragment(R.id.fl_micro_3_content, lastFragment, fragments[index]);
                lastFragment = fragments[index];
            }
        });

        // 设置默认选中项
        // 加载主内容，以微课视频作为首页
        navigatorManager.setSelectIndex(0, simpleTabViews[0], true);
    }
}
