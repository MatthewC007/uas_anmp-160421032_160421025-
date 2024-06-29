package com.anmp.myapplication.util

import android.content.Context
import androidx.room.TypeConverter
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.anmp.myapplication.model.HobbyDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val DB_NAME = "uasanmpdb"


fun buildDb(context: Context): HobbyDatabase {
    val db = HobbyDatabase.buildDatabase(context)
    return db
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("INSERT INTO author (name) VALUES ('matthew')")
        database.execSQL("INSERT INTO author (name) VALUES ('john')")
        database.execSQL("INSERT INTO author (name) VALUES ('chandra')")
        database.execSQL("INSERT INTO author (name) VALUES ('hubert')")
    }
}

val MIGRATION_2_3 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE news ADD COLUMN category TEXT  DEFAULT NULL")
        database.execSQL("ALTER TABLE news ADD COLUMN author_id INTEGER  DEFAULT NULL")
        database.execSQL("INSERT INTO author (name) VALUES ('matthew')")
        database.execSQL("INSERT INTO author (name) VALUES ('john')")


    }
}
val MIGRATION_3_4 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL("INSERT INTO author (name) VALUES ('chandra')")
        database.execSQL("INSERT INTO author (name) VALUES ('hubert')")
    }
}










