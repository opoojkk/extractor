package com.joykeepsflowin.extractor

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

fun extractInstalledAppApk(context: Context, packageName: String, outputFilePath: String): String? {
    try {
        val packageManager = context.packageManager
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
