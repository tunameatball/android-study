package com.kkh.record_app

import android.os.Handler
import android.os.Looper

class Timer(listener: OnTimerTickListener) {

    private var duration = 0L
    private val handler = Handler(Looper.getMainLooper())
    private val timerCallback: Runnable = object : Runnable {
        override fun run() {
            duration += 100L
            handler.postDelayed(this, 100L)
            listener.onTick(duration)
        }
    }

    fun start() {
        handler.postDelayed(timerCallback, 100L)
    }

    fun stop() {
        handler.removeCallbacks(timerCallback)
    }
}

interface OnTimerTickListener {
    fun onTick(duration: Long)
}