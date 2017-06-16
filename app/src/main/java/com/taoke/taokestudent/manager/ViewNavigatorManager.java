package com.taoke.taokestudent.manager;

import android.view.View;
import android.view.View.OnClickListener;

import com.taoke.taokestudent.customerview.NavigatorBaseItem;

/**
 * 界面导航管理器
 */
public class ViewNavigatorManager {
    // 菜单项集合
    private NavigatorBaseItem[] navigatorItems = null;
    // 当前选中菜单项索引
    private int currentIndex = -1;

    // 菜单项点击监听器
    private OnNavigatorItemClickListener listener = null;

    /**
     * 设置菜单项集合
     *
     * @param items 菜单项集合
     */
    public void setNavigatorItems(NavigatorBaseItem[] items) {
        if (items != null && items.length > 0) {
            navigatorItems = items;
            for (int i = 0; i < navigatorItems.length; i++) {
                // 将ID设置为其索引
                navigatorItems[i].setId(i);
                // 设置点击监听器
                navigatorItems[i].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 设置选中项
                        setSelectIndex(v.getId(), v, true);
                    }
                });
                // 设置未选中
                navigatorItems[i].setSelectedState(false);
            }
        }
    }

    /**
     * 设置选中项
     *
     * @param index          选中项索引
     * @param v              选中的控件
     * @param notifyListener 是否通知监听器
     * @return true：设置成功；false：设置失败
     */
    public boolean setSelectIndex(int index, View v, boolean notifyListener) {
        if (navigatorItems != null && navigatorItems.length > 0
                && index < navigatorItems.length && index >= 0) {
            if (index != currentIndex) {
                // 变换选中项
                navigatorItems[index].setSelectedState(true);
                if (currentIndex != -1) {
                    navigatorItems[currentIndex].setSelectedState(false);
                }
                currentIndex = index;

                // 通知菜单项点击监听器
                if (listener != null && notifyListener) {
                    listener.onItemClick(v, index);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前选中菜单项索引
     *
     * @return 当前选中菜单项索引
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * 设置菜单项点击监听器
     *
     * @param listener 菜单项点击监听器
     */
    public void setOnMenuItemClickListener(OnNavigatorItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 菜单项点击监听器
     */
    public interface OnNavigatorItemClickListener {
        void onItemClick(View v, int index);
    }

}
