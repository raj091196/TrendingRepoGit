package com.repository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "BuildBy",
    foreignKeys = [ForeignKey(
        entity = TrendingRepo::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("buildId"),
        onDelete = CASCADE
    )]
)
data class BuildBy(

    @ColumnInfo(name = "buildId")
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @SerializedName("href")
    @ColumnInfo(name = "href")
    @Expose
    var href: String,

    @SerializedName("avatar")
    @ColumnInfo(name = "avatar")
    @Expose
    var avatar: String,

    @SerializedName("username")
    @ColumnInfo(name = "username")
    @Expose
    var userName: String
)