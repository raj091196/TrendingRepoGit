package com.repository.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.repository.Utils
import com.repository.models.TrendingRepo
import com.repository.models.TrendingResponse
import com.repository.repository.TrendingRepoLocalDataSource
import com.repository.repository.TrendingRepoRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrendingActivityViewModel @Inject constructor(
    private val utils: Utils,
    private val trendingRepoLocalDataSource: TrendingRepoLocalDataSource,
    private val trendingRepoRemoteDataSource: TrendingRepoRemoteDataSource
) : BaseViewModel() {

    val trendingRepo: MutableLiveData<List<TrendingRepo>?> = MutableLiveData()

    val networkStatus: MutableLiveData<String> = MutableLiveData()

    val showProgress: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            refreshData()
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            showProgress.postValue(true)
            fetchTrendingRepoData()
            showProgress.postValue(false)
        }
    }

    private suspend fun fetchTrendingRepoData() = withContext(Dispatchers.Default) {
        if (utils.hasInternet())
            trendingRepoRemoteDataSource.execute { trendingRepoList ->
                if (trendingRepoList.isSuccess) {
                    insertData(trendingRepoList)
                } else {
                    networkStatus.postValue(trendingRepoList.errorMessage)
                    getRepoTrendingLocalData()
                }
            }
        else {
            networkStatus.postValue("Please check your Internet Connection and TryAgain")
            getRepoTrendingLocalData()
        }
    }

    private suspend fun insertData(trendingRepoList: TrendingResponse) =
        withContext(Dispatchers.Default) {
            trendingRepoList.trendingRepoList?.let {
                trendingRepoLocalDataSource.insertOrUpdateRepoList(it) {
                    trendingRepo.postValue(it)
                }
            }
        }

    private suspend fun getRepoTrendingLocalData() = withContext(Dispatchers.Default) {
        trendingRepo.postValue(trendingRepoLocalDataSource.getTrendingRepo())
    }

}