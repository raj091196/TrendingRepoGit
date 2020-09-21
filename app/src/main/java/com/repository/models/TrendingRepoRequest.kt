package com.repository.models

data class TrendingRepoRequest(

    var language: String? = null,

    var since: String? = null,

    var spokenLanguageCode: String? = null
)