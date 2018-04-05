package com.m2f.sheddtest.presentation.features.search

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.m2f.sheddtest.domain.features.search.model.TopicImage

/**
 * @author Marc Moreno
 * @since 5/4/18.
 */


@BindingAdapter("bind:topicItems")
fun RecyclerView.bindTopicItemList(list: List<TopicImage>) {
    if (adapter != null) {
        (adapter as? SearchItemsAdapter)?.updateList(list)
    } else {
        adapter = SearchItemsAdapter(list)
    }
}