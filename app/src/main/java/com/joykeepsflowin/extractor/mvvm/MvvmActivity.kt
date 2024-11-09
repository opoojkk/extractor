package com.joykeepsflowin.extractor.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.joykeepsflowin.extractor.R
import java.lang.reflect.ParameterizedType

open class MvvmActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {
    protected lateinit var mBinding: VB
    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        preInitData()
        initViews()
        initData()
    }

    open fun preInitData() {

    }

    @CallSuper
    open fun initViews() {
        mBinding = createViewBinding()
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @CallSuper
    open fun initData() {
        mViewModel = createViewModel()
    }

    @Suppress("UNCHECKED_CAST")
    private fun createViewBinding(): VB {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = type.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

    private fun createViewModel(): VM {
        val modelClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this).get(modelClass)
    }
}