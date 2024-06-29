package com.anmp.myapplication.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "news")
@Parcelize
data class News (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "synopsis")
    val synopsis: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "category")
    val category: String?,
    @ColumnInfo(name = "author_id")
    val authorId: Int?,

    var authorName:String?=null

):Parcelable

