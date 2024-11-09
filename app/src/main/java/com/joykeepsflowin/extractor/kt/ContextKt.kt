package com.joykeepsflowin.extractor.kt

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


fun Context.extractInstalledAppApk(packageName: String, outputFilePath: String): String? {
    try {
        val packageManager = packageManager
        val appInfo = packageManager.getApplicationInfo(packageName, 0)
        val apkPath = appInfo.sourceDir
        val apkFile = File(apkPath)

        // apk file path
        val outputFile = File(outputFilePath)

        // copy apk file
        FileInputStream(apkFile).use { inputStream ->
            FileOutputStream(outputFile).use { outputStream ->
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }
            }
        }
        Log.d("extractInstalledAppApk", "APK path: $outputFilePath")
        return outputFilePath
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}

inline fun <reified T : Activity> Context.startActivity(block: (Bundle.() -> Unit) = {}) {
    startActivity(Intent(this, T::class.java).apply {
        val bundle = Bundle()
        block.invoke(bundle)
        putExtras(bundle)
    })
}

//fun Context.getString(@StringRes strRes: Int): String = resources.getString(strRes)