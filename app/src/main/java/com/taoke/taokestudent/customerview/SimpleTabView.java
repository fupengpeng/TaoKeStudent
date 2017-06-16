package com.taoke.taokestudent.customerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Tab项
 * <p>
 * 构成：一个TextView
 */
public class SimpleTabView extends NavigatorBaseItem {
    // Tab名
    private TextView tvTabName = null;

    // 未选中时的字体颜色
    private int normalTextColor = 0;
    // 被选中时的字体颜色
    private int selectedTextColor = 0;

    // 未选中时的背景图片
    private int normalBackgroundImage = 0;
    // 被选中时的背景图片
    private int selectedBackgroundImage = 0;

    public SimpleTabView(Context context) {
        super(context);
        // 初始化
        init();
    }

    public SimpleTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        tvTabName = new TextView(getContext());
        tvTabName.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        this.addView(tvTabName, params);
        this.setGravity(Gravity.CENTER);
        setSelectedState(false);
    }

    /**
     * 设置该项的选中状态
     *
     * @param select
     */
    @Override
    public void setSelectedState(boolean select) {
        if (select) { // 选中
            // 设置字体颜色
            if (selectedTextColor != 0) {
                this.tvTabName.setTextColor(selectedTextColor);
            }
            // 设置背景图片
            if (selectedBackgroundImage != 0) {
                this.setBackgroundResource(selectedBackgroundImage);
            }

        } else { // 未选中
            // 设置字体颜色
            if (normalTextColor != 0) {
                this.tvTabName.setTextColor(normalTextColor);
            }
            // 设置背景图片
            if (normalBackgroundImage != 0) {
                this.setBackgroundResource(normalBackgroundImage);
            }
        }

        // 设置选中状态
        this.setSelected(select);
        selected = select;
    }

    /**
     * 获取该项的选中状态
     *
     * @return
     */
    @Override
    public boolean getSelectedState() {
        return selected;
    }

    /**
     * 设置Tab名
     *
     * @param name
     */
    public void setTabName(String name) {
        if (name != null) {
            this.tvTabName.setText(name);
        }
    }

    /**
     * 设置Tab名的文本字号(sp)
     *
     * @param textSize
     */
    public void setTabNameTextSize(int textSize) {
        this.tvTabName.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    /**
     * 设置未选中时的字体颜色
     *
     * @param normalTextColorResId
     */
    public void setNormalTextColor(int normalTextColorResId) {
        this.normalTextColor = getResources().getColor(normalTextColorResId);
    }

    /**
     * 设置被选中时的字体颜色
     *
     * @param selectedTextColorResId
     */
    public void setSelectedTextColor(int selectedTextColorResId) {
        this.selectedTextColor = getResources().getColor(selectedTextColorResId);
    }

    /**
     * 设置未选中时的背景图片
     *
     * @param normalBackgroundImageResId
     */
    public void setNormalBackgroundImage(int normalBackgroundImageResId) {
        this.normalBackgroundImage = normalBackgroundImageResId;
    }

    /**
     * 设置被选中时的背景图片
     *
     * @param selectedBackgroundImageResId
     */
    public void setSelectedBackgroundImage(int selectedBackgroundImageResId) {
        this.selectedBackgroundImage = selectedBackgroundImageResId;
    }

}
