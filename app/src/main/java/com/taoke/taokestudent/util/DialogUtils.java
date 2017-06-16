package com.taoke.taokestudent.util;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.customerview.ProgressDialog;

/**
 * 对话框工具类
 */
public class DialogUtils {

    // Context对象
    private Context context = null;

    public DialogUtils(Context context) {
        this.context = context;
    }

    /**
     * 带一个“确定”按钮的提示对画框
     *
     * @param title   标题
     * @param message 提示信息
     * @return
     */
    public Dialog alert(CharSequence title, CharSequence message) {
        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 带一个“确定”按钮的提示对画框
     *
     * @param title   标题
     * @param message 提示信息
     * @return
     */
    public Dialog alert(int title, int message) {
        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 带“确定”“取消”按钮的确认对话框
     *
     * @param title           标题
     * @param message         确认信息
     * @param confirmListener 确认监听器
     * @param cancelListener  取消监听器
     * @return
     */
    public Dialog confirm(CharSequence title, CharSequence message,
                          DialogInterface.OnClickListener confirmListener,
                          DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 带“确定”“取消”按钮的确认对话框
     *
     * @param title           标题
     * @param message         确认信息
     * @param confirmListener 确认监听器
     * @param cancelListener  取消监听器
     * @return
     */
    public Dialog confirm(int title, int message,
                          DialogInterface.OnClickListener confirmListener,
                          DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 带“确定”“取消”按钮的自定义确认信息界面的确认对话框
     *
     * @param title           标题
     * @param view            自定义界面
     * @param confirmListener 确认监听器
     * @param cancelListener  取消监听器
     * @return
     */
    public Dialog showView(CharSequence title, View view,
                           DialogInterface.OnClickListener confirmListener,
                           DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(context);

        // 设置标题
        if (title != null && !"".equals(title)) {
            builder.setTitle(title);
        }

        builder.setView(view);

        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 带“确定”“取消”按钮的自定义确认信息界面的确认对话框
     *
     * @param title           标题
     * @param view            自定义界面
     * @param confirmListener 确认监听器
     * @param cancelListener  取消监听器
     * @return
     */
    public Dialog showView(int title, View view,
                           DialogInterface.OnClickListener confirmListener,
                           DialogInterface.OnClickListener cancelListener) {
        Builder builder = new Builder(context);
        if (title != 0)
            builder.setTitle(title);
        builder.setView(view);

        try {
            builder.setPositiveButton("确定", confirmListener);
        } catch (Exception e) {
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        try {
            builder.setNegativeButton("取消", cancelListener);
        } catch (Exception e) {
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 不带按钮的提示对话框
     *
     * @param title   标题
     * @param message 提示信息
     * @return
     */
    public Dialog showMsg(CharSequence title, CharSequence message) {
        Builder builder = new Builder(context);
        // 设置标题
        if (title != null && !"".equals(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 不带按钮的提示对话框
     *
     * @param title   标题
     * @param message 提示信息
     * @return
     */
    public Dialog showMsg(int title, int message) {
        Builder builder = new Builder(context);
        if (title != 0)
            builder.setTitle(title);
        builder.setMessage(message);
        Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    /**
     * 带圆形进度条的提示对话框
     *
     * @param message
     * @return
     */
    public Dialog showLoading(String message) {
        ProgressDialog dialog = new ProgressDialog(context,
                R.style.dialogBase);
        TextView txt = dialog.getMsgTextView();
        if (message != null && txt != null) {
            txt.setText(message);
        }
        dialog.show();
        dialog.setCancelable(false);
        return dialog;
    }

    /**
     * 带圆形进度条的提示对话框(非Activity)
     *
     * @param message
     * @return
     */
    public static Dialog showLoadingOnBg(String message) {
        ProgressDialog dialog = new ProgressDialog(MyApplication.getInstance(), R.style.dialogBase);
        TextView txt = dialog.getMsgTextView();
        if (message != null && txt != null) {
            txt.setText(message);
        }
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        dialog.setCancelable(false);
        return dialog;
    }

}
