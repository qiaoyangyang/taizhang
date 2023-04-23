package com.meiling.oms.jpush.jpushPlay

import android.content.Context
import androidx.annotation.RawRes
import com.meiling.oms.jpush.jpushPlay.OnPrepareCompletedListener

interface IPlayerApi {


    /**
     * 加载元数据媒体资源
     *
     * @param musicRawId
     */
    fun loadMedia(context: Context, @RawRes musicRawId: Int, listener: OnPrepareCompletedListener)

    /**
     * 释放资源
     */
    fun release()

    /**
     * 判断是否在播放
     *
     * @return
     */
    fun isPlaying(): Boolean

    /**
     * 开始播放
     */
    fun play()



    /**
     * 暂停
     */
    fun pause()

    /**
     * 滑动到某个位置
     */
    fun seekTo(position: Int)

}