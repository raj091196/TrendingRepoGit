package com.repository.localdatabase

import androidx.room.Embedded
import androidx.room.Relation
import com.repository.models.BuildBy
import com.repository.models.TrendingRepo

data class TrendingRepoMapToBuildBy(

    @Embedded
    val trendingRepo: TrendingRepo,

    @Relation(parentColumn = "id", entityColumn = "buildId", entity = BuildBy::class)
    val buildByList: List<BuildBy>
)