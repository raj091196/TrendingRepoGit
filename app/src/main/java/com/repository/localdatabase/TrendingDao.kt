package com.repository.localdatabase

import androidx.room.*
import com.repository.extension.debugLog
import com.repository.models.TrendingRepo


@Dao
interface TrendingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTrendingRepo(trendingRepo: TrendingRepo): Long?

    @Update
    fun updateTrendingRepo(trendingRepo: TrendingRepo): Int

    @Transaction
    @Query("SELECT * FROM TrendingRepo")
    fun getTrendingRepoList(): List<TrendingRepo>?

    @Transaction
    fun insertOrUpdate(trendingRepo: TrendingRepo) {
        var updateId = 0
        val id = insertTrendingRepo(trendingRepo)
        debugLog("INSERT ID : $id")
        if (id == -1L) updateId = updateTrendingRepo(trendingRepo)
        debugLog("UPDATE ID: $updateId")
    }
}