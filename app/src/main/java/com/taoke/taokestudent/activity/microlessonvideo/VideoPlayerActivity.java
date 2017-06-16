package com.taoke.taokestudent.activity.microlessonvideo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.taoke.taokestudent.R;
import com.taoke.taokestudent.activity.BaseActivity;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.util.InfoUtils;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.List;

import butterknife.BindView;

/**
 * 视频播放界面
 */
public class VideoPlayerActivity extends BaseActivity {
    /* 播放器区域 */
    @BindView(R.id.video_layout)
    FrameLayout videoLayout;
    /* 视频播放器 */
    @BindView(R.id.videoView)
    UniversalVideoView videoView;
    /* 视频播放器控制器 */
    @BindView(R.id.media_controller)
    UniversalMediaController mediaController;


    /* 视频URL集 */
    private List<Uri> uriList;
    /* 视频名 */
    private String name;
    /* 当前视频 */
    private int urlPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_video_player, true);
        // 初始化视频播放器
        initVideoPlayer();
        // 获取参数
        uriList = getIntent().getParcelableArrayListExtra(Consts.IntentExtraKey.VIDEO_URI);
        name = getIntent().getStringExtra(Consts.IntentExtraKey.VIDEO_NAME);
        // 播放
        play();
    }

    /**
     * 初始化视频播放器
     */
    private void initVideoPlayer() {
        videoView.setMediaController(mediaController);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (urlPosition < (uriList.size() - 1)) {
                    urlPosition++;
                    videoView.setVideoURI(uriList.get(urlPosition));
                    videoView.requestFocus();
                    videoView.start();
                }
            }
        });
    }

    /**
     * 播放
     */
    public void play() {
        // 判断是否存在视频
        if (uriList == null || uriList.size() == 0) {
            InfoUtils.showInfo(this, "没有视频数据");
            return;
        }

        // 播放视频
        urlPosition = 0;
        videoView.setVideoURI(uriList.get(urlPosition));
        videoView.requestFocus();
        videoView.start();
        mediaController.setTitle(name);
    }

}
