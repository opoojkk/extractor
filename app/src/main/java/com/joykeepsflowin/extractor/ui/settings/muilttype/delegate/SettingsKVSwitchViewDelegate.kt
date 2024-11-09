package com.joykeepsflowin.extractor.ui.settings.muilttype.delegate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewDelegate
import com.joykeepsflowin.extractor.R
import com.joykeepsflowin.extractor.base.holder.ViewBindingViewHolder
import com.joykeepsflowin.extractor.databinding.ItemSettingsSwitchBinding
import com.joykeepsflowin.extractor.ui.settings.muilttype.bean.SettingsKVSwitch
import com.joykeepsflowin.lib_kv.Prefs

class SettingsKVSwitchViewDelegate :
    ItemViewDelegate<SettingsKVSwitch, SettingsKVSwitchViewDelegate.SettingsKVSwitchViewHolder>() {

    class SettingsKVSwitchViewHolder(itemView: View) :
        ViewBindingViewHolder<ItemSettingsSwitchBinding>(itemView)

    override fun onBindViewHolder(
        holder: SettingsKVSwitchViewHolder,
        item: SettingsKVSwitch
    ) {
        holder.mBinding.tvSettingTitle.setText(item.resId)
        holder.mBinding.switchSetting.isChecked = Prefs.get().getBoolean(item.key, false) == true
        holder.mBinding.switchSetting.setOnCheckedChangeListener { _, isChecked ->
            Prefs.get().putBoolean(item.key, isChecked)
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): SettingsKVSwitchViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_settings_switch, parent, false)
        return SettingsKVSwitchViewHolder(itemView)
    }


}