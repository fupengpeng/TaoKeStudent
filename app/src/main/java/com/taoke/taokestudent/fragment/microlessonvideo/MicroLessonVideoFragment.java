package com.taoke.taokestudent.fragment.microlessonvideo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.customerview.BottomTabItemView;
import com.taoke.taokestudent.fragment.BaseFragment;
import com.taoke.taokestudent.manager.ViewNavigatorManager;

import butterknife.BindView;

/**
 * 微课视频Fragment
 */
public class MicroLessonVideoFragment extends BaseFragment {
    /* 主体内容 */
    @BindView(R.id.fl_micro_micro_content)
    FrameLayout flMicroContent;
    /* 微课视频 */
    @BindView(R.id.btiv_micro_lesson)
    BottomTabItemView btivMicroLesson;
    /* 我的微课 */
    @BindView(R.id.btiv_my_lesson)
    BottomTabItemView btivMyLesson;
    /* 我的下载 */
    @BindView(R.id.btiv_my_download)
    BottomTabItemView btivMyDownload;

    /* 子项Fragment集合 */
    private BaseFragment[] fragments;
    /* 上一次显示的Fragment */
    private BaseFragment lastFragment;

    /* 导航菜单管理器 */
    private ViewNavigatorManager navigatorManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载界面
        View view = super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_micro_lesson_video);

        // 初始化子项Fragment
        initSubFragments();

        // 初始化Tab项
        initTabItemView();

        return view;
    }

    /**
     * 初始化子项Fragment
     */
    private void initSubFragments() {
        fragments = new BaseFragment[]{
                new Micro2LessonVIdeoFragment(),
                new MicroMyLessonFragment(),
                new MicroMyDownloadFragment()
        };
    }

    /**
     * 初始化Tab项
     */
    private void initTabItemView() {
        btivMicroLesson.setTitleText(getString(R.string.micro_lesson_video));
        btivMicroLesson.setNormalTitleImageResId(R.drawable.tab_item_micro_lesson_normal);
        btivMicroLesson.setSelectedTitleImageResId(R.drawable.tab_item_micro_lesson_select);
        btivMicroLesson.setNormalTitleTextColorResId(R.color.text_bottom_tab_item_normal);
        btivMicroLesson.setSelectedTitleTextColorResId(R.color.text_bottom_tab_item_select);

        btivMyLesson.setTitleText(getString(R.string.my_lesson));
        btivMyLesson.setNormalTitleImageResId(R.drawable.tab_item_my_lesson_normal);
        btivMyLesson.setSelectedTitleImageResId(R.drawable.tab_item_my_lesson_select);
        btivMyLesson.setNormalTitleTextColorResId(R.color.text_bottom_tab_item_normal);
        btivMyLesson.setSelectedTitleTextColorResId(R.color.text_bottom_tab_item_select);

        btivMyDownload.setTitleText(getString(R.string.my_download));
        btivMyDownload.setNormalTitleImageResId(R.drawable.tab_item_download_normal);
        btivMyDownload.setSelectedTitleImageResId(R.drawable.tab_item_download_select);
        btivMyDownload.setNormalTitleTextColorResId(R.color.text_bottom_tab_item_normal);
        btivMyDownload.setSelectedTitleTextColorResId(R.color.text_bottom_tab_item_select);


        // 菜单项集合
        BottomTabItemView[] menuItemViews = new BottomTabItemView[]{
                btivMicroLesson, btivMyLesson, btivMyDownload};

        // 菜单管理器
        navigatorManager = new ViewNavigatorManager();
        // 设置菜单项集合
        navigatorManager.setNavigatorItems(menuItemViews);
        // 设置监听器
        navigatorManager.setOnMenuItemClickListener(new ViewNavigatorManager.OnNavigatorItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                showFragment(R.id.fl_micro_micro_content, lastFragment, fragments[index]);
                lastFragment = fragments[index];
            }
        });

        // 设置默认选中项
        // 加载主内容，以微课视频作为首页
        navigatorManager.setSelectIndex(0, menuItemViews[0], true);
    }
}
