package com.m2f.sheddtest.presentation.features.search

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.m2f.sheddtest.R
import com.m2f.sheddtest.databinding.ActivitySearchBinding
import com.m2f.sheddtest.presentation.main.BaseActivity


class SearchActivity : BaseActivity() {

    //thanks to kotlin reified generics we can do so!
    private val searchViewModel by lazy<SearchViewModel> { getViewModel() }
    private val binding by lazy<ActivitySearchBinding> { DataBindingUtil.setContentView(this, R.layout.activity_search) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //As the viewModel is lifecycle aware it's behaviour over config changes it's already controlled (try to make a screen rotation for instance)
        binding.vm = searchViewModel.apply {
            searchedTextEvents.observe({ lifecycle }) {
                it?.let {
                    Snackbar.make(binding.root, getString(R.string.results, it), Snackbar.LENGTH_LONG).show()
                }
            }
        }

        FacebookSdk.sdkInitialize(applicationContext) {
            if (AccessToken.getCurrentAccessToken() == null) {
                LoginManager.getInstance().logInWithPublishPermissions(this, listOf())
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        //super.onActivityResult(requestCode, resultCode, data);
        CallbackManager.Factory.create().onActivityResult(requestCode, resultCode, data)
    }
}
