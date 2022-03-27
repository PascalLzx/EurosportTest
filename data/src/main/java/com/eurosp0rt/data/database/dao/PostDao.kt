package com.eurosp0rt.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eurosp0rt.data.database.entities.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM post_table")
    fun getAll(): List<PostEntity>

    @Query("SELECT * FROM post_table WHERE id=:id")
    fun get(id: Long): PostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("DELETE FROM post_table")
    suspend fun deleteAll()
}