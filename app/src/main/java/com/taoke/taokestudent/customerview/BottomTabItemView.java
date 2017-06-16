package com.taoke.taokestudent.customerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoke.taokestudent.R;

/**
 * 底部Tab项
 */
public class BottomTabItemView extends NavigatorBaseItem {
    // 菜单项界面
    private View view = null;
    // 标题图片
    private ImageView imageView = null;
    // 标题
    private TextView tvTitle = null;

    // 未选中时的标题颜色的资源Id
    private int normalTitleTextColorResId = getResources().getColor(
            R.color.text_bottom_tab_item_normal);
    // 被选中时的标题颜色的资源Id
    private int selectedTitleTextColorResId = getResources().getColor(
            R.color.text_bottom_tab_item_select);

    // 未选中时的标题图片的资源Id
    private int normalTitleImageResId = 0;
    // 被选中时的标题图片的资源Id
    private int selectedTitleImageResId = 0;

    // 未选中时的背景颜色的资源Id
    private int normalBackgroundColorResId = 0;
    // 被选中时的背景颜色的资源Id
    private int selectedBackgroundColorResId = 0;

    // 标识该菜单项的是否可以被选中
    private boolean canChangeState = true;

    public BottomTabItemView(Context context) {
        super(context);
        // 初始化
        init();
    }

    public BottomTabItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 加载界面
        view = LayoutInflater.from(getContext()).inflate(
                R.layout.view_bottom_tab_item, null);
        imageView = (ImageView) view
                .findViewById(R.id.iv_bottom_tab_item_image);
        tvTitle = (TextView) view
                .findViewById(R.id.tv_bottom_tab_item_title);
        LinearLayout.LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        this.addView(view, params);
    }

    /**
     * 设置该项的选中状态
     *
     * @param select
     */
    public void setSelectedState(boolean select) {
        if (canChangeState) {
            if (select) { // 选中
                // 设置标题图片
                if (selectedTitleImageResId != 0) {
                    imageView.setImageResource(selectedTitleImageResId);
                }
                // 设置标题文字颜色
                if (selectedTitleTextColorResId != 0) {
                    tvTitle.setTextColor(selectedTitleTextColorResId);
                }
                // 设置背景颜色
                if (selectedBackgroundColorResId != 0) {
                    view.setBackgroundColor(selectedBackgroundColorResId);
                }
            } else { // 未选中
                // 设置标题图片
                if (normalTitleImageResId != 0) {
                    imageView.setImageResource(normalTitleImageResId);
                }
                // 设置标题文字颜色
                if (normalTitleTextColorResId != 0) {
                    tvTitle.setTextColor(normalTitleTextColorResId);
                }
                // 设置背景颜色
                if (normalBackgroundColorResId != 0) {
                    view.setBackgroundColor(normalBackgroundColorResId);
                }
            }

            // 设置选中状态
            this.selected = select;
        }
    }

    /**
     * 获取该项的选中状态
     *
     * @return
     */
    public boolean getSelectedState() {
        return selected;
    }

    /**
     * 设置标题文字字号
     *
     * @param titleTextSize
     */
    public void setTitleTextSize(int titleTextSize) {
        this.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize);
    }

    /**
     * 设置标题文字
     *
     * @param text
     */
    public void setTitleText(String text) {
        if (tvTitle != null && text != null) {
            tvTitle.setText(text);
        }
    }

    /**
     * 获取该菜单项是否可以被选中
     *
     * @return
     */
    public boolean isCanChangeState() {
        return canChangeState;
    }

    /**
     * 设置该菜单项是否可以被选中
     *
     * @param canChangeState
     */
    public void setCanChangeState(boolean canChangeState) {
        this.canChangeState = canChangeState;
    }

    /**
     * 设置未选中时的标题颜色
     *
     * @param normalTitleTextColorResId
     */
    public void setNormalTitleTextColorResId(int normalTitleTextColorResId) {
        this.normalTitleTextColorResId = getResources().getColor(normalTitleTextColorResId);
    }

    /**
     * 设置被选中时的标题颜色
     *
     * @param selectedTitleTextColorResId
     */
    public void setSelectedTitleTextColorResId(int selectedTitleTextColorResId) {
        this.selectedTitleTextColorResId = getResources().getColor(selectedTitleTextColorResId);
    }

    /**
     * 设置未选中时的标题图片资源Id
     *
     * @param normalTitleImageResId
     */
    public void setNormalTitleImageResId(int normalTitleImageResId) {
        this.normalTitleImageResId = normalTitleImageResId;
    }

    /**
     * 设置被选中时的标题图片资源Id
     *
     * @param selectedTitleImageResId
     */
    public void setSelectedTitleImageResId(int selectedTitleImageResId) {
        this.selectedTitleImageResId = selectedTitleImageResId;
    }

    /**
     * 被选中时的背景颜色的资源Id
     *
     * @param selectedBackgroundColorResId
     */
    public void setSelectedBackgroundColorResId(int selectedBackgroundColorResId) {
        this.selectedBackgroundColorResId = getResources().getColor(selectedBackgroundColorResId);
    }

    /**
     * 未选中时的背景颜色的资源Id
     *
     * @param normalBackgroundColorResId
     */
    public void setNormalBackgroundColorResId(int normalBackgroundColorResId) {
        this.normalBackgroundColorResId = getResources().getColor(normalBackgroundColorResId);
    }
}
