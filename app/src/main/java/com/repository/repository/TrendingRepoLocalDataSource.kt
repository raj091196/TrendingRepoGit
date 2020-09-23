package com.repository.repository

import com.repository.localdatabase.TrendingDao
import com.repository.models.TrendingRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *  @param trendingDao room dao object insert by the Dagger
 *  Single Responsible Data Source, to Communicate with the dao
 *  to get data from Room and return to viewModel
 */
class TrendingRepoLocalDataSource @Inject constructor(private val trendingDao: TrendingDao) {

    /**
     *  @param list list of Trending data
     *  @param result suspend method params to returns the result
     *  insert or update the Trending Repo result
     */
    suspend fun insertOrUpdateRepoList(list: List<TrendingRepo>, result: suspend () -> Unit) =
        withContext(Dispatchers.Default) {
            list.forEach { trendingRepo ->
                trendingDao.insertOrUpdate(trendingRepo)
            }
            result()
        }

    /**
     * @return list of trending repo
     */
    suspend fun getTrendingRepoList(): List<TrendingRepo>? =
        withContext(Dispatchers.Default) {
            trendingDao.getTrendingRepoList()
        }
}