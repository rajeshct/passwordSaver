package com.remember.password.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.remember.password.database.dao.IMasterPasswordEntityDao
import com.remember.password.database.dao.IRecordEntityDao
import com.remember.password.database.entity.MasterPasswordEntity
import com.remember.password.database.entity.RecordEntity

@Database(entities = [RecordEntity::class, MasterPasswordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRecordDao(): IRecordEntityDao
    abstract fun getMasterPasswordDao(): IMasterPasswordEntityDao
}