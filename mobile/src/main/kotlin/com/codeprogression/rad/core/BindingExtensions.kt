package com.codeprogression.rad.core

import android.databinding.BindingConversion
import android.view.View

@BindingConversion
fun convertBooleanToVisibility(expression: Boolean): Int {
    return if (expression) View.VISIBLE else View.GONE
}