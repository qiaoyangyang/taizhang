package com.meiling.account.jpush.jpushPlay

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.util.Log
import java.io.IOException

class MediaPlayerAdapter : IPlayerApi, OnPreparedListener, MediaPlayer.OnErrorListener,
    OnCompletionListener {

    private var TAG = "MediaPlayerHolder"
    private var STATUS_ERROR = -1 //错误
    private var STATUS_STOP = 1 //暂停播放
    private var mMusicRawId = 0
    private var STATUS_PALYINGP = 0 //正在播放
    private var STATUS_PLAY_COMPLETE = 3 //播放完成
    private var STATUS_PREPER_ING = 5 //媒体流加载中
    private var mMediaPlayer: MediaPlayer? = null
    private var mPlaybackInfoListener: PlaybackInfoListener? = null
    private var mOnPrepareCompletedListener: OnPrepareCompletedListener? = null


    public fun setPlaybackInfoListener(mPlaybackInfoListener: PlaybackInfoListener) {
        this.mPlaybackInfoListener = mPlaybackInfoListener
    }


    override fun loadMedia(
        context: Context,
        musicRawId: Int,
        listener: OnPrepareCompletedListener
    ) {
        try {
            var afd = context.getResources().openRawResourceFd(musicRawId)
            if (afd == null) {
                Log.e(TAG, "afd == null");
                return
            }
            mOnPrepareCompletedListener = listener
            mPlaybackInfoListener?.onStateChanged(STATUS_PREPER_ING)
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.setOnErrorListener(this)
            mMediaPlayer?.setOnCompletionListener(this)
            mMediaPlayer?.setOnPreparedListener(this)
            mMediaPlayer?.reset()
            mMediaPlayer?.setDataSource(
                afd.getFileDescriptor(),
                afd.getStartOffset(),
                afd.getLength()
            )
            mMediaPlayer?.prepareAsync()
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
        }
    }

    override fun release() {
        if (mMediaPlayer != null) {
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
            mMediaPlayer = null
            mMusicRawId = 0
        }
    }

    override fun isPlaying(): Boolean {
        if (mMediaPlayer != null) {
            return mMediaPlayer!!.isPlaying()
        }
        return false
    }

    override fun play() {
        if (mMediaPlayer != null && !mMediaPlayer!!.isPlaying()) {
            mMediaPlayer?.start()
            mPlaybackInfoListener?.onStateChanged(STATUS_PALYINGP)
        }
    }


    override fun pause() {
        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying()) {
            mMediaPlayer?.pause()
            mPlaybackInfoListener?.onStateChanged(STATUS_STOP)
        }
    }

    override fun seekTo(position: Int) {
        if (mMediaPlayer != null) {
            mMediaPlayer?.seekTo(position)
        }
    }

    override fun onPrepared(p0: MediaPlayer?) {
        if (mOnPrepareCompletedListener != null) {
            mOnPrepareCompletedListener?.onComplete();
        }
    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        mPlaybackInfoListener?.onStateChanged(STATUS_ERROR);
        Log.d("lwq", "OnError - Error code: " + p1 + " Extra code: " + p2);
        return false
    }

    override fun onCompletion(p0: MediaPlayer?) {
        mPlaybackInfoListener?.onStateChanged(STATUS_PLAY_COMPLETE);
        mPlaybackInfoListener?.onPlayCompleted();
    }

}