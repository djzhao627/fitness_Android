package com.lilei.fitness.view.test;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.MediaController;
import android.widget.VideoView;

import com.lilei.fitness.R;
import com.lilei.fitness.view.base.BaseActivity;

/**
 * Created by djzhao on 17/04/29.
 */

public class VideoPlayer extends BaseActivity {

    private VideoView videoView;

    private MediaController mediaController;
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_test_video_player);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        this.videoView = $(R.id.player_test_vv);
    }

    @Override
    protected void initView() {
        // String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/movie.mp4";
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.base;
        // 本地视频
        // videoView.setVideoPath(path);
        // 网络视频
        // videoView.setVideoURI(Uri.parse("http://www.runoob.com/try/demo_source/movie.mp4"));
        videoView.setVideoURI(Uri.parse(uri));
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
    }
}
