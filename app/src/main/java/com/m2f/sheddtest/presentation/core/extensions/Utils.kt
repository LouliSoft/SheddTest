package com.m2f.sheddtest.presentation.core.extensions

import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.m2f.sheddtest.R

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

@BindingAdapter("bind:url")
fun ImageView.loadImage(imageUrl: String) {
    Glide.with(context).load(imageUrl).into(this@loadImage)
}

@BindingAdapter("bind:sugestions")
fun AutoCompleteTextView.bindSuggestionsToEditText(sugestions: List<String>) {
    val arrayAdapter = ArrayAdapter<String>(context, R.layout.simple_list_item, sugestions)
    setAdapter<ArrayAdapter<String>>(arrayAdapter)
    threshold = 0
}