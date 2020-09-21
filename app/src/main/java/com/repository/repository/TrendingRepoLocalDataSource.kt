package com.repository.repository

import com.repository.localdatabase.TrendingDao
import com.repository.models.TrendingRepo
import javax.inject.Inject

class TrendingRepoLocalDataSource @Inject constructor(private val trendingDao: TrendingDao) {

    fun insertRepoList(list: List<TrendingRepo>) {
        trendingDao.insertTrendingRepo(list)
    }
}