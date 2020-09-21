package com.repository.views

import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import com.repository.BaseActivity
import com.repository.R
import com.repository.databinding.TrendingRepoActivityBinding
import com.repository.di.viewmodelfactory.CreateViewModelFactory
import com.repository.viewmodels.TrendingActivityViewModel
import javax.inject.Inject

class TrendingRepoActivity : BaseActivity() {

    private lateinit var binding: TrendingRepoActivityBinding

    @Inject
    lateinit var trendingViewModelFactory: CreateViewModelFactory

    private lateinit var viewModel: TrendingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.trending_repo_activity)
        viewModel = createViewModel(TrendingActivityViewModel::class.java, trendingViewModelFactory)
        initView()
    }

    override fun inject() {
        super.inject()
        //Injection Happens in the Base Class
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)
        val searchView = menu?.findItem(R.menu.search_view)?.actionView
        return true
    }
}