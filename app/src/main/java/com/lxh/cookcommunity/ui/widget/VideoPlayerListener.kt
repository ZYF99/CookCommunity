package com.lxh.cookcommunity.ui.widget

import android.util.Log
import tv.danmaku.ijk.media.player.IMediaPlayer

class VideoPlayerListener(
    val onVideoPrepared: (() -> Unit)? = null,
    private val onInfoReturned: ((code: Int) -> Unit)? = null,
    val onVideoCompleted: (() -> Unit)? = null
) :
    IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnCompletionListener,
    IMediaPlayer.OnPreparedListener, IMediaPlayer.OnInfoListener,
    IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnErrorListener,
    IMediaPlayer.OnSeekCompleteListener {
    override fun onBufferingUpdate(iMediaPlayer: IMediaPlayer, i: Int) {

    }

    override fun onCompletion(iMediaPlayer: IMediaPlayer) {
        onVideoCompleted?.invoke()
    }

    override fun onError(iMediaPlayer: IMediaPlayer, i: Int, i1: Int): Boolean {
        return false
    }

    override fun onInfo(iMediaPlayer: IMediaPlayer, i: Int, i1: Int): Boolean {
        onInfoReturned?.invoke(i)
        Log.d("~~~~~~~~~~~~~~","${i}--${i1}")
        return false
    }

    override fun onPrepared(iMediaPlayer: IMediaPlayer) {
        // 视频准备好播放了，但是他不会自动播放，需要手动让他开始。
        //iMediaPlayer.start();
        iMediaPlayer.isLooping = false
        onVideoPrepared?.invoke()
    }

    override fun onSeekComplete(iMediaPlayer: IMediaPlayer) {
        Log.d("", "!!!")
    }

    override fun onVideoSizeChanged(
        iMediaPlayer: IMediaPlayer,
        w: Int,
        h: Int,
        i2: Int,
        i3: Int
    ) {
        //在此可以获取到视频的宽和高
        Log.d("", "!!!")
    }

}