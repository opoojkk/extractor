package com.joykeepsflowin.extractor

import android.content.res.Resources
import android.util.TypedValue

fun Resources.Theme.getThemedValue(resid: Int): Int {
    val typedValue = TypedValue()
    resolveAttribute(resid, typedValue, true)
    return typedValue.data
}