package com.lilei.fitness.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.lilei.fitness.R;
import com.lilei.fitness.adapter.NewsDetailCommnetsAdapter;
import com.lilei.fitness.entity.Comment;
import com.lilei.fitness.entity.NewsDetail;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.DateUtils;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.view.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by djzhao on 17/04/29.
 */

public class VideoPlayer extends BaseActivity {

    private VideoView videoView;

    private MediaController mediaController;

    private Context mContext;

    private int tag;

    private boolean videoStop;
    private int currentPosition;
    private String duration = "0";

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        tag = getIntent().getIntExtra("tag", 1);
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
        mContext = this;
        uiFlusHandler = new MyDialogHandler(mContext, "数据同步中...");
        loadVideo();
    }

    private void loadVideo() {
        // String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/movie.mp4";
        String uri = "android.resource://" + getPackageName() + "/";
        switch (tag) {
            case 1:
                duration = "8";
                uri += R.raw.base;
                break;
            case 2:
                duration = "9";
                uri += R.raw.enhance;
                break;
            case 3:
                duration = "11";
                uri += R.raw.acme;
                break;
        }
        // 本地视频
        // videoView.setVideoPath(path);
        // 网络视频
        // videoView.setVideoURI(Uri.parse("http://www.runoob.com/try/demo_source/movie.mp4"));
        videoView.setVideoURI(Uri.parse(uri));
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoStop = true;
                saveTrainRecord();
            }
        });
        videoView.start();
    }

    private void saveTrainRecord() {
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        String url = Constants.BASE_URL + "Train?method=addNewTrainRecord";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId() + "")
                .addParams("duration", duration)
                .build()
                .execute(new MyStringCallback());
    }

    private void showInfo() {
        String message = "";
        switch (tag) {
            case 1:
                message = "“肌撕裂者-初级”";
                break;
            case 2:
                message = "“肌撕裂者-中级”";
                break;
            case 3:
                message = "“肌撕裂者-极致”";
                break;
        }
        SystemClock.sleep(1000);
        AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayer.this);
        builder.setTitle("锻炼结束")
                .setMessage("恭喜你，" + message + "锻炼结束！")
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create()
                .show();
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
            switch (id) {
                case 1:
                    if (response.contains("error")) {
                        DisplayToast("锻炼记录同步失败");
                    }
                    showInfo();
                    break;
                default:
                    DisplayToast("what?");
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络链接出错！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!videoStop) {
                videoView.pause();
                currentPosition = videoView.getCurrentPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayer.this);
                builder.setTitle("猛男")
                        .setMessage("你确定要放弃锻炼吗？")
                        .setPositiveButton("择日再战", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setNegativeButton("我怎么可能半途而废", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                videoView.seekTo(currentPosition);
                                videoView.start();
                            }
                        })
                        .create()
                        .show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
