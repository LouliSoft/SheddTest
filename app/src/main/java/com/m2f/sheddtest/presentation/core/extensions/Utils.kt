package com.m2f.sheddtest.presentation.core.extensions

import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.view.View

/**
 * @author Marc Moreno
 * @since 5/4/18.
 */

//this let us call the .set() method of the ObservableField as if it was an object invocation
operator fun <T> ObservableField<T>.invoke(t: T) = this.set(t)


//this binding method lets us assign a boolean on the visibility param to set ist visibility (gone or visible)
@BindingAdapter("android:visibility")
fun View.visibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}