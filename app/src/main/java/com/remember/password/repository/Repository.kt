package com.remember.password.repository

import androidx.lifecycle.LiveData
import com.remember.password.database.AppDatabase
import com.remember.password.database.entity.RecordEntity

class Repository(private val appDatabase: AppDatabase) {

    fun getListingRecord(): LiveData<List<RecordEntity>> {
        return appDatabase.getRecordDao().getAllRecords()
    }

    fun insertRecordEntity(recordEntity: RecordEntity) {
        appDatabase.getRecordDao().insertRecord(recordEntity)
    }

}