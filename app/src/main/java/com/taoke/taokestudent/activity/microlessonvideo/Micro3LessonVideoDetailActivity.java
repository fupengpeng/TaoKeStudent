package com.taoke.taokestudent.activity.microlessonvideo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.activity.personalcenter.LoginActivity;
import com.taoke.taokestudent.application.MyApplication;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.customerview.SimpleTabView;
import com.taoke.taokestudent.entity.MicroLessonVideoListItem;
import com.taoke.taokestudent.fragment.BaseFragment;
import com.taoke.taokestudent.fragment.microlessonvideo.Micro3DetailChargeFragment;
import com.taoke.taokestudent.fragment.microlessonvideo.Micro3DetailFreeFragment;
import com.taoke.taokestudent.manager.ViewNavigatorManager;
import com.taoke.taokestudent.presenter.microlessonvideo.Micro3LessonVideoDetailActivityPresenter;
import com.taoke.taokestudent.util.InfoUtils;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 微课视频-微课视频-微课视频 详情界面
 */
public class Micro3LessonVideoDetailActivity extends BaseActivity {
    /* 标题栏 */
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    /* 标题 */
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    /* 免费课程 */
    @BindView(R.id.stv_micro_3_detail_free)
    SimpleTabView stvMicro3DetailFree;
    /* 收费课程 */
    @BindView(R.id.stv_micro_3_detail_charge)
    SimpleTabView stvMicro3DetailCharge;
    /* 播放器区域 */
    @BindView(R.id.video_layout)
    FrameLayout videoLayout;
    /* 视频播放器 */
    @BindView(R.id.videoView)
    UniversalVideoView videoView;
    /* 视频播放器控制器 */
    @BindView(R.id.media_controller)
    UniversalMediaController mediaController;
    /* 数量 */
    @BindView(R.id.tv_micro_3_detail_clanum)
    TextView tvMicro3DetailClanum;
    /* 价格 */
    @BindView(R.id.tv_micro_3_detail_price)
    TextView tvMicro3DetailPrice;
    /* 免费课程下载 */
    @BindView(R.id.ll_micro_3_detail_free_download)
    LinearLayout llMicro3DetailFreeDownload;
    /* 收费课程购买 */
    @BindView(R.id.ll_micro_3_detail_charge_buy)
    LinearLayout llMicro3DetailChargeBuy;
    /* 购买图标 */
    @BindView(R.id.iv_micro_3_detail_charge_buy_icon)
    ImageView ivBuyIcon;
    /* 购买提示 */
    @BindView(R.id.tv_micro_3_detail_charge_buy_text)
    TextView tvBuyText;


    /* 子项Fragment集合 */
    private BaseFragment[] fragments;
    /* 上一次显示的Fragment */
    private BaseFragment lastFragment;

    /* 导航菜单管理器 */
    private ViewNavigatorManager navigatorManager;
    /* 课程 */
    private MicroLessonVideoListItem item;

    /* 视频播放器区域高度 */
    private int cachedHeight;
    /* 标识是否全屏 */
    private boolean isFullscreen;
    /* 视频URL集 */
    private List<String> urlList;
    /* 当前视频 */
    private int urlPosition;

    /* 主导器 */
    private Micro3LessonVideoDetailActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_micro3_lesson_video_detail);
        presenter = new Micro3LessonVideoDetailActivityPresenter(this);

        // 获取参数
        item = getIntent().getParcelableExtra(Consts.IntentExtraKey.MICRO_DETAIL);
        // 初始化子项Fragment
        initSubFragments();
        // 初始化Tab项
        initTabItemView();
        // 初始化界面
        initViews();
        // 初始化视频播放器
        initVideoPlayer();
    }

    /**
     * 初始化视频播放器
     */
    private void initVideoPlayer() {
        videoView.setMediaController(mediaController);
        setVideoAreaSize();
        videoView.setVideoViewCallback(new InnerVideoViewCallBack());
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (urlPosition < (urlList.size() - 1)) {
                    urlPosition++;
                    videoView.setVideoPath(urlList.get(urlPosition).replace(".flv", ".mp4"));
                    videoView.requestFocus();
                    videoView.start();
                }
            }
        });
        mediaController.setEnabled(false);
    }

    /**
     * 视频播放器回调监听器
     */
    private class InnerVideoViewCallBack implements UniversalVideoView.VideoViewCallback {

        @Override
        public void onScaleChange(boolean isFullscreen) {
            Micro3LessonVideoDetailActivity.this.isFullscreen = isFullscreen;
            if (isFullscreen) {
                ViewGroup.LayoutParams layoutParams = videoLayout.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayout.setLayoutParams(layoutParams);
                rlTitle.setVisibility(View.GONE);

            } else {
                ViewGroup.LayoutParams layoutParams = videoLayout.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = Micro3LessonVideoDetailActivity.this.cachedHeight;
                videoLayout.setLayoutParams(layoutParams);
                rlTitle.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPause(MediaPlayer mediaPlayer) {

        }

        @Override
        public void onStart(MediaPlayer mediaPlayer) {

        }

        @Override
        public void onBufferingStart(MediaPlayer mediaPlayer) {

        }

        @Override
        public void onBufferingEnd(MediaPlayer mediaPlayer) {

        }
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        videoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = videoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = videoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                videoLayout.setLayoutParams(videoLayoutParams);
            }
        });
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        if (item != null) {
            // 标题
            tvTitleText.setText(item.getName());
            // 数量
            tvMicro3DetailClanum.setText(item.getClanum());
            // 价格
            tvMicro3DetailPrice.setText(item.getPrice());

            // 购买
            // 验证是否已购买或免费
            if (MyApplication.getInstance().getUser() == null
                    || (Consts.MicroPayType.UNPAY.equals(item.getPayly())
                    && !MyApplication.getInstance().getUser().isFree())) {
                // 需要购买
                llMicro3DetailChargeBuy.setEnabled(true);
                ivBuyIcon.setVisibility(View.VISIBLE);
                tvBuyText.setText(getString(R.string.buy));
            } else {
                llMicro3DetailChargeBuy.setEnabled(false);
                ivBuyIcon.setVisibility(View.GONE);
                tvBuyText.setText(getString(R.string.buyed));
            }
        }
    }

    /**
     * 初始化子项Fragment
     */
    private void initSubFragments() {
        fragments = new BaseFragment[]{
                new Micro3DetailFreeFragment(),
                new Micro3DetailChargeFragment()
        };
    }

    /**
     * 初始化Tab项
     */
    private void initTabItemView() {
        stvMicro3DetailFree.setTabName("免费课程");
        stvMicro3DetailFree.setTabNameTextSize(18);
        stvMicro3DetailFree.setNormalTextColor(R.color.text_micro_detail_tab_item_normal);
        stvMicro3DetailFree.setSelectedTextColor(R.color.theme_color);
        stvMicro3DetailFree.setNormalBackgroundImage(R.drawable.bg_micro_detail_tab_item_normal);
        stvMicro3DetailFree.setSelectedBackgroundImage(R.drawable.bg_micro_detail_tab_item_selected);


        stvMicro3DetailCharge.setTabName("收费课程");
        stvMicro3DetailCharge.setTabNameTextSize(18);
        stvMicro3DetailCharge.setNormalTextColor(R.color.text_micro_detail_tab_item_normal);
        stvMicro3DetailCharge.setSelectedTextColor(R.color.theme_color);
        stvMicro3DetailCharge.setNormalBackgroundImage(R.drawable.bg_micro_detail_tab_item_normal);
        stvMicro3DetailCharge.setSelectedBackgroundImage(R.drawable.bg_micro_detail_tab_item_selected);


        // 菜单项集合
        SimpleTabView[] simpleTabViews = new SimpleTabView[]{
                stvMicro3DetailFree, stvMicro3DetailCharge};

        // 菜单管理器
        navigatorManager = new ViewNavigatorManager();
        // 设置菜单项集合
        navigatorManager.setNavigatorItems(simpleTabViews);
        // 设置监听器
        navigatorManager.setOnMenuItemClickListener(new ViewNavigatorManager.OnNavigatorItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                showFragment(R.id.fl_micro_3_detail_content, lastFragment, fragments[index]);
                lastFragment = fragments[index];
                // 显示下载或购买
                if (index == 0) {
                    llMicro3DetailFreeDownload.setVisibility(View.VISIBLE);
                    llMicro3DetailChargeBuy.setVisibility(View.GONE);
                } else {
                    llMicro3DetailFreeDownload.setVisibility(View.GONE);
                    llMicro3DetailChargeBuy.setVisibility(View.VISIBLE);
                }
            }
        });

        // 设置默认选中项
        navigatorManager.setSelectIndex(0, simpleTabViews[0], true);
    }

    /**
     * 获取课程
     *
     * @return
     */
    public MicroLessonVideoListItem getMicroLessonVideoListItem() {
        return item;
    }

    /**
     * 播放
     *
     * @param name    课程小节名
     * @param urlList 视频URL集
     */
    public void play(String name, List<String> urlList) {
        mediaController.setEnabled(true);

        // 判断是否存在视频
        this.urlList = urlList;
        if (urlList == null || urlList.size() == 0) {
            InfoUtils.showInfo(this, "没有视频数据");
            return;
        }

        // 播放视频
        urlPosition = 0;
        videoView.setVideoPath(urlList.get(urlPosition).replace(".flv", ".mp4"));
        videoView.requestFocus();
        videoView.start();
        mediaController.setTitle(name);
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            videoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void back() {
        finish();
    }

    /**
     * 购买课程
     */
    @OnClick(R.id.ll_micro_3_detail_charge_buy)
    public void buyLesson() {
        // 判断用户是否登录
        if (MyApplication.getInstance().getUser() == null) {
            startActivity(LoginActivity.class);
            return;
        }

        // 购买
        presenter.buyLesson(MyApplication.getInstance().getUser().getUid(), item.getId());
    }

    /**
     * 设置已购买
     */
    public void setBuyed(String tid) {
        if (tid.equals(item.getId())) {
            llMicro3DetailChargeBuy.setEnabled(false);
            ivBuyIcon.setVisibility(View.GONE);
            tvBuyText.setText(getString(R.string.buyed));
            item.setPayly(Consts.MicroPayType.PAY);
        }
    }

    /**
     * 下载课程视频
     *
     * @param view
     */
    @OnClick({R.id.iv_title_download, R.id.ll_micro_3_detail_free_download})
    public void downloadLesson(View view) {
        Intent intent = new Intent(this, MicroDownloadActivity.class);
        intent.putExtra(Consts.IntentExtraKey.MICRO_DETAIL, item);
        startActivity(intent);
    }

}
