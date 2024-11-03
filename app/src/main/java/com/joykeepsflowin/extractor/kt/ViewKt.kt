package com.joykeepsflowin.extractor.kt

import android.util.Log
import android.view.View

const val TAG = "ViewKt"

const val VALID_CLICK_TIME_DELAY = 700L
var lastClickedTime = 0L

fun View.click(delay: Long = VALID_CLICK_TIME_DELAY, block: (View) -> Unit) {
    setOnClickListener {
        val timeInterval = System.currentTimeMillis() - lastClickedTime
        if (timeInterval > delay) {
            block.invoke(this)
        } else {
            Log.i(TAG, "click deny, time interval: $timeInterval")
        }
    }
}