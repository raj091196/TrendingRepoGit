package com.repository.repository

import com.repository.localdatabase.TrendingDao
import com.repository.models.TrendingRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrendingRepoLocalDataSource @Inject constructor(private val trendingDao: TrendingDao) {

    suspend fun insertOrUpdateRepoList(list: List<TrendingRepo>, result: suspend () -> Unit) =
        withContext(Dispatchers.Default) {
            list.forEach { trendingRepo ->
                trendingDao.insertOrUpdate(trendingRepo)
            }
            result()
        }

    suspend fun getTrendingRepo(): List<TrendingRepo>? =
        withContext(Dispatchers.Default) {
            trendingDao.getTrendingRepoList()
        }
}