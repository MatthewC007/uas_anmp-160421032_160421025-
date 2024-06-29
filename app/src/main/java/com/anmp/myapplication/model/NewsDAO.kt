package com.anmp.myapplication.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(vararg news: News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newsList: List<News>)

    @Query("SELECT * FROM news")
    fun getAllNews(): List<News>


}