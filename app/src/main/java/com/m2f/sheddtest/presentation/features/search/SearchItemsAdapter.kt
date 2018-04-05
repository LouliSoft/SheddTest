package com.m2f.sheddtest.presentation.features.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.flexbox.AlignSelf
import com.google.android.flexbox.FlexboxLayoutManager
import com.m2f.sheddtest.databinding.RowItemImageBinding
import com.m2f.sheddtest.domain.features.search.model.TopicImage

/**
 * @author Marc Moreno
 * @since 5/4/18.
 */
class SearchItemsAdapter(list: List<TopicImage>) : RecyclerView.Adapter<SearchItemsAdapter.ItemViewHolder>() {

    private val mutableList = list.toMutableList()

    fun updateList(list: List<TopicImage>) {
        mutableList.clear()
        mutableList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ItemViewHolder(RowItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = mutableList.count()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindImage(mutableList[position].image)
    }

    inner class ItemViewHolder(private val binding: RowItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindImage(image: String) {
            binding.imageUrl = image
            (binding.image.layoutParams as? FlexboxLayoutManager.LayoutParams)?.apply {
                flexGrow = 1f
                alignSelf = AlignSelf.FLEX_END
            }
            binding.executePendingBindings()
        }
    }
}