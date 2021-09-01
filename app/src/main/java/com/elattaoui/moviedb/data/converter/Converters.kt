package com.elattaoui.moviedb.data.converter

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun getOriginalCountryFromString(originCountry: String?): List<String> {
        val list = mutableListOf<String>()

        val array = originCountry?.split(",".toRegex())?.dropLastWhile {
            it.isEmpty()
        }?.toTypedArray()

        if (array != null) {
            for (originCountryItem in array) {
                if (originCountryItem.isNotEmpty()) {
                    list.add(originCountryItem)
                }
            }
        }
        return list
    }

    @TypeConverter
    fun getOriginalCountryString(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun getGenreIdsFromString(genreIds: String?): List<Int> {
        val list = mutableListOf<Int>()

        val array = genreIds?.split(",".toRegex())?.dropLastWhile {
            it.isEmpty()
        }?.toTypedArray()

        if (array != null) {
            for (stringId in array) {
                if (stringId.isNotEmpty()) {
                    list.add(stringId.toInt())
                }
            }
        }
        return list
    }

    @TypeConverter
    fun getGenreIdsString(list: List<Int>): String {
        return list.joinToString(",")
    }
}
