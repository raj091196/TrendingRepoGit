package com.repository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "TrendingRepo")
data class TrendingRepo(

    @PrimaryKey
    @SerializedName("author")
    @ColumnInfo(name = "author")
    @Expose
    var author: String,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    @Expose
    var name: String,

    @SerializedName("avatar")
    @ColumnInfo(name = "avatar")
    @Expose
    var avatar: String,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    @Expose
    var url: String,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    @Expose
    var description: String,

    @SerializedName("language")
    @ColumnInfo(name = "language")
    @Expose
    var language: String,

    @SerializedName("languageColor")
    @ColumnInfo(name = "languageColor")
    @Expose
    var languageColor: String,

    @SerializedName("stars")
    @ColumnInfo(name = "stars")
    @Expose
    var stars: Long,

    @SerializedName("forks")
    @ColumnInfo(name = "forks")
    @Expose
    var forks: Long,

    @SerializedName("currentPeriodStars")
    @ColumnInfo(name = "currentPeriodStars")
    @Expose
    var currentPeriodStars: Long,

    @SerializedName("builtBy")
    @ColumnInfo(name = "builtBy")
    @Expose
    var builtBy: List<BuildBy>
)