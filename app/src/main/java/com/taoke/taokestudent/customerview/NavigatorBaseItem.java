package com.taoke.taokestudent.customerview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 导航菜单项的父类
 */
public abstract class NavigatorBaseItem extends LinearLayout {

    // 标识该导航项是否被选中
    protected boolean selected = false;

    public NavigatorBaseItem(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
    }

    public NavigatorBaseItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigatorBaseItem(Context context) {
        super(context);
    }

    /**
     * 设置该项的选中状态
     *
     * @param select
     */
    public abstract void setSelectedState(boolean select);

    /**
     * 获取该项的选中状态
     *
     * @return
     */
    public abstract boolean getSelectedState();

}
