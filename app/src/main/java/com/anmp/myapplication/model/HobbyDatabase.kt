package com.anmp.myapplication.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anmp.myapplication.util.DB_NAME
import com.anmp.myapplication.util.MIGRATION_1_2
import com.anmp.myapplication.util.MIGRATION_2_3
import com.anmp.myapplication.util.MIGRATION_3_4


@Database(entities = [User::class, News::class,Author::class], version = 2, exportSchema = false)
abstract class HobbyDatabase:RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun newsDao(): NewsDAO
    abstract fun authorDao():AuthorDAO

    companion object {
        @Volatile
        private var instance: HobbyDatabase? = null
        private val LOCK = Any()


        fun buildDatabase(context: Context): HobbyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                HobbyDatabase::class.java,
                DB_NAME
            )
//                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2)
                .build()
        }


        operator fun invoke(context: Context): HobbyDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

    }
}