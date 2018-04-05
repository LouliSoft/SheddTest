package com.m2f.sheddtest.presentation.features.search

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.m2f.sheddtest.R
import com.m2f.sheddtest.databinding.ActivitySearchBinding
import com.m2f.sheddtest.presentation.main.BaseActivity

class SearchActivity : BaseActivity() {

    //thanks to kotlin reified generics we can do so!
    private val searchViewModel by lazy<SearchViewModel> { getViewModel() }
    private val binding by lazy<ActivitySearchBinding> { DataBindingUtil.setContentView(this, R.layout.activity_search) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = searchViewModel

    }
}
