package com.meiling.oms.widget

import android.os.CountDownTimer

class CaptchaCountdownTool(private val listener: CaptchaCountdownListener) {


    private val COUNTDOWN_TIME = 30L // Countdown time in seconds
    private val COUNTDOWN_INTERVAL = 1000L // Countdown interval in milliseconds

    private lateinit var countDownTimer: CountDownTimer

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

        countDownTimer.start()
    }

    fun stopCountdown() {
        countDownTimer.cancel()
    }

    interface CaptchaCountdownListener {
        fun onCountdownTick(countDownText: String)
        fun onCountdownFinish()
    }
}

