package com.repository.views

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.repository.BaseActivity
import com.repository.R
import com.repository.databinding.TrendingRepoActivityBinding
import com.repository.di.viewmodelfactory.CreateViewModelFactory
import com.repository.extension.debugLog
import com.repository.extension.search
import com.repository.extension.visibility
import com.repository.models.TrendingRepo
import com.repository.viewmodels.TrendingActivityViewModel
import com.repository.views.adapter.TrendingAdapter
import javax.inject.Inject

class TrendingRepoActivity : BaseActivity<TrendingActivityViewModel>() {

    private lateinit var binding: TrendingRepoActivityBinding

    @Inject
    lateinit var trendingViewModelFactory: CreateViewModelFactory

    private lateinit var adapter: TrendingAdapter

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugLog("OnCreate started")
        initListener()
    }

    private fun initListener() {
        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = true
            viewModel?.refreshData()
        }

        binding.tryAgain.setOnClickListener {
            viewModel?.refreshData()
        }
    }

    override fun setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.trending_repo_activity)
    }

    override fun initViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        adapter = TrendingAdapter(utils, ArrayList())
        binding.repoRecyclerView.adapter = adapter
        binding.repoRecyclerView.layoutManager = LinearLayoutManager(this)
        setNetworkTextColor(utils.hasInternet())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)
        searchView = menu?.findItem(R.id.repo_search)?.actionView as SearchView
        searchView?.search { searchString ->
            adapter.filter.filter(searchString)
        }
        return true
    }

    override fun viewModel(): Class<TrendingActivityViewModel> {
        return TrendingActivityViewModel::class.java
    }

    override fun viewModelFactory(): CreateViewModelFactory? {
        return trendingViewModelFactory
    }

    override fun initObservers() {
        viewModel?.mTrendingRepo?.observe(this, {
            setAdapterData(it)
            binding.noInternetMsg.visibility(it.isNullOrEmpty())
            binding.tryAgain.visibility(it.isNullOrEmpty())
            binding.repoRecyclerView.visibility(!it.isNullOrEmpty())
        })

        viewModel?.mNetworkStatus?.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel?.mShowProgress?.observe(this, {
            binding.progressBar.visibility(it)
            binding.swipeLayout.isRefreshing = false
        })

        viewModel?.networkStateLiveData?.observe(this, {
            changeNetworkState(it)
            if (it) viewModel?.refreshData()
        })
    }

    private fun changeNetworkState(it: Boolean) {
        binding.networkStatus.text = if (it) "online" else "offline"
        setNetworkTextColor(it)
    }

    private fun setNetworkTextColor(isOnline: Boolean) {
        binding.networkStatus.setTextColor(
            if (isOnline) getColorStateList(R.color.color_green) else getColorStateList(
                R.color.color_red
            )
        )
    }

    private fun setAdapterData(it: List<TrendingRepo>?) {
        if (!it.isNullOrEmpty() && searchView?.isIconified!!) {
            debugLog("refresh Adapter")
            val previousState =
                binding.repoRecyclerView.layoutManager?.onSaveInstanceState() as Parcelable
            adapter.setData(it as ArrayList<TrendingRepo>)
            binding.repoRecyclerView.layoutManager?.onRestoreInstanceState(previousState)
        }
    }
}