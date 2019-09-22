package com.remember.password.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.remember.password.CustomApplication
import com.remember.password.database.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    object Holder {
        val DB = synchronized(AppDatabase::class.java) {
            Room.databaseBuilder(
                CustomApplication.instance,
                AppDatabase::class.java, "co_routine_db"
            )
        }.fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    }

    companion object {
        val INSTANCE: AppDatabase by lazy { Holder.DB }
    }
}