package com.joykeepsflowin.extractor.ui.settings

import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.joykeepsflowin.extractor.R
import com.joykeepsflowin.extractor.databinding.ActivitySettingsBinding
import com.joykeepsflowin.extractor.mvvm.BViewModel
import com.joykeepsflowin.extractor.mvvm.MvvmActivity
import com.joykeepsflowin.extractor.ui.settings.muilttype.bean.SettingsKVSwitch
import com.joykeepsflowin.extractor.ui.settings.muilttype.bean.SettingsTitle
import com.joykeepsflowin.extractor.ui.settings.muilttype.delegate.SettingsKVSwitchViewDelegate
import com.joykeepsflowin.extractor.ui.settings.muilttype.delegate.SettingsTitleViewDelegate

class SettingsActivity : MvvmActivity<ActivitySettingsBinding, BViewModel>() {
    private val adapter = MultiTypeAdapter()
    override fun initViews() {
        super.initViews()
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.title = resources.getString(R.string.title_settings)
        mBinding.rvSettings.adapter = adapter
        mBinding.rvSettings.layoutManager = LinearLayoutManager(this)
        adapter.register(SettingsTitleViewDelegate())
        adapter.register(SettingsKVSwitchViewDelegate())
        val settingsItems = mutableListOf(
            SettingsKVSwitch(
                R.string.label_settings_hint_extractor,
                "confirm_before_extracting"
            )
        )
        adapter.items = settingsItems
    }
}