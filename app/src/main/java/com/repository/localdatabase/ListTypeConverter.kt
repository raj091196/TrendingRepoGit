package com.repository.localdatabase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.repository.models.BuildBy


class ListTypeConverter {

    @TypeConverter
    fun fromListToString(list: List<BuildBy>?): String {
        if (list == null) return ""
        val gson = Gson()
        val type = object : TypeToken<List<BuildBy>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromStringToList(value: String?): List<BuildBy>? {
        if (value.isNullOrEmpty()) return null
        val gson = Gson()
        val type = object : TypeToken<List<BuildBy>?>() {}.type
        return gson.fromJson(value, type)
    }
}