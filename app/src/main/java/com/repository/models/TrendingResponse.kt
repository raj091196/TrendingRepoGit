package com.repository.models

data class TrendingResponse(

    var trendingRepoList: List<TrendingRepo>?,

    var isSuccess: Boolean,

    var errorMessage: String
)