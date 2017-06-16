package com.taoke.taokestudent.customerview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taoke.taokestudent.R;

/**
 * 进度对话框 可显示在加载数据时的等待时间
 * 
 */
public class ProgressDialog extends Dialog {
	// 圆形进度条
	private ProgressBar pbCircle = null;
	// 提示信息TextView
	private TextView tvMsg = null;

	public ProgressDialog(Context context) {
		super(context);

		// 初始化
		init();
	}

	public ProgressDialog(Context context, int theme) {
		super(context, theme);

		// 初始化
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		View view = View.inflate(getContext(), R.layout.dialog_custom_loading,
				null);
		tvMsg = (TextView) view
				.findViewById(R.id.dialog_custom_loading_txt_progress_msg);
		pbCircle = (ProgressBar) view
				.findViewById(R.id.dialog_custom_loading_pb_progress);
		pbCircle.setIndeterminateDrawable(getContext().getResources()
				.getDrawable(R.drawable.rotate_progress_white));
		this.setContentView(view);
	}

	/**
	 * 获取提示信息TextView
	 * 
	 * @return
	 */
	public TextView getMsgTextView() {
		return tvMsg;
	}

	/**
	 * 设置提示信息TextView
	 * 
	 * @param textView
	 */
	public void setMsgTextView(TextView textView) {
		this.tvMsg = textView;
	}

	/**
	 * 设置提示信息
	 * 
	 * @param msg
	 */
	public void setMsg(String msg) {
		if (tvMsg != null && msg != null) {
			tvMsg.setText(msg);
		}
	}

}
