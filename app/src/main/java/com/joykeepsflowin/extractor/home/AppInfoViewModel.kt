package com.joykeepsflowin.extractor.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joykeepsflowin.extractor.home.appinfo.InstalledAppManager
import com.joykeepsflowin.extractor.home.appinfo.bean.AppInfo

class AppInfoViewModel : ViewModel() {
    private val _filterAppListMode = MutableLiveData<Int>()

    fun observeFilterAppListMode(owner: LifecycleOwner) {
        _filterAppListMode.observe(owner) { mode ->
            val appInfos = InstalledAppManager.get(mode)
            _filterAppList.value = appInfos
        }
    }

    fun postFilterAppListModeValue(mode: Int) {
        _filterAppListMode.postValue(mode)
    }

    private val _filterAppList = MutableLiveData<List<AppInfo>>()
    val filterAppList: LiveData<List<AppInfo>>
        get() = _filterAppList
}