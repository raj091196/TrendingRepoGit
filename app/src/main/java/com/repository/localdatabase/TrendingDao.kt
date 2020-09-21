package com.repository.localdatabase

import androidx.room.*
import com.repository.models.TrendingRepo


@Dao
interface TrendingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingRepo(trendingRepoList: List<TrendingRepo>)

    @Transaction
    @Query("SELECT * FROM TrendingRepo")
    fun getTrendingRepoList(): List<TrendingRepoMapToBuildBy>
}