package com.joykeepsflowin.extractor.kt

import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding

inline fun <reified VB : ViewBinding> ComponentActivity.viewBindings() = lazy {
    inflateBinding<VB>(layoutInflater).also { binding ->
        setContentView(binding.root)
    }
}

inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB