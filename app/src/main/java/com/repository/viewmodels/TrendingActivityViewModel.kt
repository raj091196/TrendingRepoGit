package com.repository.viewmodels

import androidx.lifecycle.LiveData
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

/**
 *  @param utils Utility class Instance
 *  @param trendingRepoLocalDataSource LocalDataSource
 *  @param trendingRepoRemoteDataSource RemoteDataSource
 *  all these instance are inject by Dagger Component
 *  Fetch the data from DataSource and Post it to UI
 */
class TrendingActivityViewModel @Inject constructor(
    private val utils: Utils,
    private val trendingRepoLocalDataSource: TrendingRepoLocalDataSource,
    private val trendingRepoRemoteDataSource: TrendingRepoRemoteDataSource
) : BaseViewModel() {

    private val trendingRepo: MutableLiveData<List<TrendingRepo>?> = MutableLiveData()
    val mTrendingRepo: LiveData<List<TrendingRepo>?>
        get() = trendingRepo

    private val networkStatus: MutableLiveData<String> = MutableLiveData()
    val mNetworkStatus: LiveData<String>
        get() = networkStatus

    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val mShowProgress: LiveData<Boolean>
        get() = showProgress

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
        trendingRepo.postValue(trendingRepoLocalDataSource.getTrendingRepoList())
    }

}