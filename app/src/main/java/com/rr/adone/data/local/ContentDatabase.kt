package com.rr.adone.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.rr.adone.domain.model.ContentItem

@Database(
    entities = [ContentItem::class],
    version = 1,
    exportSchema = false
)
abstract class ContentDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao

    companion object {
        const val DATABASE_NAME = "content_database"
    }
}