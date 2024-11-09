package com.joykeepsflowin.extractor.base.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class ViewBindingViewHolder<VB : ViewBinding>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private var _mBinding: VB
    val mBinding: VB
        get() = _mBinding

    init {
        _mBinding = createViewBinding()
        _mBinding
    }

    @Suppress("UNCHECKED_CAST")
    private fun createViewBinding(): VB {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = type.getMethod("bind", View::class.java)
        return method.invoke(null, itemView) as VB
    }
}