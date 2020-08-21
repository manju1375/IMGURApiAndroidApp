package com.dms.imagesearch.core.storage.entity

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dms.imagesearch.core.storage.ImagesDao


@Database(
        entities = [ImageDbItem::class],
        version = ImageDatabaseMigrations.latestVersion
)
abstract class ImagesDatabase : RoomDatabase() {

    /**
     * Get images DAO
     */
    abstract fun imagesDao(): ImagesDao

    companion object {

        private const val databaseName = "images-db"

        fun buildDefault(context: Context) =
                Room.databaseBuilder(context, ImagesDatabase::class.java, databaseName)
                        .addMigrations(*ImageDatabaseMigrations.allMigrations)
                        .build()

        @VisibleForTesting
        fun buildTest(context: Context) =
                Room.inMemoryDatabaseBuilder(context, ImagesDatabase::class.java)
                        .build()
    }
}