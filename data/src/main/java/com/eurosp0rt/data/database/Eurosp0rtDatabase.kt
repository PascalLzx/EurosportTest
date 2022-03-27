package com.eurosp0rt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.eurosp0rt.data.database.dao.PostDao
import com.eurosp0rt.data.database.entities.PostEntity
import com.eurosp0rt.data.database.entities.SportEntity
import com.eurosp0rt.domain.models.PostType
import com.eurosp0rt.domain.models.Sport
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
@TypeConverters(PostTypeConverters::class, SportConverters::class)
abstract class Eurosp0rtDatabase: RoomDatabase() {
    abstract val postDao: PostDao
}

class PostTypeConverters {

    @TypeConverter
    fun toPostType(data: String): PostType = PostType.valueOf(data)

    @TypeConverter
    fun fromPostType(postType: PostType): String = postType.name
}

class SportConverters {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun toSport(data: String?): SportEntity? {
        if (data == null) {
            return null
        }
        return moshi.adapter(SportEntity::class.java).fromJson(data)
    }

    @TypeConverter
    fun fromSport(sport: SportEntity?): String {
        return moshi.adapter(SportEntity::class.java).toJson(sport)
    }
}