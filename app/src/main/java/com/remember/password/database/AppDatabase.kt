package com.remember.password.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.remember.password.database.dao.IRecordEntityDao
import com.remember.password.database.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRecordDao(): IRecordEntityDao
}