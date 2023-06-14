package com.meiling.account.jpush.jpushPlay

interface PlaybackInfoListener {

    fun onTotalDuration(duration: Int)//总时长

    fun onPositionChanged(position: Int)//当前时长进度

    fun onStateChanged(state: Int)//记录当前的状态

    fun onPlayCompleted()//播放完成回调

}