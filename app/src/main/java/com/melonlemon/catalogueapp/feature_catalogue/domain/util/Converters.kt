package com.melonlemon.catalogueapp.feature_catalogue.domain.util

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTimestamp(text: String): List<String> {
        return text.split("#")
    }
    @TypeConverter
    fun dateToTimestamp(listText: List<String>): String {
        return listText.joinToString("#")
    }
}