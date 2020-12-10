package com.lxh.cookcommunity.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lxh.cookcommunity.R;
import com.lxh.cookcommunity.manager.sharedpref.SharedPrefModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoPlayer extends FrameLayout {

    /**
     * 由ijkplayer提供，用于播放视频，需要给他传入一个surfaceView
     */
    private IMediaPlayer mMediaPlayer = null;

    /**
     * 视频文件地址
     */
    private String mPath = "";

    /*视频地址索引*/
    private Long currentIndex = 0L;

    /**
     * 视频文件地址列表
     */
    private List<String> pathList = new ArrayList<>();
    private SurfaceView surfaceView;
    private VideoPlayerListener listener;
    private Context mContext;

    //是否是播放状态
    public Boolean isPlaying = true;
    private Boolean isVolumeMax = true;
    public ImageView ivPlaying;
    public ImageView ivSound;
    public ProgressBar updateProgressBar;
    public LinearLayout ll_seek;
    public LinearLayout ll_empty;
    public SeekBar seekBarVideo;
    private Boolean isTouchingSeekBar = false;
    public int playImageResourse;
    public int pauseImageResourse;

    public VideoPlayer(@NonNull Context context) {
        super(context);
        initVideoView(context);
    }

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    private void initVideoView(Context context) {
        mContext = context;
        //获取焦点
        setFocusable(true);
    }

    /*设置视频地址。根据是否第一次播放视频，做不同的操作*/
    public void setVideoPath(String path) {
        if (TextUtils.equals("", mPath)) {
            //如果是第一次播放视频，那就创建一个新的surfaceView
            mPath = path;
            createSurfaceView();
        } else {
            //否则就直接load
            mPath = path;
            load();
        }
    }

    public void clear() {
        ll_empty.setVisibility(VISIBLE);
        removeView(surfaceView);
        ll_seek.setVisibility(INVISIBLE);
        mPath = "";
    }

    private Pair<Long, Boolean> continueDataPair;

    public void setContinueData(long continueDuration, boolean continueIsPlaying) {
        continueDataPair = Pair.create(continueDuration, continueIsPlaying);
    }

    public void setContinueData() {
        continueDataPair = Pair.create(getCurrentPosition(), isPlaying);
    }

    /*播放下一个*/
    public void playNext() {
        if (pathList.isEmpty()) {
            if (surfaceView != null) clear();
            return;
        }
        int realIndexInList;
        if (currentIndex == 0) {
            realIndexInList = 0;
        } else {
            realIndexInList = (int) (currentIndex % pathList.size());
        }
        currentIndex += 1;
        setVideoPath(pathList.get(realIndexInList));
    }

    /*设置播放列表*/
    public void setVideoPathList(List<String> pathList) {
        this.pathList = pathList;
        playNext(); //初始播放
/*        if(pathList.size()>0){
            setVideoPath(pathList.get(0));
        }*/
    }

    OnClickListener onVideoClick;

    public OnClickListener getOnVideoClick() {
        return onVideoClick;
    }

    public void setOnVideoClick(OnClickListener onVideoClick) {
        this.onVideoClick = onVideoClick;
    }

    /*新建一个surfaceview*/
    @SuppressLint("ClickableViewAccessibility")
    private void createSurfaceView() {
        //生成一个新的surface view
        surfaceView = new SurfaceView(mContext);
        surfaceView.getHolder().addCallback(new LmnSurfaceCallback());
        surfaceView.setOnClickListener(v -> {
            if (onVideoClick != null) {
                onVideoClick.onClick(v);
            }
            if (ll_seek.getVisibility() == VISIBLE) {
                ll_seek.setVisibility(GONE);
            } else {
                ll_seek.setVisibility(VISIBLE);
            }
        });
        this.addView(surfaceView);
/*        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT
                , LayoutParams.MATCH_PARENT, Gravity.CENTER);
        surfaceView.setLayoutParams(layoutParams);*/

        if (ivPlaying != null) {
            ivPlaying.setOnClickListener(v -> {
                //ivPlaying.setVisibility(VISIBLE);
                if (isPlaying) {
                    //暂停
                    pause();
                    ivPlaying.setImageResource(playImageResourse);
                    isPlaying = false;
                } else {
                    start();
                    ivPlaying.setImageResource(pauseImageResourse);
                    isPlaying = true;
                }
            });
        }

        if (ivSound != null)
            ivSound.setOnClickListener(v -> {
                if (isVolumeMax) {
                    //静音
                    ivSound.setImageResource(R.drawable.icon_silence);
                    mMediaPlayer.setVolume(0f, 0f);
                    isVolumeMax = false;
                } else {
                    //打开声音
                    ivSound.setImageResource(R.drawable.icon_sound);
                    mMediaPlayer.setVolume(1f, 1f);
                    isVolumeMax = true;
                }
            });
        if (ll_seek != null) {
            ll_seek.setOnTouchListener((v, event) -> true);
        }
        if (seekBarVideo != null)
            seekBarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    isTouchingSeekBar = true;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int seekPos = seekBar.getProgress();
                    seekTo(seekPos);
                    isTouchingSeekBar = false;
                }
            });


    }

    /**
     * surfaceView的监听器
     */
    private class LmnSurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            //surfaceview创建成功后，加载视频
            load();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    /**
     * 加载视频
     */
    private void load() {
        //每次都要重新创建IMediaPlayer
        createPlayer();
        try {
            Map<String, String> headers = new HashMap<>();
            Uri uri = Uri.parse(mPath);
            headers.put("mac", SharedPrefModel.INSTANCE.getMacAddress());
            mMediaPlayer.setDataSource(getContext(), uri, headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //给mediaPlayer设置视图
        mMediaPlayer.setDisplay(surfaceView.getHolder());
        mMediaPlayer.prepareAsync();
    }

    /**
     * 创建一个新的player
     */
    private void createPlayer() {
        if (ll_empty != null) {
            ll_empty.setVisibility(GONE);
        }
        setBackgroundColor(Color.BLACK);
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.setDisplay(null);
            mMediaPlayer.release();
        }
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
        IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        mMediaPlayer = ijkMediaPlayer;
        //开启硬解码
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-buffer-size", 100000 * 1024);//设置缓冲区为100000KB
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "min-frames", 1000);// 视频的话，设置100帧即开始播放

        // 清空DNS,有时因为在APP里面要播放多种类型的视频(如:MP4,直播,直播平台保存的视频,和其他http视频), 有时会造成因为DNS的问题而报10000问题的
        //ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1);

        if (listener != null) {
            mMediaPlayer.setLogEnabled(false);
            mMediaPlayer.setOnPreparedListener(listener);
            mMediaPlayer.setOnCompletionListener(listener);
            mMediaPlayer.setOnInfoListener((iMediaPlayer, i, i1) -> {
                Log.d("~~~~~~~~~~~~~~", i + "---" + i1);
                if (updateProgressBar != null) {
                    switch (i) {
                        case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        /*case IMediaPlayer.MEDIA_INFO_AUDIO_DECODED_START:
                        case IMediaPlayer.MEDIA_INFO_VIDEO_DECODED_START:*/
                        case IMediaPlayer.MEDIA_INFO_OPEN_INPUT:
                        case IMediaPlayer.MEDIA_INFO_VIDEO_DECODED_START:
                        case IMediaPlayer.MEDIA_INFO_AUDIO_DECODED_START:
                            updateProgressBar.setVisibility(VISIBLE);
                            break;
                        /*case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                        case IMediaPlayer.MEDIA_INFO_VIDEO_SEEK_RENDERING_START:
                        case IMediaPlayer.MEDIA_INFO_AUDIO_SEEK_RENDERING_START:*/
                        default:
                            updateProgressBar.setVisibility(GONE);
                            break;
                    }
                }
                return false;
            });
            mMediaPlayer.setOnSeekCompleteListener(listener);
            mMediaPlayer.setOnBufferingUpdateListener(listener);
            mMediaPlayer.setOnErrorListener(listener);
            mMediaPlayer.setOnVideoSizeChangedListener((iMediaPlayer, w, h, i2, i3) -> {
                setBackgroundColor(Color.BLACK);
                //在此可以获取到视频的宽和高
                Log.d("!!!", w + "-" + h + "-" + i2 + "-" + i3);
                videoWidth = w;
                videoHeight = h;

                float scaleMutNum = 1f;
                int width = 0;
                int height = 0;
                ViewGroup.LayoutParams layoutParams = null;

                //视频高度小于控件高度或者视频宽度小于控件宽度
                if (h <= getHeight() || w <= getWidth()) {
                    //可放大最大倍数
                    scaleMutNum = Math.min((float) getHeight() / h, (float) getWidth() / w);
                    width = (int) (scaleMutNum * (float) w);
                    height = (int) (scaleMutNum * (float) h);
                    if (height >= getHeight()) {
                        height = getHeight();
                        width = (int) (height * ((float) w / (float) h));
                    }
                    if (width >= getWidth()) {
                        width = getWidth();
                        height = (int) (width * ((float) h / (float) w));
                    }
                    layoutParams = new LayoutParams(width, height, Gravity.CENTER);
                } else {
                    width = (int) (((float) w / (float) h) * getHeight());
                    layoutParams = new LayoutParams(width, getHeight(), Gravity.CENTER);
                }
                surfaceView.setLayoutParams(layoutParams);
            });
        }
    }

    int videoWidth;
    int videoHeight;

    public void setListener(VideoPlayerListener listener) {
        this.listener = listener;
/*        if (mMediaPlayer != null) {
            mMediaPlayer.setOnPreparedListener(listener);
            mMediaPlayer.setOnBufferingUpdateListener(listener);
        }*/
    }

    /**
     * 封装控制视频的方法
     */
    public void start() {
        ivPlaying.setImageResource(pauseImageResourse);
        if (mMediaPlayer != null) {
            if (ll_empty != null) ll_empty.setVisibility(GONE);
            if (ll_seek != null) ll_seek.setVisibility(VISIBLE);
            if (isVolumeMax)
                mMediaPlayer.setVolume(1f, 1f);
            else
                mMediaPlayer.setVolume(0f, 0f);
            mMediaPlayer.start();
            Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnEach(longNotification -> {
                        if (getDuration() > 0 && !isTouchingSeekBar) {
                            seekBarVideo.setMax((int) getDuration());
                            seekBarVideo.setProgress((int) getCurrentPosition());
                        }
                    }).subscribe();

            if (continueDataPair != null) {
                seekTo(continueDataPair.first);
                if (!continueDataPair.second) pause();
                continueDataPair = null;
            }
        }
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void pause() {
        if(ivPlaying!=null){
            ivPlaying.setImageResource(playImageResourse);
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    public void stop() {
        ivPlaying.setImageResource(playImageResourse);
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    public void reset() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        }
    }

    public void resetZero() {
        if (mMediaPlayer != null) {
            pause();
        }
    }


    public long getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    public long getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public void seekTo(long l) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(l);
        }
    }
}

