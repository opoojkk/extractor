package com.joykeepsflowin.extractor.kt

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.joykeepsflowin.extractor.appinfo.InstalledAppMode
import com.joykeepsflowin.extractor.appinfo.bean.AppInfo
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

fun Context.getInstalledApps(
    mode: Int
): List<AppInfo> {
    val packageManager: PackageManager = packageManager
    val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    val userAppInfoList = mutableListOf<AppInfo>()
    val systemAppInfoList = mutableListOf<AppInfo>()

    for (app in apps) {
        val packageName = app.packageName
        val appInfo = packageManager.getApplicationInfo(packageName, 0)
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        // filter system app
        if ((app.flags and ApplicationInfo.FLAG_SYSTEM) != 0) {
            systemAppInfoList.add(
                AppInfo(
                    appName = packageManager.getApplicationLabel(appInfo).toString(),
                    packageName = packageInfo.packageName,
                    icon = packageManager.getApplicationIcon(appInfo.packageName),
                    versionName = packageInfo.versionName,
                    versionCode = packageInfo.versionCode,
                    firstInstallTime = packageInfo.firstInstallTime,
                    lastUpdateTime = packageInfo.lastUpdateTime,
                    sourceDir = appInfo.sourceDir,
                    dataDir = appInfo.dataDir,
                    isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
                    isUpdatedSystemApp = (appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0,
                    isDebuggable = (appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0,
                    uid = appInfo.uid,
                    permissions = packageInfo.requestedPermissions?.toList() ?: emptyList(),
                )
            )
            continue
        } else {
            userAppInfoList.add(
                AppInfo(
                    appName = packageManager.getApplicationLabel(appInfo).toString(),
                    packageName = packageInfo.packageName,
                    icon = packageManager.getApplicationIcon(appInfo.packageName),
                    versionName = packageInfo.versionName,
                    versionCode = packageInfo.versionCode,
                    firstInstallTime = packageInfo.firstInstallTime,
                    lastUpdateTime = packageInfo.lastUpdateTime,
                    sourceDir = appInfo.sourceDir,
                    dataDir = appInfo.dataDir,
                    isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
                    isUpdatedSystemApp = (appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0,
                    isDebuggable = (appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0,
                    uid = appInfo.uid,
                    permissions = packageInfo.requestedPermissions?.toList() ?: emptyList(),
                )
            )
        }
    }

    return when (mode) {
        InstalledAppMode.USER.value -> userAppInfoList
        InstalledAppMode.SYSTEM.value -> systemAppInfoList
        InstalledAppMode.ALL.value -> userAppInfoList + systemAppInfoList
        else -> {
            throw IllegalArgumentException("it should be a member of InstalledAppMode")
        }
    }
}


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