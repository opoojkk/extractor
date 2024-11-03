package com.joykeepsflowin.extractor.kt

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

fun Window.transparentNavigationBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        isNavigationBarContrastEnforced = false
    }
    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    var systemUiVisibility = decorView.systemUiVisibility
    systemUiVisibility =
        systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    decorView.systemUiVisibility = systemUiVisibility
    navigationBarColor = Color.TRANSPARENT

    setNavigationBarBtnColor(
        /** AppCompatDelegate.NightMode.isNightMode(window.context)*/
        false
    )

}

fun Window.setNavigationBarBtnColor(light: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        var systemUiVisibility = decorView.systemUiVisibility
        systemUiVisibility = if (light) { //白色按钮
            systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        } else {
            systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        decorView.systemUiVisibility = systemUiVisibility
    }
}