package com.meiling.oms.jpush.jpushPlay

import android.content.Context
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class MessageManagement private constructor(context: Context) {

    companion object {
        private var instance: MessageManagement? = null;

        @Synchronized
        fun get(context: Context): MessageManagement? {
            if (instance == null) {
                instance = MessageManagement(context);
            }
            return instance;
        }
    }

    private var isPaly = false
    private var context: Context = context
    private var mMessageManagementListener: OnMessageManagementListener? = null
    private var mediaPlayerAdapter: MediaPlayerAdapter = MediaPlayerAdapter()
    private var messageDataQueue: Queue<MessageData> =
        ConcurrentLinkedQueue<MessageData>()


    public fun setMessageManagementListener(listener: OnMessageManagementListener) {
        this.mMessageManagementListener = listener
    }

    public fun addMessage(messageData: MessageData, index: Int = 1) {
        for (i in 0 until index){
            messageDataQueue.add(messageData)
            mMessageManagementListener?.currentMessageTotal(messageDataQueue.size)
            play()
        }
    }

    @Synchronized //或者isPaly使用关键字线程安全
    private fun play() {
        if (isPaly) {
            return
        }

        var poll = messageDataQueue.poll() ?: return
        isPaly = true

        mMessageManagementListener?.currentMessageTotal(messageDataQueue.size)
        mMessageManagementListener?.currentPlaying(poll)
        mediaPlayerAdapter.loadMedia(context, poll.rawId, object : OnPrepareCompletedListener {
            override fun onComplete() {
                mediaPlayerAdapter.play()
            }
        })
        mediaPlayerAdapter.setPlaybackInfoListener(object : PlaybackInfoListener {
            override fun onTotalDuration(duration: Int) {

            }

            override fun onPositionChanged(position: Int) {

            }

            override fun onStateChanged(state: Int) {

            }

            override fun onPlayCompleted() {
                isPaly = false
                play()
            }
        })
    }


    interface OnMessageManagementListener {
        fun currentPlaying(messageData: MessageData)
        fun currentMessageTotal(total: Int)

    }

}