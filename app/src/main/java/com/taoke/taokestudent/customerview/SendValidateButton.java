package com.taoke.taokestudent.customerview;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

/**
 * 短信验证码发送按钮
 * 
 */
public class SendValidateButton extends Button {
	// 默认倒计时时间，默认60秒
	private static final int DEFAULT_DISABLE_TIME = 60;

	// Handler消息标识
	private static final int MSG_TICK = 0;

	// 计时器
	private Timer timer = null;
	// 计时器任务
	private TimerTask timerTask = null;

	// 倒计时
	private int disableTime = DEFAULT_DISABLE_TIME;

	// 按钮有效时显示的文字
	private String enableString = "获取验证码";
	// 按钮有效时的字体颜色
	private int enableColor = Color.BLACK;

	// 按钮无效时显示的文字
	private String disableString = "重新获取";
	// 按钮无效时的字体颜色
	private int disableColor = Color.BLACK;

	// 标识按钮是否有效
	private boolean clickBle = true;

	// 验证码发送监听器
	private OnSendValidateListener listener = null;

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param attrs
	 */
	public SendValidateButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 初始化
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		// 设置显示内容
		this.setText(enableString);
		this.setGravity(Gravity.CENTER);
		this.setTextColor(enableColor);
		this.setTextSize(17);
		// 初始化计时器
		initTimer();

		// 点击监听器
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null && clickBle) {
					// startTickWork();
					listener.onClickSendValidateButton();
				}
			}
		});
	}

	/**
	 * 初始化计时器
	 */
	private void initTimer() {
		timer = new Timer();
	}

	/**
	 * 开始倒计时
	 */
	public void startTickWork() {
		// 判断按钮是否有效
		if (clickBle) {
			// 将标识设为无效
			clickBle = false;
			// 设置无效内容
			SendValidateButton.this.setText(disableString + "(" + disableTime
					+ "秒)");
			SendValidateButton.this.setTextColor(disableColor);

			// 初始化计时器任务
			initTimerTask();
			// 开始倒计时
			timer.schedule(timerTask, 0, 1000);
		}
	}

	/**
	 * 初始化计时器任务
	 */
	private void initTimerTask() {
		timerTask = new TimerTask() {
			@Override
			public void run() {
				// 传递消息
				handler.sendEmptyMessage(MSG_TICK);
			}
		};
	}

	// 主线程Handler
	private Handler handler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_TICK:
				tickWork();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 每秒钟调用一次
	 */
	private void tickWork() {
		// 倒计时时间减一秒
		disableTime--;
		// 设置倒计时显示文字
		this.setText(disableString + "(" + disableTime + ")");

		// 通知验证码发送监听器
		if (listener != null) {
			listener.onTick();
		}

		// 倒计时时间结束，停止计时
		if (disableTime <= 0) {
			stopTickWork();
		}
	}

	/**
	 * 停止计时
	 */
	public void stopTickWork() {
		// 恢复初始值
		timerTask.cancel();
		timerTask = null;
		disableTime = DEFAULT_DISABLE_TIME;
		this.setText(enableString);
		this.setTextColor(enableColor);
		clickBle = true;
	}

	/**
	 * 获取按钮有效时显示的文字
	 * 
	 * @return
	 */
	public String getEnableString() {
		return enableString;
	}

	/**
	 * 设置按钮有效时显示的文字
	 * 
	 * @param enableString
	 */
	public void setEnableString(String enableString) {
		this.enableString = enableString;
		if (this.isEnabled()) {
			this.setText(enableString);
		}
	}

	/**
	 * 获取按钮无效时显示的文字
	 * 
	 * @return
	 */
	public String getDisableString() {
		return disableString;
	}

	/**
	 * 设置按钮无效时显示的文字
	 * 
	 * @param disableString
	 */
	public void setDisableString(String disableString) {
		this.disableString = disableString;
	}

	/**
	 * 获取按钮有效时字体颜色
	 * 
	 * @return
	 */
	public int getEnableColor() {
		return enableColor;
	}

	/**
	 * 设置按钮有效时字体颜色
	 * 
	 * @param enableColor
	 */
	public void setEnableColor(int enableColor) {
		this.enableColor = enableColor;
		if (isEnabled()) {
			this.setTextColor(enableColor);
		}
	}

	/**
	 * 获取按钮无效时字体颜色
	 * 
	 * @return
	 */
	public int getmDisableColor() {
		return disableColor;
	}

	/**
	 * 设置按钮无效时字体颜色
	 * 
	 * @param disableColor
	 */
	public void setDisableColor(int disableColor) {
		this.disableColor = disableColor;
	}

	/**
	 * 设置验证码发送监听器
	 * 
	 * @param listener
	 */
	public void setmListener(OnSendValidateListener listener) {
		this.listener = listener;
	}

	/**
	 * 验证码发送监听器
	 * 
	 */
	public interface OnSendValidateListener {
		/**
		 * 当有效点击发送按钮时调用
		 */
		public void onClickSendValidateButton();

		public void onTick();
	}

}
