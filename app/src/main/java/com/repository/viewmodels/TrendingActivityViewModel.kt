package com.repository.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repository.repository.TrendingRepoLocalDataSource
import com.repository.repository.TrendingRepoRemoteDataSource
import javax.inject.Inject

class TrendingActivityViewModel @Inject constructor(
    private val trendingRepoLocalDataSource: TrendingRepoLocalDataSource,
    private val trendingRepoRemoteDataSource: TrendingRepoRemoteDataSource
) : ViewModel() {


    init {
        trendingRepoRemoteDataSource.execute(viewModelScope) { trendingRepoList ->
            if (trendingRepoList.isSuccess) {
                trendingRepoList.trendingRepoList?.let {
                    trendingRepoLocalDataSource.insertRepoList(it)
                }
            }
        }
    }
}