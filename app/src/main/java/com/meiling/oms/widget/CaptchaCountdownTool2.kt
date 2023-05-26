package com.meiling.oms.widget

import android.os.CountDownTimer

class CaptchaCountdownTool2(private val listener: CaptchaCountdownListener) {


    private val COUNTDOWN_TIME = 60L // Countdown time in seconds
    private val COUNTDOWN_INTERVAL = 1000L // Countdown interval in milliseconds

    private lateinit var countDownTimer: CountDownTimer
    private var isInitialized = false
    fun startCountdown() {
        countDownTimer = object : CountDownTimer(COUNTDOWN_TIME * 1000, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val countDownText = "$secondsLeft"
                listener.onCountdownTick(countDownText)
            }

            override fun onFinish() {
                listener.onCountdownFinish()
            }
        }
        isInitialized = true
        countDownTimer.start()
    }

    fun stopCountdown() {
        if (isInitialized) {
            countDownTimer.cancel()
            isInitialized = false
        }
    }

    interface CaptchaCountdownListener {
        fun onCountdownTick(countDownText: String)
        fun onCountdownFinish()
    }
}

