package com.anmp.myapplication.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthorDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthor(vararg author: Author)

    @Query("SELECT * FROM author")
    fun getAllAuthor(): List<Author>

    @Query("SELECT * FROM author WHERE id = :authorId")
    fun getAuthorById(authorId: Int): Author?
}