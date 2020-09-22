package com.repository.models

import com.google.gson.annotations.SerializedName

data class BuildBy(

    @SerializedName("href")
    var href: String,

    @SerializedName("avatar")
    var avatar: String,

    @SerializedName("username")
    var userName: String
)